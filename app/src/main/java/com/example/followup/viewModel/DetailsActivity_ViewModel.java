package com.example.followup.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.followup.Model.Item_Entity;
import com.example.followup.Model.Item_Repositary;

import java.util.List;

public class DetailsActivity_ViewModel extends AndroidViewModel {
    private Item_Repositary mitemRepositary;
    public DetailsActivity_ViewModel(@NonNull Application application) {
        super(application);
        mitemRepositary=new Item_Repositary(application);
    }
    public void insert(Item_Entity item)
    {
        mitemRepositary.insert(item);
    }
    public void update(Item_Entity item)
    {
        mitemRepositary.update(item);
    }

}