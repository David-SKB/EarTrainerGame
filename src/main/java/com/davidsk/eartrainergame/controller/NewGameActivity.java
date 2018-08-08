package com.davidsk.eartrainergame.controller;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.davidsk.eartrainergame.Helper;
import com.davidsk.eartrainergame.R;
import com.davidsk.eartrainergame.model.AppDatabase;
import com.davidsk.eartrainergame.model.Game;
import com.davidsk.eartrainergame.model.PianoSounds;
import com.davidsk.eartrainergame.model.Score;

import java.lang.ref.WeakReference;
import java.util.Random;
public class NewGameActivity extends AppCompatActivity implements View.OnTouchListener
{
    private Button BtnC5, BtnDb5, BtnD5, BtnEb5, BtnE5, BtnF5, BtnGb5, BtnG5, BtnAb5, BtnA5, BtnBb5, BtnB5, BtnC6;
    private TextView timerText, scoreText;
    private Button startButton;
    private ImageButton nextButton;
    boolean gameStarted = false;
    private int nextKey, vst;
    ProgressBar mProgressBar;
    boolean selection_made;
    boolean gameOver = false;
    private Game game = new Game();
    Helper helper = Helper.getInstance();
    protected AppDatabase db;
    private int playerNoteID, aiNoteID;
    private ImageView gameOverImage;
    ConstraintLayout currentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        currentLayout = findViewById(R.id.new_game_activity);

        //get buttons
        BtnC5 = findViewById(R.id.c_note);
        BtnDb5 = findViewById(R.id.d_b_note);
        BtnD5 = findViewById(R.id.d_note);
        BtnEb5 = findViewById(R.id.e_b_note);
        BtnE5 = findViewById(R.id.e_note);
        BtnF5 = findViewById(R.id.f_note);
        BtnGb5 = findViewById(R.id.g_b_note);
        BtnG5 = findViewById(R.id.g_note);
        BtnAb5 = findViewById(R.id.a_b_note);
        BtnA5 = findViewById(R.id.a_note);
        BtnBb5 = findViewById(R.id.b_b_note);
        BtnB5 = findViewById(R.id.b_note);
        BtnC6 = findViewById(R.id.c2_note);

        //get progress bar
        mProgressBar=findViewById(R.id.progressBar);
        mProgressBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

        //Set listeners
        BtnC5.setOnTouchListener(this);
        BtnDb5.setOnTouchListener(this);
        BtnD5.setOnTouchListener(this);
        BtnEb5.setOnTouchListener(this);
        BtnE5.setOnTouchListener(this);
        BtnF5.setOnTouchListener(this);
        BtnGb5.setOnTouchListener(this);
        BtnG5.setOnTouchListener(this);
        BtnAb5.setOnTouchListener(this);
        BtnA5.setOnTouchListener(this);
        BtnBb5.setOnTouchListener(this);
        BtnB5.setOnTouchListener(this);
        BtnC6.setOnTouchListener(this);

        //Buttons
        timerText = findViewById(R.id.timer_text);
        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                cdTimer();
                startButton.setVisibility(View.INVISIBLE);
            }
        });
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                PianoSounds.getInstance().stopSound(playerNoteID);
                startGame();
            }
        });

        scoreText = findViewById(R.id.scoreView);

        startButton.setSoundEffectsEnabled(false);
        nextButton.setSoundEffectsEnabled(false);
        gameOverImage = findViewById(R.id.game_over_image);
        gameOverImage.setVisibility(View.INVISIBLE);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "ear-trainer-db").build();

        vst = PianoSounds.getInstance().getVST();
        playerNoteID = 0;
    }

    private void saveData()
    {
        new dbConnection(this).execute();
    }

    private int getRand()
    {
        Random r = new Random();
        return r.nextInt(14 - 1) + 1;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void startGame()
    {
        selection_made = false;
        if (game.getRound() == 10)
        {
            gameOver = true;
        }
        nextButton.setVisibility(View.INVISIBLE);
        gameStarted = true;
        toggleKeys(true);
        round();
    }

    public void round()
    {
        nextKey = getRand();
        if (vst == 1)
        {
            aiNoteID = helper.playSoundID(nextKey);
        }
        else if (vst == 2)
        {
            aiNoteID = helper.playSoundID(nextKey+13);
        }

        //String note = helper.getNoteString(nextKey);
        //helper.output(note);
        qTimer();

        final Handler handler = new Handler();
        handler.postDelayed(roundRun(), 5000);

        if (gameOver)
        {
            handler.postDelayed(endRun(), 5000);
        }
    }

    public Runnable roundRun()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    toggleKeys(false);
                    if (!gameOver)
                    {
                        nextButton.setVisibility(View.VISIBLE);
                    }
                    if (!selection_made)
                    {
                        boolean debugval = game.addRound(helper.getNoteString(nextKey), "NA");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        return run;
    }

    public Runnable endRun()
    {
        final Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //Game Over
                    gameOverImage.setVisibility(View.VISIBLE);
                    //SAVE GAME OBJECT HERE
                    Score debugval = game.save();
                    saveData();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        return run;
    }

    private static class dbConnection extends AsyncTask<Void, Void, String>
    {
        private WeakReference<NewGameActivity> activityReference;
        dbConnection(NewGameActivity context)
        {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... params)
        {
            activityReference.get().db.scoreDao().insertAll(activityReference.get().game.save());
            return "finished";
        }

        @Override
        protected void onPostExecute(String result)
        {

        }

    }

    public void compareNoteInput(int noteID)
    {
        int currentNote = noteID;
        int randomNote = nextKey;
        Button btn = getButtonObj(noteID);
        Button btn2 = getButtonObj(randomNote);
        selection_made = true;

        if (currentNote == randomNote)
        {
            btn.setTag(0);
            TimedKeyGreen(btn);
            boolean debugval = game.addRound(helper.getNoteString(randomNote), helper.getNoteString(currentNote));
            game.addPoint();
            String output = "Score: " + game.getScore();
            scoreText.setText(output);
        }
        else
        {
            btn.setTag(0);
            TimedKeyRed(btn);
            TimedKeyGreen(btn2);
            boolean debugval = game.addRound(helper.getNoteString(randomNote), helper.getNoteString(currentNote));
        }
    }

    public void qTimer()
    {
        mProgressBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        Thread th = new Thread() {

            @Override
            public void run() {
                try {
                    for (int i = 1; i < 100; i++)
                    {
                        runOnUiThread(pTimer(i));
                        Thread.sleep(5000/100);
                        if (i == 50)
                        {
                            setYellow();
                        }
                        else if (i == 75)
                        {
                            setRed();
                        }
                    }
                    mProgressBar.setProgress(100);
                }
                catch (InterruptedException e) {}
            }
        };
        th.start();
    }

    public void setYellow()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.getProgressDrawable().setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
    }

    public void setRed()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
    }

    public Runnable pTimer(int i)
    {
        final int count = i;
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    mProgressBar.setProgress(count);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        return run;
    }

    public Button getButtonObj(int i)
    {
        Button value = null;
        switch (i)
        {
            case 1:  value = BtnC5;
                break;
            case 2:  value = BtnDb5;
                break;
            case 3:  value = BtnD5;
                break;
            case 4:  value = BtnEb5;
                break;
            case 5:  value = BtnE5;
                break;
            case 6:  value = BtnF5;
                break;
            case 7:  value = BtnGb5;
                break;
            case 8:  value = BtnG5;
                break;
            case 9:  value = BtnAb5;
                break;
            case 10: value = BtnA5;
                break;
            case 11: value = BtnBb5;
                break;
            case 12: value = BtnB5;
                break;
            case 13: value = BtnC6;
                break;
        }
        return value;
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent)
    {
        // default method for onTouch Events
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !gameStarted)
        {
            if (vst == 1)
            {
                switch (v.getId())
                {
                    case R.id.c_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_1);
                        BtnC5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.d_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_2);
                        BtnDb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.d_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_3);
                        BtnD5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.e_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_4);
                        BtnEb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.e_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_5);
                        BtnE5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.f_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_6);
                        BtnF5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.g_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_7);
                        BtnGb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.g_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_8);
                        BtnG5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.a_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_9);
                        BtnAb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.a_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_10);
                        BtnA5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.b_b_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_11);
                        BtnBb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.b_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_12);
                        BtnB5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.c2_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_13);
                        BtnC6.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                }
            }
            else if (vst == 2)
            {
                switch (v.getId())
                {
                    case R.id.c_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_14);
                        BtnC5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.d_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_15);
                        BtnDb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.d_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_16);
                        BtnD5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.e_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_17);
                        BtnEb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.e_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_18);
                        BtnE5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.f_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_19);
                        BtnF5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.g_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_20);
                        BtnGb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.g_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_21);
                        BtnG5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.a_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_22);
                        BtnAb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.a_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_23);
                        BtnA5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.b_b_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_24);
                        BtnBb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.b_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_25);
                        BtnB5.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                    case R.id.c2_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_26);
                        BtnC6.setBackgroundDrawable(getResources().getDrawable(R.drawable.greykey));
                        break;
                }
            }
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && gameStarted)
        {
            if (vst == 1)
            {
                switch (v.getId())
                {
                    case R.id.c_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_1);
                        break;
                    case R.id.d_b_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_2);
                        break;
                    case R.id.d_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_3);
                        break;
                    case R.id.e_b_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_4);
                        break;
                    case R.id.e_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_5);
                        break;
                    case R.id.f_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_6);
                        break;
                    case R.id.g_b_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_7);
                        break;
                    case R.id.g_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_8);
                        break;
                    case R.id.a_b_note:  playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_9);
                        break;
                    case R.id.a_note: playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_10);
                        break;
                    case R.id.b_b_note: playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_11);
                        break;
                    case R.id.b_note: playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_12);
                        break;
                    case R.id.c2_note: playerNoteID = PianoSounds.getInstance().playSound(PianoSounds.SOUND_13);
                        break;
                }
            }
            else if (vst == 2)
            {
                switch (v.getId())
                {
                    case R.id.c_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_14);
                        break;
                    case R.id.d_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_15);
                        break;
                    case R.id.d_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_16);
                        break;
                    case R.id.e_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_17);
                        break;
                    case R.id.e_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_18);
                        break;
                    case R.id.f_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_19);
                        break;
                    case R.id.g_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_20);
                        break;
                    case R.id.g_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_21);
                        break;
                    case R.id.a_b_note:  PianoSounds.getInstance().playSound(PianoSounds.SOUND_22);
                        break;
                    case R.id.a_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_23);
                        break;
                    case R.id.b_b_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_24);
                        break;
                    case R.id.b_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_25);
                        break;
                    case R.id.c2_note: PianoSounds.getInstance().playSound(PianoSounds.SOUND_26);
                        break;
                }
            }
        }

        else if (motionEvent.getAction() == MotionEvent.ACTION_UP && !gameStarted)
        {
            switch (v.getId())
            {
                case R.id.c_note: BtnC5.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
                case R.id.d_b_note: BtnDb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.blackkey));
                    break;
                case R.id.d_note: BtnD5.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
                case R.id.e_b_note: BtnEb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.blackkey));
                    break;
                case R.id.e_note: BtnE5.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
                case R.id.f_note: BtnF5.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
                case R.id.g_b_note: BtnGb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.blackkey));
                    break;
                case R.id.g_note: BtnG5.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
                case R.id.a_b_note: BtnAb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.blackkey));
                    break;
                case R.id.a_note: BtnA5.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
                case R.id.b_b_note: BtnBb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.blackkey));
                    break;
                case R.id.b_note: BtnB5.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
                case R.id.c2_note: BtnC6.setBackgroundDrawable(getResources().getDrawable(R.drawable.whitekey));
                    break;
            }
        }
        if (gameStarted && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch (v.getId())
            {
                case R.id.c_note: compareNoteInput(1);
                    break;
                case R.id.d_b_note: compareNoteInput(2);
                    break;
                case R.id.d_note: compareNoteInput(3);
                    break;
                case R.id.e_b_note: compareNoteInput(4);
                    break;
                case R.id.e_note: compareNoteInput(5);
                    break;
                case R.id.f_note: compareNoteInput(6);
                    break;
                case R.id.g_b_note: compareNoteInput(7);
                    break;
                case R.id.g_note: compareNoteInput(8);
                    break;
                case R.id.a_b_note: compareNoteInput(9);
                    break;
                case R.id.a_note: compareNoteInput(10);
                    break;
                case R.id.b_b_note: compareNoteInput(11);
                    break;
                case R.id.b_note: compareNoteInput(12);
                    break;
                case R.id.c2_note: compareNoteInput(13);
                    break;
            }
        }

        if (gameStarted && motionEvent.getAction() == MotionEvent.ACTION_UP)
        {
            toggleKeys(false);
        }
        return true;
    }

    /** Disables & Enables all keys **/
    public void toggleKeys(boolean status)
    {
        BtnC5.setEnabled(status);
        BtnDb5.setEnabled(status);
        BtnD5.setEnabled(status);
        BtnEb5.setEnabled(status);
        BtnE5.setEnabled(status);
        BtnF5.setEnabled(status);
        BtnGb5.setEnabled(status);
        BtnG5.setEnabled(status);
        BtnAb5.setEnabled(status);
        BtnA5.setEnabled(status);
        BtnBb5.setEnabled(status);
        BtnB5.setEnabled(status);
        BtnC6.setEnabled(status);
    }

    public synchronized void TimedKeyRed(Button btn)
    {
        final Drawable original = btn.getBackground();

        //If Color is not active
        if (btn.getTag() == null || (int) btn.getTag() == 0)
        {
            //Set key to red
            btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.redkey));
            btn.setTag(1);

            //Reset key back to normal
            final Handler handler = new Handler();
            handler.postDelayed(colorRun(btn, original), 1000);
        }
        else
        {
            //Do nothing
        }
    }

    public synchronized void TimedKeyGreen(Button btn)
    {
        final Drawable original = btn.getBackground();

        //If Color is not active
        if (btn.getTag() == null || (int) btn.getTag() == 0)
        {
            //Set key to green
            btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.greenkey));
            btn.setTag(1);

            //Reset key back to normal
            final Handler handler = new Handler();
            handler.postDelayed(colorRun(btn, original), 1000);
        }
        else
        {
            //Do nothing
        }
    }

    public void cdTimer()
    {
        Thread th = new Thread() {

            @Override
            public void run() {
                try {
                    for (int i = 3; i >= -1; i--)
                    {
                        runOnUiThread(gameTimer(i));
                        Thread.sleep(1000);
                    }
                }
                catch (InterruptedException e) {}
            }
        };
        th.start();
        startGameDelay();
    }

    public void startGameDelay()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startGame();
            }
        }, 4000);
    }

    public Runnable gameTimer(int i)
    {
        final String countdown;
        if (i == 0)
        {
            countdown = "GO!";
            helper.playSoundID(PianoSounds.SOUND_START);
        }
        else if (i == -1)
        {
            countdown = "";
        }
        else
        {
            countdown = "" + i;
            helper.playSoundID(PianoSounds.SOUND_CLICK);
        }

        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    timerText.setText(countdown);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        return run;
    }

    public Runnable

    colorRun(Button button, Drawable bg)
    {
        final Drawable original = bg;
        final Button btn = button;
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    btn.setBackgroundDrawable(original);
                    btn.setTag(0);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        return run;
    }

    @Override
    protected void onResume()
    {
        game = new Game();
        if (Helper.getInstance().getBackground() == 1)
        {
            currentLayout.setBackgroundResource(R.drawable.background);
            timerText.setTextColor(Color.rgb(124, 68, 23));
        }
        else
        {
            currentLayout.setBackgroundResource(R.drawable.darkbackground);
            timerText.setTextColor(Color.rgb(255, 218, 191));
        }
        super.onResume();
    }
}
