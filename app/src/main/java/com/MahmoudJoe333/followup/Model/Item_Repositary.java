package com.MahmoudJoe333.followup.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Item_Repositary {
    private Item_Dao itemDao;
    private LiveData<List<Item_Entity>> data;

    public Item_Repositary(Application application) {
        followUpDataBase mdatabase = followUpDataBase.getInstance(application);
        itemDao = mdatabase.getItemDao();
        data = itemDao.getAllItem();
    }

    public void insert(Item_Entity item) {
        new insertAsyncTask(itemDao).execute(item);
    }

    public void update(Item_Entity item) {
        new updateAsyncTask(itemDao).execute(item);
    }

    public void delete(Item_Entity item) {
        new deleteAsyncTask(itemDao).execute(item);
    }

    public LiveData<List<Item_Entity>> getAllItem() {
        return data;
    }

    public void deleteAllItem() {
        new clearAsyncTask(itemDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Item_Entity, Void, Void> {
        private Item_Dao Item_Dao;

        public insertAsyncTask(Item_Dao Item_Dao) {
            this.Item_Dao = Item_Dao;
        }

        @Override
        protected Void doInBackground(Item_Entity... item_entities) {
            Item_Dao.insert(item_entities[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Item_Entity, Void, Void> {
        private Item_Dao Item_Dao;

        public deleteAsyncTask(Item_Dao Item_Dao) {
            this.Item_Dao = Item_Dao;
        }

        @Override
        protected Void doInBackground(Item_Entity... item_entities) {
            Item_Dao.delete(item_entities[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Item_Entity, Void, Void> {
        private Item_Dao Item_Dao;

        public updateAsyncTask(Item_Dao Item_Dao) {
            this.Item_Dao = Item_Dao;
        }

        @Override
        protected Void doInBackground(Item_Entity... item_entities) {
            Item_Dao.update(item_entities[0]);
            return null;
        }
    }

    private static class clearAsyncTask extends AsyncTask<Void, Void, Void> {
        private Item_Dao Item_Dao;

        public clearAsyncTask(Item_Dao Item_Dao) {
            this.Item_Dao = Item_Dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Item_Dao.deleteAllItem();
            return null;
        }
    }
}
