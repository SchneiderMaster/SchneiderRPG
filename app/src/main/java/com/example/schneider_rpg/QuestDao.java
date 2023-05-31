package com.example.schneider_rpg;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestDao {
    @Query("SELECT * FROM quest ORDER BY id")
    List<Quest> getAll();

    @Query("SELECT COUNT(*) FROM quest")
    Integer count();

    @Query("UPDATE quest SET progress = progress + 1 WHERE id = :id")
    void addProgress(Integer id);

    @Query("DELETE FROM quest WHERE id = :id")
    void deleteQuest(Integer id);

    @Insert
    void insert(Quest quest);


}
