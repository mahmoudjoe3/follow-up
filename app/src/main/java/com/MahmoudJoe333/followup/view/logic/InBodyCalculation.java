package com.MahmoudJoe333.followup.view.logic;


import android.content.res.Resources;

import com.MahmoudJoe333.followup.R;

import java.text.DecimalFormat;


public class InBodyCalculation {
    private String[] genderArr;
    private String[] data;
    public InBodyCalculation(Resources r) {
        genderArr=r.getStringArray(R.array.gender);
        data=r.getStringArray(R.array.inBody_data);
    }
    public double getBurnRate(Double height,Double weight,Integer age,String gender) {
        double BMR=0;
        if(height<3)
            height*=100;
        if(gender.equalsIgnoreCase(genderArr[0])) {
            BMR=88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        }
        else if(gender.equalsIgnoreCase(genderArr[1])) {
            BMR=655.0955 + (9.5634 * weight) + (1.8496 * height) - (4.6756 * age );
        }
        return BMR;
    }
    public double getFatPercent(Double height,Double weight,Integer age,String gender) {
        double BFP=0;
        if(height>4)height/=100;
        double BMI=weight/Math.pow(height,2);
        int sex;
        //sex 0 for female 1 for male
        sex = (gender.equalsIgnoreCase(genderArr[0])) ? 1:0;

        BFP = (age<=15) ?
                (1.51*BMI)-(0.70*age)-(3.6*sex)+1.4 :
                (1.20 * BMI) + (0.23 * age) - (10.8 * sex) - 5.4;

        return BFP;
    }
    public double getWaterPercent(Double height,Double weight,Integer age,String gender){
        double BWP=0;
        if(height<10)
            height*=100;
        int sex=(gender.equalsIgnoreCase(genderArr[0])) ? 1:0;
        BWP = 1.485+(0.001518*weight*height)-(0.0007872*Math.pow(age,2))+(0.349*weight)-(0.00199*Math.pow(weight,2))+(0.06611*sex*weight)+(0.0002861*height*weight);
        return BWP;
    }
    private double getCalorieIntake(Double height,Double weight,Integer age,String gender,int level){
        double CalPerDay=0;
        double BMR=getBurnRate(height,weight,age,gender);
        switch (level){
            case 1:
                CalPerDay=BMR*1.375;
                break;
            case 2:
                CalPerDay=BMR*1.55;
                break;
            case 3:
                CalPerDay=BMR*1.725;
                break;
            case 4:
                CalPerDay=BMR*1.9;
                break;
            default:
                CalPerDay=BMR*1.2;
        }
        return CalPerDay;
    }
    private String getDietTime(Double fat,String gender,double calperday){
        double k;
        boolean up=false;
        double r_up=0,r_down=0;
        if(gender.equalsIgnoreCase(genderArr[1])){
            r_up=24;
            r_down=21;
        }
        else{
            r_up=17;
            r_down=14;
        }

        if(fat<r_down) {
            k=(r_down-fat)+1;
            up=true;
        }
        else if (fat>r_up) {
            k = (fat - r_up)+1;
            up=false;
        }
        else k=0;

        DecimalFormat format=new DecimalFormat("#.0");
        double calPerKG=k*7700;
        String val;
        String instraction="";
      /*0  <item>You need </item>
        1   <item> Weeks to get the fitness body</item>
        2  <item>2.To lose 0.5 kg/week: </item>
        3  <item> Kcal/day</item>
        4  <item>3.To lose 1 kg/week: </item>
        5  <item>2.To gain 0.5 kg/week:</item>
        6  <item>3.To gain 1 kg/week</item>
        7  <item>Your normal Calories is </item>
        8  <item>1.To maintain your weight: </item>
        9  <item>its better to mentain your weight</item>*/
        if(!up&&k!=0) {
            val=data[0]+" "+Math.floor(calPerKG/(500*7))+" "+data[1];
            instraction+=data[2]+" "+replaceArabicNumbers(format.format((calperday-500)))+" "+data[3]+"\n"+val+"\n\n";
            val=data[0]+" "+Math.floor(calPerKG/(1000*7))+" "+data[1];
            instraction+=data[4]+replaceArabicNumbers(format.format((calperday-1000)))+" "+data[3]+"\n"+val+"\n\n";
        }
        else if(up&&k!=0) {
            val= data[0]+" "+Math.floor(calPerKG/(500*7))+" "+data[1];
            instraction+=data[5]+" "+replaceArabicNumbers(format.format((calperday+500)))+" "+data[3]+"\n"+val+"\n\n";
            val=data[0]+" "+Math.floor(calPerKG/(1000*7))+" "+data[1];
            instraction+=data[6]+" "+replaceArabicNumbers(format.format((calperday+1000)))+" "+data[3]+"\n"+val+"\n";
        }
        else
            instraction+=data[9];

        return instraction;
    }
    public String getInstruction(Double height,Double weight,Integer age,String gender,int level,Double fatPercent)
    {
        DecimalFormat format=new DecimalFormat("#.0");
        double calperday= replaceArabicNumbers(format.format(getCalorieIntake(height,weight,age,gender,level)));
        String instraction=data[7]+" "+calperday+" "+data[3]+"\n\n";
        instraction+=data[8]+" "+calperday+" "+data[3]+"\n\n";
        instraction+=getDietTime(fatPercent,gender,calperday);
        return instraction;
    }


    public static double replaceArabicNumbers(String original) {
            String o=original.replaceAll("٠","0")
                    .replaceAll("١","1")
                    .replaceAll("٢","2")
                    .replaceAll("٣","3")
                    .replaceAll("٤","4")
                    .replaceAll("٥","5")
                    .replaceAll("٦","6")
                    .replaceAll("٧","7")
                    .replaceAll("٨","8")
                    .replaceAll("٩","9")
                    .replaceAll("٫",".");
            return Double.parseDouble(o);
    }
}

/* references
https://en.wikipedia.org/wiki/Body_fat_percentage


 */