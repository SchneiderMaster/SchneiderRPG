package com.example.schneider_rpg;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quest {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "skillId")
    public Integer skillId;

    @ColumnInfo(name = "max")
    public Integer max;

    @ColumnInfo(name = "progress")
    public Integer progress;

    @ColumnInfo(name = "reward")
    public Integer reward;
}
