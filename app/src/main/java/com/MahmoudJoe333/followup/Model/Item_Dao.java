package com.MahmoudJoe333.followup.Model;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Item_Dao {

    @Insert
    void insert(Item_Entity item);
    @Delete
    void delete(Item_Entity item);
    @Update
    void update(Item_Entity item);
    @Query("Select * from item")
    LiveData<List<Item_Entity>> getAllItem();

    @Query("delete from item")
    void deleteAllItem();
}
