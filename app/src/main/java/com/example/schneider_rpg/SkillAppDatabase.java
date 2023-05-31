package com.example.schneider_rpg;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Skill.class}, version = 1)
public abstract class SkillAppDatabase extends RoomDatabase{
    public abstract SkillDao skillDao();

}
