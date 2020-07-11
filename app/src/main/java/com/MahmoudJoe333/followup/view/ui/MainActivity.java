package com.MahmoudJoe333.followup.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.MahmoudJoe333.followup.Model.Item_Entity;
import com.MahmoudJoe333.followup.R;
import com.MahmoudJoe333.followup.view.logic.Item_Adapter;
import com.MahmoudJoe333.followup.viewModel.MainActivity_viewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private MainActivity_viewModel mViewModel;
    RecyclerView mRecycleView;
    Item_Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    FloatingActionButton mAddNewItem_BTN,mundo_BTN;
    Stack<Item_Entity> mStack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        AddNewItem();
        Update();
        Delete();
        Undo();
    }

    private void init() {
        mViewModel= ViewModelProviders.of(this).get(MainActivity_viewModel.class);
        setTitle(getResources().getString(R.string.mainTitle));
        BuildAdapter();
        observe();
        mundo_BTN=findViewById(R.id.undo);
        mStack=new Stack<>();
    }

    private void Undo() {
        mundo_BTN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if(!mStack.isEmpty()) {
                    mViewModel.insert(mStack.pop());
                    mAdapter.notifyDataSetChanged();
                }
                if(mStack.isEmpty())
                    mundo_BTN.setVisibility(View.GONE);
            }
        });

    }

    private void observe() {
        mViewModel.GetAllList().observe(this, new Observer<List<Item_Entity>>() {
            @Override
            public void onChanged(List<Item_Entity> item_entities) {
                mAdapter.setRes(getResources());
                mAdapter.setList(item_entities);
                if(item_entities.isEmpty())
                    findViewById(R.id.noNote).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.noNote).setVisibility(View.GONE);
            }
        });
    }

    private void Delete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Item_Entity item=mAdapter.getItemAt(position);
                mStack.push(item);
                mViewModel.delete(item);
                mundo_BTN.setVisibility(View.VISIBLE);
            }
        }).attachToRecyclerView(mRecycleView);
    }

    private void Update() {
        mAdapter.setOnItemClickListener(new Item_Adapter.OnItemClickListener() {
            @Override
            public void OnClick(Item_Entity item) {
                Intent intent =new Intent(MainActivity.this, detailsActivity.class);
                intent.putExtra(detailsActivity.EXTRA_ITEM_KEY,item);
                intent.putExtra(detailsActivity.EXTRA_PROCESS_TYPE,"update");
                startActivity(intent);
            }
        });
    }

    private void AddNewItem() {
        mAddNewItem_BTN=findViewById(R.id.addNew);
        mAddNewItem_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, detailsActivity.class);
                intent.putExtra(detailsActivity.EXTRA_PROCESS_TYPE,"insert");
                startActivityForResult(intent,1);
            }
        });
    }

    private void BuildAdapter() {
        mRecycleView=findViewById(R.id.Container);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new Item_Adapter();
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(mLayoutManager);
    }
}
