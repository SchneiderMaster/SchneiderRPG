package com.example.schneider_rpg;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Quest.class}, version = 1)
public abstract class QuestAppDatabase extends RoomDatabase {
    public abstract QuestDao questDao();

}
