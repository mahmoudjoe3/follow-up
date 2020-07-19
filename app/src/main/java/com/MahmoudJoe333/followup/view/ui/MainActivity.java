package com.MahmoudJoe333.followup.view.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MahmoudJoe333.followup.Model.Item_Entity;
import com.MahmoudJoe333.followup.R;
import com.MahmoudJoe333.followup.viewModel.MainActivity_viewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.noNote)
    TextView mNoNoteTXT;
    @BindView(R.id.Container)
    RecyclerView mContainerRECYCLER;
    @BindView(R.id.addNew)
    FloatingActionButton mAddNewBTN;
    @BindView(R.id.undo)
    FloatingActionButton mUndoBTN;
    
    
    private MainActivity_viewModel mViewModel;
    Item_Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Stack<Item_Entity> mStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        AddNewItem();
        Update();
        Delete();
        Undo();
    }

    private void init() {
        mViewModel = new ViewModelProvider(this).get(MainActivity_viewModel.class);
        setTitle(getResources().getString(R.string.mainTitle));
        BuildAdapter();
        observe();
        mStack = new Stack<>();
    }

    private void Undo() {
        mUndoBTN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (!mStack.isEmpty()) {
                    mViewModel.insert(mStack.pop());
                    mAdapter.notifyDataSetChanged();
                }
                if (mStack.isEmpty())
                    mUndoBTN.setVisibility(View.GONE);
            }
        });
    }

    private void observe() {
        mViewModel.GetAllList().observe(this, new Observer<List<Item_Entity>>() {
            @Override
            public void onChanged(List<Item_Entity> item_entities) {
                mAdapter.setRes(getResources());
                mAdapter.setList(item_entities);
                if (item_entities.isEmpty())
                    mNoNoteTXT.setVisibility(View.VISIBLE);
                else
                    mNoNoteTXT.setVisibility(View.GONE);
            }
        });
    }

    private void Delete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Item_Entity item = mAdapter.getItemAt(position);
                mStack.push(item);
                mViewModel.delete(item);
                mUndoBTN.setVisibility(View.VISIBLE);
            }
        }).attachToRecyclerView(mContainerRECYCLER);
    }

    private void Update() {
        mAdapter.setOnItemClickListener(new Item_Adapter.OnItemClickListener() {
            @Override
            public void OnClick(Item_Entity item) {
                Intent intent = new Intent(MainActivity.this, detailsActivity.class);
                intent.putExtra(detailsActivity.EXTRA_ITEM_KEY, item);
                intent.putExtra(detailsActivity.EXTRA_PROCESS_TYPE, "update");
                startActivity(intent);
            }
        });
    }

    private void AddNewItem() {
        mAddNewBTN = findViewById(R.id.addNew);
        mAddNewBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, detailsActivity.class);
                intent.putExtra(detailsActivity.EXTRA_PROCESS_TYPE, "insert");
                startActivityForResult(intent, 1);
            }
        });
    }

    private void BuildAdapter() {
        mContainerRECYCLER = findViewById(R.id.Container);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Item_Adapter();
        mContainerRECYCLER.setAdapter(mAdapter);
        mContainerRECYCLER.setHasFixedSize(true);
        mContainerRECYCLER.setLayoutManager(mLayoutManager);
    }
}
