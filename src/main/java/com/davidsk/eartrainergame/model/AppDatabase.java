package com.davidsk.eartrainergame.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Score.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract ScoreDao scoreDao();
}
