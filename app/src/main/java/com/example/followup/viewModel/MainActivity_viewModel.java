package com.example.followup.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.followup.Model.Item_Entity;
import com.example.followup.Model.Item_Repositary;

import java.util.List;

public class MainActivity_viewModel extends AndroidViewModel {
    private Item_Repositary mitemRepositary;
    private LiveData<List<Item_Entity>> mList;
    public MainActivity_viewModel(@NonNull Application application) {
        super(application);
        mitemRepositary=new Item_Repositary(application);
        mList=mitemRepositary.getAllItem();
    }
    public void insert(Item_Entity item)
    {
        mitemRepositary.insert(item);
    }
    public void update(Item_Entity item)
    {
        mitemRepositary.update(item);
    }
    public void delete(Item_Entity item)
    {
        mitemRepositary.delete(item);
    }
    public void clear()
    {
        mitemRepositary.deleteAllItem();
    }
    public LiveData<List<Item_Entity>> GetAllList(){
        return mList;
    }

}
