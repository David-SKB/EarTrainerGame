package com.davidsk.eartrainergame.model;

import java.util.HashMap;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;

import com.davidsk.eartrainergame.R;

public class PianoSounds {

    //private static final String TAG = PianoSounds.class.toString();
    private static final PianoSounds INSTANCE = new PianoSounds();

    //Sound ID's
    public static final int SOUND_1 = 1;
    public static final int SOUND_2 = 2;
    public static final int SOUND_3 = 3;
    public static final int SOUND_4 = 4;
    public static final int SOUND_5 = 5;
    public static final int SOUND_6 = 6;
    public static final int SOUND_7 = 7;
    public static final int SOUND_8 = 8;
    public static final int SOUND_9 = 9;
    public static final int SOUND_10 = 10;
    public static final int SOUND_11 = 11;
    public static final int SOUND_12 = 12;
    public static final int SOUND_13 = 13;
    public static final int SOUND_14 = 14;
    public static final int SOUND_15 = 15;
    public static final int SOUND_16 = 16;
    public static final int SOUND_17 = 17;
    public static final int SOUND_18 = 18;
    public static final int SOUND_19 = 19;
    public static final int SOUND_20 = 20;
    public static final int SOUND_21 = 21;
    public static final int SOUND_22 = 22;
    public static final int SOUND_23 = 23;
    public static final int SOUND_24 = 24;
    public static final int SOUND_25 = 25;
    public static final int SOUND_26 = 26;

    public static final int SOUND_CLICK = 27;
    public static final int SOUND_START = 28;

    private boolean fx;
    private int vst;
    SharedPreferences sharedPref;
    private PianoSounds() {

    }

    public static PianoSounds getInstance() {
        return INSTANCE;
    }

    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
    int priority = 1;
    int no_loop = 0;
    private int volume;
    float normal_playback_rate = 1f;

    private Context context;

    public void init(Context context) {
        this.context = context;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();

        sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        fx = sharedPref.getBoolean("fx", true);
        vst = sharedPref.getInt("vst", 1);

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //volume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        volume = 6;

        //Map sound files
        //Piano
        soundPoolMap.put(SOUND_1, soundPool.load(context, R.raw.c_5, priority));
        soundPoolMap.put(SOUND_2, soundPool.load(context, R.raw.d_b_5, priority));
        soundPoolMap.put(SOUND_3, soundPool.load(context, R.raw.d_5, priority));
        soundPoolMap.put(SOUND_4, soundPool.load(context, R.raw.e_b_5, priority));
        soundPoolMap.put(SOUND_5, soundPool.load(context, R.raw.e_5, priority));
        soundPoolMap.put(SOUND_6, soundPool.load(context, R.raw.f_5, priority));
        soundPoolMap.put(SOUND_7, soundPool.load(context, R.raw.g_b_5, priority));
        soundPoolMap.put(SOUND_8, soundPool.load(context, R.raw.g_5, priority));
        soundPoolMap.put(SOUND_9, soundPool.load(context, R.raw.a_b_5, priority));
        soundPoolMap.put(SOUND_10, soundPool.load(context, R.raw.a_5, priority));
        soundPoolMap.put(SOUND_11, soundPool.load(context, R.raw.b_b_5, priority));
        soundPoolMap.put(SOUND_12, soundPool.load(context, R.raw.b_5, priority));
        soundPoolMap.put(SOUND_13, soundPool.load(context, R.raw.c_6, priority));
        //Guitar
        soundPoolMap.put(SOUND_14, soundPool.load(context, R.raw.c_5_bell, priority));
        soundPoolMap.put(SOUND_15, soundPool.load(context, R.raw.d_b_5_bell, priority));
        soundPoolMap.put(SOUND_16, soundPool.load(context, R.raw.d_5_bell, priority));
        soundPoolMap.put(SOUND_17, soundPool.load(context, R.raw.e_b_5_bell, priority));
        soundPoolMap.put(SOUND_18, soundPool.load(context, R.raw.e_5_bell, priority));
        soundPoolMap.put(SOUND_19, soundPool.load(context, R.raw.f_5_bell, priority));
        soundPoolMap.put(SOUND_20, soundPool.load(context, R.raw.g_b_5_bell, priority));
        soundPoolMap.put(SOUND_21, soundPool.load(context, R.raw.g_5_bell, priority));
        soundPoolMap.put(SOUND_22, soundPool.load(context, R.raw.a_b_5_bell, priority));
        soundPoolMap.put(SOUND_23, soundPool.load(context, R.raw.a_5_bell, priority));
        soundPoolMap.put(SOUND_24, soundPool.load(context, R.raw.b_b_5_bell, priority));
        soundPoolMap.put(SOUND_25, soundPool.load(context, R.raw.b_5_bell, priority));
        soundPoolMap.put(SOUND_26, soundPool.load(context, R.raw.c_6_bell, priority));
        //FX
        soundPoolMap.put(SOUND_CLICK, soundPool.load(context, R.raw.rim_mixed, priority));
        soundPoolMap.put(SOUND_START, soundPool.load(context, R.raw.rim_echo_mixed, priority));
    }

    public int playSound(int soundId)
    {
        return soundPool.play(soundId, volume, volume, priority, no_loop, normal_playback_rate);
    }

    public int playSoundFX(int soundId)
    {
        if (fx)
        {
            return soundPool.play(soundId, volume, volume, priority, no_loop, normal_playback_rate);
        }
        return soundPool.play(soundId, 0f, 0f, priority, no_loop, normal_playback_rate);
    }

    public void stopSound(int streamId) {
        soundPool.stop(streamId);
    }

    public void toggleFX(boolean x)
    {
        fx = x;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("fx", fx);
        editor.apply();
    }

    public boolean getFX()
    {
        return fx;
    }

    public int getVST()
    {
        return vst;
    }

    public void setVST(int x)
    {
        vst = x;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("vst", x);
        editor.apply();
    }
}