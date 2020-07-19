package com.MahmoudJoe333.followup.view.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.MahmoudJoe333.followup.Model.Item_Entity;
import com.MahmoudJoe333.followup.R;
import com.MahmoudJoe333.followup.view.logic.InBodyCalculation;
import com.MahmoudJoe333.followup.viewModel.DetailsActivity_ViewModel;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class detailsActivity extends AppCompatActivity {

    @BindView(R.id.d_date)
    TextView mDate;
    @BindView(R.id.d_hint)
    TextView mHint;
    @BindView(R.id.d_emoji)
    ImageView mEmojiRes;
    @BindView(R.id.d_title)
    EditText mTitle;
    @BindView(R.id.d_age)
    EditText mAge;
    @BindView(R.id.d_weekno)
    EditText mWeekNo;
    @BindView(R.id.d_gender)
    Spinner mgenderspiner;
    @BindView(R.id.d_weight)
    EditText mWeight;
    @BindView(R.id.d_height)
    EditText mHeight;
    @BindView(R.id.d_level)
    Spinner mlevelSpiner;
    @BindView(R.id.d_burnRateProcces_btn)
    Button mBurnRateBTN;
    @BindView(R.id.d_BurnRate)
    EditText mBurnRate;
    @BindView(R.id.d_fatPercent)
    EditText mFatPercent;
    @BindView(R.id.d_waterPercent)
    EditText mWaterPercent;
    @BindView(R.id.d_comment)
    EditText mComment;
    private DetailsActivity_ViewModel mViewModel;
    public final static String EXTRA_ITEM_KEY = "com.example.followup.view.ui.EXTRA_ITEM";
    public final static String EXTRA_PROCESS_TYPE = "com.example.followup.view.ui.PROCESS_TYPE";
    private Item_Entity mItem;
    private String process = "";
    private String hint = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        mViewModel = new ViewModelProvider(this).get(DetailsActivity_ViewModel.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_exit);

        InBodyProcess();

        if (getIntent().hasExtra(EXTRA_PROCESS_TYPE)) {
            process = getIntent().getStringExtra(EXTRA_PROCESS_TYPE);
            if (process.equalsIgnoreCase("update")) {
                setTitle(getResources().getString(R.string.datails_Title_update));
                mItem = (Item_Entity) getIntent().getSerializableExtra(EXTRA_ITEM_KEY);
                AssignValues();
                mBurnRateBTN.performClick();
            } else {
                setTitle(getResources().getString(R.string.datails_Title_insert));
                //date
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                mDate.setText(currentDate);
            }
        }


    }

    private void InBodyProcess() {
        mBurnRateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWaterPercent.setEnabled(true);
                mBurnRate.setEnabled(true);
                mFatPercent.setEnabled(true);
                mComment.setEnabled(true);

                String gender = mgenderspiner.getSelectedItem().toString();
                int level = mlevelSpiner.getSelectedItemPosition();
                if (gender.isEmpty() || mHeight.getText().toString().isEmpty() || mWeight.getText().toString().isEmpty() || mAge.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill The Entire fields!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double height = Double.valueOf(mHeight.getText().toString());
                Double weight = Double.valueOf(mWeight.getText().toString());
                Integer age = Integer.parseInt(mAge.getText().toString());

                InBodyCalculation calculation = new InBodyCalculation(getResources());
                double BurnRate = calculation.getBurnRate(height, weight, age, gender);
                double FatPercent = calculation.getFatPercent(height, weight, age, gender);
                double WaterPercent = calculation.getWaterPercent(height, weight, age, gender);
                String instruction = calculation.getInstruction(height, weight, age, gender, level, FatPercent);

                DecimalFormat format = new DecimalFormat("#");

                mBurnRate.setText(String.valueOf(format.format(BurnRate)));
                mFatPercent.setText(String.valueOf(format.format(FatPercent)));
                mWaterPercent.setText(String.valueOf(format.format(WaterPercent)));
                mEmojiRes.setImageResource(getEmojiSetHint(calculation.replaceArabicNumbers(format.format(FatPercent)), gender));
                mHint.setText(hint);

                if (!save)
                    mComment.setText(instruction);
            }
        });
    }

    private void AssignValues() {

        mTitle.setText(mItem.getTitle());
        mWeekNo.setText("" + mItem.getWeekNo());
        mAge.setText("" + mItem.getAge());
        mWeight.setText(String.valueOf(mItem.getWeight()));
        int pos = (mItem.getGender().equalsIgnoreCase("male")) ? 0 : 1;
        mgenderspiner.setSelection(pos);
        mlevelSpiner.setSelection(mItem.getLevel());
        mDate.setText(String.valueOf(mItem.getDate()));
        mHeight.setText(String.valueOf(mItem.getHeight()));
        mBurnRate.setText(String.valueOf(mItem.getBurn_rate()));
        mFatPercent.setText(String.valueOf(mItem.getFat_percent()));
        mWaterPercent.setText(String.valueOf(mItem.getWater_percent()));
        mComment.setText(String.valueOf(mItem.getComment()));
        mEmojiRes.setImageResource(mItem.getEmojiRes());
        getEmojiSetHint(Double.parseDouble(mFatPercent.getText().toString()), mgenderspiner.getSelectedItem().toString());
        mHint.setText(hint);
    }

    private int getEmojiSetHint(double fatPercent, String gender) {

        int imgID = R.drawable.ic_dead;
        if (gender.equalsIgnoreCase("female")) {
            if (fatPercent < 10)//dead
            {
                hint = "you are near to Die.";
                mHint.setTextColor(getResources().getColor(R.color.color4));
                return R.drawable.ic_dead;
            } else if (fatPercent >= 10 && fatPercent <= 13)//Essential  fat
            {
                hint = "you are too thin ";
                mHint.setTextColor(getResources().getColor(R.color.color4));
                return R.drawable.ic_very_dissatisfied;
            } else if (fatPercent > 13 && fatPercent <= 20)//Athletes
            {
                mHint.setTextColor(getResources().getColor(R.color.color3));
                hint = "Athletes, but preferred to gain some fat";
                return R.drawable.ic_less_satisfied;
            } else if (fatPercent > 20 && fatPercent <= 24)//Fitness
            {
                hint = "you have the perfect body ";
                mHint.setTextColor(getResources().getColor(R.color.color1));
                return R.drawable.ic_very_satisfied;
            } else if (fatPercent > 24 && fatPercent <= 31)//Average
            {
                hint = "you are in Average";
                mHint.setTextColor(getResources().getColor(R.color.color2));
                return R.drawable.ic_satisfied;
            } else if (fatPercent > 31)//Obese
            {
                hint = "its preferred to loss some weight";
                mHint.setTextColor(getResources().getColor(R.color.color4));
                return R.drawable.ic_very_dissatisfied;
            }
        } else {
            if (fatPercent < 2)//dead
            {
                hint = "you are near to Die.";
                mHint.setTextColor(getResources().getColor(R.color.color4));
                return R.drawable.ic_dead;
            } else if (fatPercent >= 2 && fatPercent <= 5)//Essential  fat
            {
                mHint.setTextColor(getResources().getColor(R.color.color4));
                hint = "you are too thin ";
                return R.drawable.ic_very_dissatisfied;
            } else if (fatPercent > 5 && fatPercent <= 13)//Athletes
            {
                mHint.setTextColor(getResources().getColor(R.color.color3));
                hint = "Athletes, but preferred to gain some fat";
                return R.drawable.ic_less_satisfied;
            } else if (fatPercent > 13 && fatPercent <= 17)//Fitness
            {
                mHint.setTextColor(getResources().getColor(R.color.color1));
                hint = "you have the perfect body ";
                return R.drawable.ic_very_satisfied;
            } else if (fatPercent > 17 && fatPercent <= 24)//Average
            {
                mHint.setTextColor(getResources().getColor(R.color.color2));
                hint = "you are in Average";
                return R.drawable.ic_satisfied;
            } else if (fatPercent > 25)//Obese
            {
                mHint.setTextColor(getResources().getColor(R.color.color4));
                hint = "its preferred to loss some weight";
                return R.drawable.ic_very_dissatisfied;
            }
        }

        return imgID;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem item = menu.getItem(0);
        if (process.equalsIgnoreCase("insert"))
            item.setIcon(R.drawable.ic_save);
        else
            item.setIcon(R.drawable.ic_system_update_alt_black_24dp);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_btn:
                saveItem(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean save = false;

    @SuppressLint("RestrictedApi")
    private void saveItem(MenuItem itemview) {
        //save
        if (process.equalsIgnoreCase("insert")) {
            save = true;
            mBurnRateBTN.performClick();
            Item_Entity item = BuildItem();
            if (item != null) {
                mViewModel.insert(item);
                finish();
            }
        } else//update
        {
            save = true;
            mBurnRateBTN.performClick();
            Item_Entity item = BuildItem();
            if (item != null) {
                item.setId(mItem.getId());
                mViewModel.update(item);
                finish();
            }
        }

    }

    private Item_Entity BuildItem() {
        //Title, Weekno, Age, gender, Weight, Hight, BurnRate, FatPercent, WaterPercent, emojiRes, date, Comment,

        String Title, gender, date, Comment;
        Integer Weekno, Age, emojiRes, level;
        Double Weight, Height, BurnRate, FatPercent, WaterPercent;

        Title = mTitle.getText().toString();
        gender = mgenderspiner.getSelectedItem().toString();
        date = mDate.getText().toString();
        Comment = mComment.getText().toString();
        Weekno = Integer.parseInt(mWeekNo.getText().toString());
        Age = Integer.parseInt(mAge.getText().toString());
        Weight = Double.valueOf(mWeight.getText().toString());
        Height = Double.valueOf(mHeight.getText().toString());
        BurnRate = InBodyCalculation.replaceArabicNumbers(mBurnRate.getText().toString());
        FatPercent = InBodyCalculation.replaceArabicNumbers(mFatPercent.getText().toString());
        WaterPercent = InBodyCalculation.replaceArabicNumbers(mWaterPercent.getText().toString());
        emojiRes = getEmojiSetHint(FatPercent, gender);
        level = mlevelSpiner.getSelectedItemPosition();
        if (Title.isEmpty() || Comment.isEmpty() || gender.isEmpty() || mAge.getText().toString().isEmpty() || mWeight.getText().toString().isEmpty() || mHeight.getText().toString().isEmpty() || mWeekNo.getText().toString().isEmpty()) {
            Toast.makeText(this, "Fill The Entire fields!!", Toast.LENGTH_SHORT).show();
            return null;
        } else
            return new Item_Entity(Title, Weekno, Age, gender, Weight, Height, BurnRate, FatPercent, WaterPercent, emojiRes, date, Comment, level);
    }
}
