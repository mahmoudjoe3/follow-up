package com.example.followup.view.logic;

public class InBodyCalculation {
    public InBodyCalculation() {
    }
    public static double getBurnRate(Double height,Double weight,Integer age,String gender) {
        double burning_Rate=0;
        if(gender.equalsIgnoreCase("male")) {
            burning_Rate=(10*weight)+(6.25*height)-(5*age)-161;
        }
        else if(gender.equalsIgnoreCase("female")) {
            burning_Rate=(10*weight)+(6.25*height)-(5*age)+5;
        }
        return burning_Rate;
    }
    public static double getFatPercent(Double height,Double weight,Integer age,String gender) {
        double BFP=0;
        double BMI=weight/Math.pow(height,2);
        int sex;
        //sex 0 for female 1 for male
        sex = (gender.equalsIgnoreCase("male")) ? 1:0;

        BFP = (age<=15) ? (1.51*BMI)-(0.70*age)-(3.6*sex)+1.4 : (1.39*BMI)+(0.16*age)-(10.34*sex)-9;

        return BFP;
    }
    public static double getWaterPercent(Double height,Double weight,Integer age,String gender){
        double BWP=0;
        height*=100;
        int sex=(gender.equalsIgnoreCase("male")) ? 1:0;
        BWP = 1.485+(0.001518*weight*height)-(0.0007872*Math.pow(age,2))+(0.349*weight)-(0.00199*Math.pow(weight,2))+(0.06611*sex*weight)+(0.0002861*height*weight);
        return BWP;
    }
}





/* references
https://en.wikipedia.org/wiki/Body_fat_percentage


 */