package com.example.followup.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "item")
public class Item_Entity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private Integer weekNo;
    private Integer age;
    private String gender;
    private Double weight;
    private Double height;
    private Double burn_rate;
    private Double fat_percent;
    private Double water_percent;
    private Integer emojiRes;
    private String Date;
    private String comment;

    public Item_Entity(String title, Integer weekNo, Integer age, String gender, Double weight, Double height, Double burn_rate, Double fat_percent, Double water_percent, Integer emojiRes, String date, String comment) {
        this.title = title;
        this.weekNo = weekNo;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.burn_rate = burn_rate;
        this.fat_percent = fat_percent;
        this.water_percent = water_percent;
        this.emojiRes = emojiRes;
        Date = date;
        this.comment = comment;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getWeekNo() {
        return weekNo;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    public Double getBurn_rate() {
        return burn_rate;
    }

    public Double getFat_percent() {
        return fat_percent;
    }

    public Double getWater_percent() {
        return water_percent;
    }

    public Integer getEmojiRes() {
        return emojiRes;
    }

    public String getDate() {
        return Date;
    }

    public String getComment() {
        return comment;
    }
}
