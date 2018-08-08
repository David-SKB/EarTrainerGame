package com.davidsk.eartrainergame.controller;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.davidsk.eartrainergame.Helper;
import com.davidsk.eartrainergame.R;

public class HelpActivity extends AppCompatActivity
{

    ConstraintLayout currentLayout;

    private TextView earTitle, earText, newGameTitle, newGameText, myScoresTitle, myScoresText, settingsTitle, settingsText, pianoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        currentLayout = findViewById(R.id.help_activity);

        earTitle = findViewById(R.id.ear_title);
        earText = findViewById(R.id.ear_text);
        newGameTitle = findViewById(R.id.new_game_title);
        newGameText = findViewById(R.id.new_game_text);
        myScoresTitle = findViewById(R.id.my_scores_title);
        myScoresText = findViewById(R.id.my_scores_text);
        settingsTitle = findViewById(R.id.settings_title);
        settingsText = findViewById(R.id.settings_text);
        pianoTitle = findViewById(R.id.piano_title);
        //PIANO IMAGE
        //http://www.piano-keyboard-guide.com/piano-keyboard-layout.html
    }




    @Override
    protected void onResume()
    {
        if (Helper.getInstance().getBackground() == 1)
        {
            currentLayout.setBackgroundResource(R.drawable.background);
            earTitle.setTextColor(Color.rgb(124, 68, 23));
            earText.setTextColor(Color.rgb(45, 22, 3));
            newGameTitle.setTextColor(Color.rgb(124, 68, 23));
            newGameText.setTextColor(Color.rgb(45, 22, 3));
            myScoresTitle.setTextColor(Color.rgb(124, 68, 23));
            myScoresText.setTextColor(Color.rgb(45, 22, 3));
            settingsTitle.setTextColor(Color.rgb(124, 68, 23));
            settingsText.setTextColor(Color.rgb(45, 22, 3));
            pianoTitle.setTextColor(Color.rgb(124, 68, 23));
        }
        else
        {
            currentLayout.setBackgroundResource(R.drawable.darkbackground);
            earTitle.setTextColor(Color.rgb(255, 218, 191));
            earText.setTextColor(Color.rgb(255, 218, 191));
            newGameTitle.setTextColor(Color.rgb(255, 218, 191));
            newGameText.setTextColor(Color.rgb(255, 218, 191));
            myScoresTitle.setTextColor(Color.rgb(255, 218, 191));
            myScoresText.setTextColor(Color.rgb(255, 218, 191));
            settingsTitle.setTextColor(Color.rgb(255, 218, 191));
            settingsText.setTextColor(Color.rgb(255, 218, 191));
            pianoTitle.setTextColor(Color.rgb(255, 218, 191));
        }
        super.onResume();
    }
}
