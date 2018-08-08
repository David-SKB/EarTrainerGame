package com.davidsk.eartrainergame.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.davidsk.eartrainergame.Helper;
import com.davidsk.eartrainergame.R;
import com.davidsk.eartrainergame.model.PianoSounds;

public class MainActivity extends AppCompatActivity
{
    //Instance Variables
    PianoSounds sounds;
    Helper helper;
    ImageButton newGameButton, scoreButton, settingsButton, helpButton;
    ConstraintLayout currentLayout;
    Boolean firstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        PianoSounds.getInstance().init(this);
        Helper.getInstance().init(this);
        sounds = PianoSounds.getInstance();
        helper = Helper.getInstance();
        newGameButton = findViewById(R.id.newGameButton);
        scoreButton = findViewById(R.id.scoreButton);
        settingsButton = findViewById(R.id.settingsButton);
        helpButton = findViewById(R.id.helpButton);
        firstRun = helper.getFirstRun();
        newGameButton.setSoundEffectsEnabled(false);
        scoreButton.setSoundEffectsEnabled(false);
        settingsButton.setSoundEffectsEnabled(false);
        helpButton.setSoundEffectsEnabled(false);
        currentLayout = findViewById(R.id.main_activity);

        //Set Background
        if (helper.getBackground() == 1)
        {
            currentLayout.setBackgroundResource(R.drawable.background);
        }
        else
        {
            currentLayout.setBackgroundResource(R.drawable.darkbackground);
        }
    }

    public void startGame (View view)
    {
        if (firstRun)
        {
            firstRun = helper.getFirstRun();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Learn To Play");
            builder.setMessage("Would you like to view the help guide?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //Open Help Page
                            sounds.playSoundFX(PianoSounds.SOUND_CLICK);
                            Intent openHelp = new Intent(MainActivity.this, HelpActivity.class);
                            startActivity(openHelp);
                        }
                    });
            builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    //Start Game
                    sounds.playSoundFX(PianoSounds.SOUND_CLICK);
                    Intent NewGame = new Intent(MainActivity.this, NewGameActivity.class);
                    startActivity(NewGame);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            helper.getFirstRun();
        }
        else
        {
            sounds.playSoundFX(PianoSounds.SOUND_CLICK);
            Intent NewGame = new Intent(this, NewGameActivity.class);
            startActivity(NewGame);
        }
    }

    public void startScore (View view)
    {
        sounds.playSoundFX(PianoSounds.SOUND_CLICK);
        Intent myScore = new Intent(this, MyScoreActivity.class);
        startActivity(myScore);
    }

    public void startSettings (View view)
    {
        sounds.playSoundFX(PianoSounds.SOUND_CLICK);
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    public void startHelp (View view)
    {
        sounds.playSoundFX(PianoSounds.SOUND_CLICK);
        Intent openHelp = new Intent(this, HelpActivity.class);
        startActivity(openHelp);
    }

    @Override
    protected void onResume()
    {
        if (helper.getBackground() == 1)
        {
            currentLayout.setBackgroundResource(R.drawable.background);
        }
        else
        {
            currentLayout.setBackgroundResource(R.drawable.darkbackground);
        }
        super.onResume();
    }
}
