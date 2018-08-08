package com.davidsk.eartrainergame.controller;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.davidsk.eartrainergame.Helper;
import com.davidsk.eartrainergame.R;
import com.davidsk.eartrainergame.model.AppDatabase;
import com.davidsk.eartrainergame.model.PianoSounds;

import java.lang.ref.WeakReference;

public class SettingsActivity extends AppCompatActivity
{

    private AppDatabase db;
    private CheckBox fxBox;
    private ImageButton clearButton;
    private int vst;
    private RadioGroup vstGroup, themeGroup;
    private RadioButton pianoRadio, guitarRadio, lightRadio, darkRadio;
    private ConstraintLayout currentLayout, mainLayout;
    private TextView soundText, themeText;
    private SettingsActivity current = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "ear-trainer-db").build();

        fxBox = findViewById(R.id.fx_check_box);
        pianoRadio = findViewById(R.id.piano_radio_button);
        guitarRadio = findViewById(R.id.guitar_radio_button);
        clearButton = findViewById(R.id.clear_button);
        vstGroup = findViewById(R.id.vst_radio_group);
        themeGroup = findViewById(R.id.theme_radio_group);
        lightRadio = findViewById(R.id.light_radio_button);
        darkRadio = findViewById(R.id.dark_radio_button);
        soundText = findViewById(R.id.sount_text);
        themeText = findViewById(R.id.theme_text);

        currentLayout = findViewById(R.id.settings_activity);
        mainLayout = findViewById(R.id.main_activity);
        //Set Background
        if (Helper.getInstance().getBackground() == 1)
        {
            currentLayout.setBackgroundResource(R.drawable.background);
            soundText.setTextColor(Color.rgb(124, 68, 23));
            themeText.setTextColor(Color.rgb(124, 68, 23));
            pianoRadio.setTextColor(Color.rgb(124, 68, 23));
            guitarRadio.setTextColor(Color.rgb(124, 68, 23));
            fxBox.setTextColor(Color.rgb(124, 68, 23));
            lightRadio.setTextColor(Color.rgb(124, 68, 23));
            darkRadio.setTextColor(Color.rgb(124, 68, 23));
        }
        else
        {
            currentLayout.setBackgroundResource(R.drawable.darkbackground);
            soundText.setTextColor(Color.rgb(255, 218, 191));
            themeText.setTextColor(Color.rgb(255, 218, 191));
            pianoRadio.setTextColor(Color.rgb(255, 218, 191));
            guitarRadio.setTextColor(Color.rgb(255, 218, 191));
            fxBox.setTextColor(Color.rgb(255, 218, 191));
            lightRadio.setTextColor(Color.rgb(255, 218, 191));
            darkRadio.setTextColor(Color.rgb(255, 218, 191));
        }

        vst =  PianoSounds.getInstance().getVST();
        if (vst == 1)
        {
            pianoRadio.setChecked(true);
        }
        else
        {
            guitarRadio.setChecked(true);
        }

        if (PianoSounds.getInstance().getFX())
        {
            fxBox.setChecked(true);
        }
        else
        {
            fxBox.setChecked(false);
        }

        if (Helper.getInstance().getBackground() == 1)
        {
            lightRadio.setChecked(true);
        }
        else
        {
            darkRadio.setChecked(true);
        }

        vstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.piano_radio_button)
                {
                    PianoSounds.getInstance().setVST(1);
                }
                else if (i == R.id.guitar_radio_button)
                {
                    PianoSounds.getInstance().setVST(2);
                }
            }
        });

        themeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.light_radio_button)
                {
                    Helper.getInstance().setBackground(1);
                    currentLayout.setBackgroundResource(R.drawable.background);
                    soundText.setTextColor(Color.rgb(124, 68, 23));
                    themeText.setTextColor(Color.rgb(124, 68, 23));
                    pianoRadio.setTextColor(Color.rgb(124, 68, 23));
                    guitarRadio.setTextColor(Color.rgb(124, 68, 23));
                    fxBox.setTextColor(Color.rgb(124, 68, 23));
                    lightRadio.setTextColor(Color.rgb(124, 68, 23));
                    darkRadio.setTextColor(Color.rgb(124, 68, 23));
                }
                else if (i == R.id.dark_radio_button)
                {
                    Helper.getInstance().setBackground(2);
                    currentLayout.setBackgroundResource(R.drawable.darkbackground);
                    soundText.setTextColor(Color.rgb(255, 218, 191));
                    themeText.setTextColor(Color.rgb(255, 218, 191));
                    pianoRadio.setTextColor(Color.rgb(255, 218, 191));
                    guitarRadio.setTextColor(Color.rgb(255, 218, 191));
                    fxBox.setTextColor(Color.rgb(255, 218, 191));
                    lightRadio.setTextColor(Color.rgb(255, 218, 191));
                    darkRadio.setTextColor(Color.rgb(255, 218, 191));
                }
            }
        });

        fxBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fxBox.isChecked())
                {
                    PianoSounds.getInstance().toggleFX(true);
                    PianoSounds.getInstance().playSoundFX(PianoSounds.SOUND_CLICK);
                }
                else
                {
                    PianoSounds.getInstance().toggleFX(false);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Clear Data");
                builder.setMessage("Are you sure you wish to reset score data?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //CLEAR DATA
                                new dbDelete(current).execute();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CANCEL
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private static class dbDelete extends AsyncTask<Void, Void, String>
    {
        private WeakReference<SettingsActivity> activityReference;

        // only retain a weak reference to the activity
        dbDelete(SettingsActivity context)
        {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... params)
        {
            activityReference.get().db.scoreDao().deleteAll();
            return "finished";
        }

        @Override
        protected void onPostExecute(String result)
        {
            Helper.getInstance().output("Data has been cleared");
        }
    }







    /*@Override
    public void onBackPressed()
    {

        MainActivity.refresh();
        MainActivity.
        SettingsActivity.super.onBackPressed();

    }*/
}
