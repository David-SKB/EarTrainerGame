package com.davidsk.eartrainergame.model;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Score
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "rounds")
    private int rounds;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getRounds()
    {
        return rounds;
    }

    public void setRounds(int rounds)
    {
        this.rounds = rounds;
    }
}
