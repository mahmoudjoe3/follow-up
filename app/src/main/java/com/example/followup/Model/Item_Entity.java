package com.example.followup.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class Item_Entity {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private Integer age;
    private Integer weight;
    private Integer height;
    private Integer burn_rate;
    private Integer fat_rate;
    private Integer water_rate;
    private Integer emoji_imgRes;

    public Item_Entity(String name, Integer age, Integer weight, Integer height, Integer burn_rate, Integer fat_rate, Integer water_rate, Integer emoji_imgRes) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.burn_rate = burn_rate;
        this.fat_rate = fat_rate;
        this.water_rate = water_rate;
        this.emoji_imgRes = emoji_imgRes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getBurn_rate() {
        return burn_rate;
    }

    public Integer getFat_rate() {
        return fat_rate;
    }

    public Integer getWater_rate() {
        return water_rate;
    }

    public Integer getEmoji_imgRes() {
        return emoji_imgRes;
    }
}
