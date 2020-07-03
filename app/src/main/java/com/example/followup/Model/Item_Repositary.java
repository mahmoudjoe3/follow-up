package com.example.followup.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Item_Repositary {
    private Item_Dao mitem_dao;
    private LiveData<List<Item_Entity>> mlist;
    public void Item_Repositary(Application app)
    {
        followUpDataBase mdatabase = followUpDataBase.getInstance(app);
        mitem_dao=mdatabase.getItemDao();
        mlist=mitem_dao.getAllItem();
    }

    public void insert(Item_Entity item)
    {
        new insertAsyncTask(mitem_dao).execute(item);
    }
    public void update(Item_Entity item)
    {
        new updateAsyncTask(mitem_dao).execute(item);
    }
    public void delete(Item_Entity item)
    {
        new deleteAsyncTask(mitem_dao).execute(item);
    }
    public LiveData<List<Item_Entity>> getAllItem(Item_Entity item)
    {
        return mlist;
    }
    public void deleteAllItem(Item_Entity item)
    {
        new clearAsyncTask(mitem_dao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Item_Entity,Void,Void>{
        private Item_Dao item_dao;

        public insertAsyncTask(Item_Dao item_dao) {
            this.item_dao = item_dao;
        }

        @Override
        protected Void doInBackground(Item_Entity... item_entities) {
            item_dao.insert(item_entities[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Item_Entity,Void,Void>{
        private Item_Dao item_dao;

        public deleteAsyncTask(Item_Dao item_dao) {
            this.item_dao = item_dao;
        }

        @Override
        protected Void doInBackground(Item_Entity... item_entities) {
            item_dao.delete(item_entities[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<Item_Entity,Void,Void>{
        private Item_Dao item_dao;

        public updateAsyncTask(Item_Dao item_dao) {
            this.item_dao = item_dao;
        }

        @Override
        protected Void doInBackground(Item_Entity... item_entities) {
            item_dao.update(item_entities[0]);
            return null;
        }
    }
    private static class clearAsyncTask extends AsyncTask<Void,Void,Void>{
        private Item_Dao item_dao;

        public clearAsyncTask(Item_Dao item_dao) {
            this.item_dao = item_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            item_dao.deleteAllItem();
            return null;
        }
    }
}
