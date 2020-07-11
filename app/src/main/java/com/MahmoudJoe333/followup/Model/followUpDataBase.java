package com.MahmoudJoe333.followup.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Item_Entity.class,version = 1)
public abstract class followUpDataBase extends RoomDatabase {
    public abstract Item_Dao getItemDao();
    private static  followUpDataBase instance;
    public static synchronized followUpDataBase getInstance(Context context)
    {
        if(instance == null)
        {
            instance= Room.databaseBuilder(context,followUpDataBase.class,"follow-up").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
