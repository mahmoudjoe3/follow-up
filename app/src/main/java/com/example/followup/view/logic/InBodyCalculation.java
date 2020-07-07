package com.example.followup.view.logic;

import java.text.DecimalFormat;

public class InBodyCalculation {
    public InBodyCalculation() {
    }
    public static double getBurnRate(Double height,Double weight,Integer age,String gender) {
        double BMR=0;
        if(height<3)
            height*=100;
        if(gender.equalsIgnoreCase("male")) {
            BMR=88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        }
        else if(gender.equalsIgnoreCase("female")) {
            BMR=655.0955 + (9.5634 * weight) + (1.8496 * height) - (4.6756 * age );
        }
        return BMR;
    }
    public static double getFatPercent(Double height,Double weight,Integer age,String gender) {
        double BFP=0;
        if(height>4)height/=100;
        double BMI=weight/Math.pow(height,2);
        int sex;
        //sex 0 for female 1 for male
        sex = (gender.equalsIgnoreCase("male")) ? 1:0;

        BFP = (age<=15) ?
                (1.51*BMI)-(0.70*age)-(3.6*sex)+1.4 :
                (1.20 * BMI) + (0.23 * age) - (10.8 * sex) - 5.4;

        return BFP;
    }
    public static double getWaterPercent(Double height,Double weight,Integer age,String gender){
        double BWP=0;
        if(height<10)
            height*=100;
        int sex=(gender.equalsIgnoreCase("male")) ? 1:0;
        BWP = 1.485+(0.001518*weight*height)-(0.0007872*Math.pow(age,2))+(0.349*weight)-(0.00199*Math.pow(weight,2))+(0.06611*sex*weight)+(0.0002861*height*weight);
        return BWP;
    }
    private static double getCalorieIntake(Double height,Double weight,Integer age,String gender,int level){
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
    private static String getDietTime(Double fat,String gender,double calperday){
        double k;
        boolean up=false;
        double r_up=0,r_down=0;
        if(gender.equalsIgnoreCase("female")){
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
        if(!up) {
            val="You need "+Math.floor(calPerKG/(500*7))+" Weeks to get the fitness body";
            instraction+="2.To lose 0.5 kg/week: "+Double.parseDouble(format.format((calperday-500)))+" Kcal/day\n"+val+"\n\n";
            val="You need "+Math.floor(calPerKG/(1000*7))+" Weeks to get the fitness body";
            instraction+="3.To lose 1 kg/week: "+Double.parseDouble(format.format((calperday-1000)))+" Kcal/day\n"+val+"\n\n";
        }
        else {
            val= "You need "+Math.floor(calPerKG/(500*7))+" Weeks to get the fitness body";
            instraction+="4.To gain 0.5 kg/week:"+Double.parseDouble(format.format((calperday+500)))+" Kcal/day\n"+val+"\n\n";
            val="You need "+Math.floor(calPerKG/(1000*7))+" Weeks to get the fitness body";
            instraction+="5.To gain 1 kg/week"+Double.parseDouble(format.format((calperday+1000)))+" Kcal/day\n"+val+"\n";

        }

        return instraction;
    }
    public static String getInstruction(Double height,Double weight,Integer age,String gender,int level,Double fatPercent)
    {
        DecimalFormat format=new DecimalFormat("#.0");
        double calperday= Double.parseDouble(format.format(getCalorieIntake(height,weight,age,gender,level)));
        String instraction="Your normal Calories is "+calperday+" Kcal/day\n\n";
        instraction+="1.To maintain your weight: "+calperday+" Kcal/day\n\n";
        instraction+=getDietTime(fatPercent,gender,calperday);
        return instraction;
    }
}





/* references
https://en.wikipedia.org/wiki/Body_fat_percentage


 */