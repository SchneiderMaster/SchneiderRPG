package com.example.schneider_rpg;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SkillDao {
    @Query("SELECT * FROM skill ORDER BY id")
    List<Skill> getAll();

    @Query("SELECT color FROM skill WHERE id = :id")
    Integer getColor(Integer id);

    @Query("SELECT COUNT(*) FROM skill")
    Integer count();

    @Query("UPDATE skill SET `current experience` = `current experience` + 10 WHERE id = :id")
    void addHour(Integer id);

    @Query("UPDATE skill SET `current level` = `current level` + 1 WHERE id = :id")
    void increaseLevel(Integer id);

    @Query("UPDATE skill SET `current experience` = :experience WHERE id = :id")
    void updateXP(int experience, int id);

    @Query("UPDATE skill SET `current experience` = `current experience` + :experience WHERE id = :id")
    void addXP(int experience, int id);

    @Insert
    void insert(Skill skill);

    @Query("SELECT id FROM skill WHERE name = :name")
    Integer getIdFromName(String name);

    @Query("SELECT name FROM skill WHERE id = :id")
    String getNameFromId(Integer id);

    @Query("SELECT color FROM skill WHERE id = :id")
    Integer getColorFromId(Integer id);

    @Query("SELECT name FROM skill ORDER BY id")
    List<String> getAllSkillNames();

}
