package com.example.followup.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.followup.Model.Item_Entity;
import com.example.followup.R;
import com.example.followup.view.logic.InBodyCalculation;
import com.example.followup.viewModel.DetailsActivity_ViewModel;

import java.text.DateFormat;
import java.util.Calendar;

public class detailsActivity extends AppCompatActivity {

    private DetailsActivity_ViewModel mViewModel;
    public final static String EXTRA_ITEM_KEY ="com.example.followup.view.ui.EXTRA_ITEM";
    public final static String EXTRA_PROCESS_TYPE ="com.example.followup.view.ui.PROCESS_TYPE";
    private Item_Entity mItem;
    private String process="";
    TextView mDate;
    EditText mTitle, mWeekNo, mAge, mWeight, mHeight, mBurnRate, mFatPercent, mWaterPercent, mComment;
    ImageView mEmojiRes,mBurnRateBTN;
    Spinner mgenderspiner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
    }

    private void init() {
        FindView();
        mViewModel= ViewModelProviders.of(this).get(DetailsActivity_ViewModel.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_exit);

        mItem=(Item_Entity) getIntent().getSerializableExtra(EXTRA_ITEM_KEY);
        process=getIntent().getStringExtra(EXTRA_PROCESS_TYPE);
        if(process.equalsIgnoreCase("update")) {
            setTitle("Update your state");
            AssignValues();
        }
        else {
            setTitle("Add New State");
            //date
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
            mDate.setText(currentDate);
        }

        mBurnRateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender=mgenderspiner.getSelectedItem().toString();
                Double height=Double.valueOf(mHeight.getText().toString());
                Double weight=Double.valueOf(mWeight.getText().toString());
                Integer age=Integer.parseInt(mAge.getText().toString());

                double BurnRate= InBodyCalculation.getBurnRate(height,weight,age,gender);
                double FatPercent=InBodyCalculation.getFatPercent(height,weight,age,gender);
                double WaterPercent=InBodyCalculation.getWaterPercent(height,weight,age,gender);

                mBurnRate.setText(String.valueOf(BurnRate));
                mFatPercent.setText(String.valueOf(FatPercent));
                mWaterPercent.setText(String.valueOf(WaterPercent));
                mEmojiRes.setImageResource(getEmoji(FatPercent,gender));
            }
        });
    }

    private void AssignValues() {
        mTitle.setText(mItem.getTitle());
        mWeekNo.setText(mItem.getWeekNo());
        mAge.setText(mItem.getAge());
        mWeight.setText(String.valueOf(mItem.getWeight()));
        int pos=(mItem.getGender().equalsIgnoreCase("male"))?1:0;
        mgenderspiner.setSelection(pos);
        mDate.setText(String.valueOf(mItem.getDate()));
        mHeight.setText(String.valueOf(mItem.getHeight()));
        mBurnRate.setText(String.valueOf(mItem.getBurn_rate()));
        mFatPercent.setText(String.valueOf(mItem.getFat_percent()));
        mWaterPercent.setText(String.valueOf(mItem.getWater_percent()));
        mComment.setText(String.valueOf(mItem.getComment()));
        mEmojiRes.setImageResource(mItem.getEmojiRes());

    }

    private int getEmoji(double fatPercent,String gender) {
        int imgID=0;
        if(gender.equalsIgnoreCase("female")) {
            if(fatPercent<10)//dead
                return R.drawable.ic_dead;
            else if(fatPercent>=10&&fatPercent<=13)//Essential  fat
                return R.drawable.ic_very_dissatisfied;
            else if(fatPercent>13&&fatPercent<=20)//Athletes
                return R.drawable.ic_less_satisfied;
            else if(fatPercent>20&&fatPercent<=24)//Fitness
                return R.drawable.ic_very_satisfied;
            else if(fatPercent>24&&fatPercent<=31)//Average
                return R.drawable.ic_satisfied;
            else if(fatPercent>31)//Obese
                return R.drawable.ic_very_dissatisfied;
        }
        else{
            if(fatPercent<2)//dead
                return R.drawable.ic_dead;
            else if(fatPercent>=2&&fatPercent<=5)//Essential  fat
                return R.drawable.ic_very_dissatisfied;
            else if(fatPercent>5&&fatPercent<=13)//Athletes
                return R.drawable.ic_less_satisfied;
            else if(fatPercent>13&&fatPercent<=17)//Fitness
                return R.drawable.ic_very_satisfied;
            else if(fatPercent>17&&fatPercent<=24)//Average
                return R.drawable.ic_satisfied;
            else if(fatPercent>25)//Obese
                return R.drawable.ic_very_dissatisfied;
        }
        return imgID;
    }


    private void FindView() {
        mDate=findViewById(R.id.d_date);
        mTitle=findViewById(R.id.d_title);
        mWeekNo=findViewById(R.id.d_weekno);
        mAge=findViewById(R.id.d_age);
        mWeight=findViewById(R.id.d_weight);
        mHeight=findViewById(R.id.d_height);
        mBurnRate=findViewById(R.id.d_BurnRate);
        mFatPercent=findViewById(R.id.d_fatPercent);
        mWaterPercent=findViewById(R.id.d_waterPercent);
        mComment=findViewById(R.id.d_comment);
        mgenderspiner=findViewById(R.id.d_gender);
        mBurnRateBTN=findViewById(R.id.d_burnRateProcces_btn);
        mEmojiRes=findViewById(R.id.d_emoji);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_btn:
                saveItem();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveItem() {
        //save
        if(process.equalsIgnoreCase("insert")) {
            ((ImageView)findViewById(R.id.save_btn)).setImageResource(R.drawable.ic_save);
            mBurnRateBTN.performClick();
            Item_Entity item = BuildItem();
            if (item != null)
                mViewModel.insert(item);
        }
        else//update
        {
            ((ImageView)findViewById(R.id.save_btn)).setImageResource(R.drawable.ic_system_update_alt_black_24dp);
            mBurnRateBTN.performClick();
            Item_Entity item = BuildItem();
            if (item != null) {
                item.setId(mItem.getId());
                mViewModel.update(item);
            }
        }
    }

    private Item_Entity BuildItem() {
        //Title, Weekno, Age, gender, Weight, Hight, BurnRate, FatPercent, WaterPercent, emojiRes, date, Comment,

        String  Title, gender, date, Comment;
        Integer Weekno,Age, emojiRes;
        Double Weight, Height, BurnRate, FatPercent, WaterPercent;
        Title=mTitle.getText().toString();
        gender=mgenderspiner.getSelectedItem().toString();
        date=mDate.getText().toString();
        Comment=mComment.getText().toString();
        Weekno=Integer.parseInt(mWeekNo.getText().toString());
        Age=Integer.parseInt(mAge.getText().toString());
        Weight=Double.valueOf(mWeight.getText().toString());
        Height=Double.valueOf(mHeight.getText().toString());
        BurnRate=Double.valueOf(mBurnRate.getText().toString());
        FatPercent=Double.valueOf(mFatPercent.getText().toString());
        WaterPercent=Double.valueOf(mWaterPercent.getText().toString());
        emojiRes=mEmojiRes.getId();
        if(Title.isEmpty()||Comment.isEmpty()||gender.isEmpty()||mAge.getText().toString().isEmpty()||mWeight.getText().toString().isEmpty()||mHeight.getText().toString().isEmpty()||mWeekNo.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill The Entire fields!!", Toast.LENGTH_SHORT).show();
            return null;
        }
        else
            return new Item_Entity(Title,Weekno,Age,gender,Weight,Height,BurnRate,FatPercent,WaterPercent,emojiRes,date,Comment);
    }
}
