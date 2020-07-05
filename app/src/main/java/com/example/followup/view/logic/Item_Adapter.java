package com.example.followup.view.logic;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.followup.Model.Item_Entity;
import com.example.followup.R;

import java.util.ArrayList;
import java.util.List;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.Holder> {
    private ArrayList<Item_Entity> mlist=new ArrayList<>();
    public void setList(List<Item_Entity> list) {
        this.mlist=new ArrayList<>(list);
        notifyDataSetChanged();
    }
    public Item_Entity getItemAt(int position)
    {
        return mlist.get(position);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Item_Entity item=mlist.get(position);
        holder.mTitle.setText(item.getTitle());
        holder.mWeight.setText("Weight: "+item.getWeight());
        holder.mWeekNo.setText("Week No: "+item.getWeekNo());
        holder.mFat.setText("Fat Percent: "+item.getFat_percent());
        holder.mWater.setText("Water Percent: "+item.getWater_percent());
        holder.mEmojiRes.setImageResource(item.getEmojiRes());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView mTitle,mWeight,mWeekNo,mFat,mWater;
        public ImageView mEmojiRes;

        public Holder(@NonNull View itemView) {
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
                    int position=getAdapterPosition();
                    if(mListener!=null&&position!=RecyclerView.NO_POSITION)
                    {
                        mListener.OnClick(mlist.get(position));
                    }
                }
            });

        }
    }
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }
    public interface OnItemClickListener{ void OnClick(Item_Entity item);}
}
