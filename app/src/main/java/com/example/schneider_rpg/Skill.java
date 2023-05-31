package com.example.schneider_rpg;


import android.graphics.Color;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Skill {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "current experience")
    public Integer experience;

    @ColumnInfo(name = "current level")
    public Short level;

    @ColumnInfo(name = "color")
    public Integer color;




}
