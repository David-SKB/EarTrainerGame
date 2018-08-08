package com.davidsk.eartrainergame;

import android.content.Context;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.davidsk.eartrainergame.model.PianoSounds;

/**
 * @author do302
 * initialise instance with:
 * private static Helper EC = Helper.getInstance();
 */
public class Helper extends AppCompatActivity
{
    PianoSounds PS;
    private int background;
    private SharedPreferences sharedPref;
    private boolean firstRun;


    private static final Helper INSTANCE = new Helper();

    private Helper() {
    }

    public static Helper getInstance() {
        return INSTANCE;
    }

    private Context context;

    public void init(Context context)
    {
        this.context = context;
        PS = PianoSounds.getInstance();

        sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        background = sharedPref.getInt("background", 1);
        firstRun = sharedPref.getBoolean("first_run", true);
    }

    public void setBackground(int x)
    {
        background = x;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("background", x);
        editor.apply();
    }

    public int getBackground()
    {
        return background;
    }

    public boolean getFirstRun()
    {
        boolean value;
        if (firstRun)
        {
            value = true;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("first_run", false);
            editor.apply();
            firstRun = false;
        }
        else
        {
            value = false;
        }
        return value;
    }


    public void output(String msg)
    {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public String getNoteString(int i)
    {
        String value;
        switch (i)
        {
            case 1:  value = "C";
                break;
            case 2:  value = "C#";
                break;
            case 3:  value = "D";
                break;
            case 4:  value = "D#";
                break;
            case 5:  value = "E";
                break;
            case 6:  value = "F";
                break;
            case 7:  value = "F#";
                break;
            case 8:  value = "G";
                break;
            case 9:  value = "G#";
                break;
            case 10: value = "A";
                break;
            case 11: value = "A#";
                break;
            case 12: value = "B";
                break;
            case 13: value = "C2";
                break;
            default: value = "error";
                break;
        }
        return value;
    }

    public int getNoteInt(String i)
    {
        int value;
        switch (i)
        {
            case "C":  value = 1;
                break;
            case "C#":  value = 2;
                break;
            case "D":  value = 3;
                break;
            case "D#":  value = 4;
                break;
            case "E":  value = 5;
                break;
            case "F":  value = 6;
                break;
            case "F#":  value = 7;
                break;
            case "G":  value = 8;
                break;
            case "G#":  value = 9;
                break;
            case "A": value = 10;
                break;
            case "A#": value = 11;
                break;
            case "B": value = 12;
                break;
            case "C2": value = 13;
                break;
            default: value = -1;
                break;
        }
        return value;
    }

    public int playSoundID(int i)
    {
        switch (i)
        {
            case 1:  return PS.playSound(PianoSounds.SOUND_1);
            case 2:  return PS.playSound(PianoSounds.SOUND_2);
            case 3:  return PS.playSound(PianoSounds.SOUND_3);
            case 4:  return PS.playSound(PianoSounds.SOUND_4);
            case 5:  return PS.playSound(PianoSounds.SOUND_5);
            case 6:  return PS.playSound(PianoSounds.SOUND_6);
            case 7:  return PS.playSound(PianoSounds.SOUND_7);
            case 8:  return PS.playSound(PianoSounds.SOUND_8);
            case 9:  return PS.playSound(PianoSounds.SOUND_9);
            case 10: return PS.playSound(PianoSounds.SOUND_10);
            case 11: return PS.playSound(PianoSounds.SOUND_11);
            case 12: return PS.playSound(PianoSounds.SOUND_12);
            case 13: return PS.playSound(PianoSounds.SOUND_13);
            case 14: return PS.playSound(PianoSounds.SOUND_14);
            case 15: return PS.playSound(PianoSounds.SOUND_15);
            case 16: return PS.playSound(PianoSounds.SOUND_16);
            case 17: return PS.playSound(PianoSounds.SOUND_17);
            case 18: return PS.playSound(PianoSounds.SOUND_18);
            case 19: return PS.playSound(PianoSounds.SOUND_19);
            case 20: return PS.playSound(PianoSounds.SOUND_20);
            case 21: return PS.playSound(PianoSounds.SOUND_21);
            case 22: return PS.playSound(PianoSounds.SOUND_22);
            case 23: return PS.playSound(PianoSounds.SOUND_23);
            case 24: return PS.playSound(PianoSounds.SOUND_24);
            case 25: return PS.playSound(PianoSounds.SOUND_25);
            case 26: return PS.playSound(PianoSounds.SOUND_26);
            case 27: return PS.playSound(PianoSounds.SOUND_CLICK);
            case 28: return PS.playSound(PianoSounds.SOUND_START);
        }
        return 0;
    }


}
