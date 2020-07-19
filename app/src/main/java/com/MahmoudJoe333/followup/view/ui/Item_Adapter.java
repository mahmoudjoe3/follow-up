package com.MahmoudJoe333.followup.view.ui;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MahmoudJoe333.followup.Model.Item_Entity;
import com.MahmoudJoe333.followup.R;

import java.util.ArrayList;
import java.util.List;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.myItem_holder> {
    private ArrayList<Item_Entity> mlist = new ArrayList<>();
    private Resources mresources;

    public void setList(List<Item_Entity> list) {
        this.mlist = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setRes(Resources res) {
        mresources = res;
    }

    public Item_Entity getItemAt(int position) {
        return mlist.get(position);
    }


    @NonNull
    @Override
    public myItem_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        myItem_holder myItem_holder = new myItem_holder(view);
        return myItem_holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myItem_holder holder, int position) {
        Item_Entity item = mlist.get(position);
        holder.mTitle.setText(item.getTitle());

        holder.mWeight.setText(mresources.getString(R.string.item_weight)+" "+item.getWeight()+"kg");
        holder.mWeekNo.setText(mresources.getString(R.string.item_weekno)+" "+item.getWeekNo());
        holder.mFat.setText(mresources.getString(R.string.item_fat)+" "+item.getFat_percent()+"%");
        holder.mWater.setText(mresources.getString(R.string.item_water)+" "+item.getWater_percent()+"%");
        holder.mEmojiRes.setImageResource(item.getEmojiRes());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class myItem_holder extends RecyclerView.ViewHolder {
        public TextView mTitle,mWeight,mWeekNo,mFat,mWater;
        public ImageView mEmojiRes;

        public myItem_holder(@NonNull View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.txt_title);
            mWeight=itemView.findViewById(R.id.txt_weight);
            mWeekNo=itemView.findViewById(R.id.txt_weekNo);
            mFat=itemView.findViewById(R.id.txt_fat);
            mWater=itemView.findViewById(R.id.txt_water);
            mEmojiRes=itemView.findViewById(R.id.img_emoji);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.OnClick(mlist.get(position));
                    }
                }
            });

        }
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void OnClick(Item_Entity item);
    }
}
