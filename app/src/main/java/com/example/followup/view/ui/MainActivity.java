package com.example.followup.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.followup.R;
import com.example.followup.viewModel.MainActivity_viewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivity_viewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        mViewModel= ViewModelProviders.of(this).get(MainActivity_viewModel.class);

    }
}
