package com.davidsk.eartrainergame.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.davidsk.eartrainergame.model.Score;

import java.util.List;



@Dao
public interface ScoreDao
{


    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Insert
    void insertAll(Score... scores);

    @Delete
    void delete(Score score);

    @Query("DELETE FROM score")
    void deleteAll();

}
