package com.davidsk.eartrainergame.controller;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.davidsk.eartrainergame.Helper;
import com.davidsk.eartrainergame.R;
import com.davidsk.eartrainergame.model.AppDatabase;
import com.davidsk.eartrainergame.model.Score;

import java.lang.ref.WeakReference;
import java.util.List;




public class MyScoreActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    List<Score> scoreList;
    protected AppDatabase db;
    ConstraintLayout currentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);
        currentLayout = findViewById(R.id.my_score_activity);

        //Use once for migrating database to a new version after schema changes
        //db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "ear-trainer-db").fallbackToDestructiveMigration().build();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "ear-trainer-db").build();

        new dbConnection(this).execute();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(new com.davidsk.eartrainergame.model.Adapter(this, scoreList));
    }


    private static class dbConnection extends AsyncTask<Void, Void, String>
    {
        private WeakReference<MyScoreActivity> activityReference;

        // only retain a weak reference to the activity
        dbConnection(MyScoreActivity context)
        {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Void... params)
        {
            String empty;
            activityReference.get().scoreList = activityReference.get().db.scoreDao().getAll();
            int length = activityReference.get().scoreList.size();
            if (length == 0)
            {
                empty = "true";
            }

            else
            {
                empty = "false";
            }
            return empty;
        }

        @Override
        protected void onPostExecute(String result)
        {
            MyScoreActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            Score[] scoreList = new Score[activityReference.get().scoreList.size()];
            for(int i = activityReference.get().scoreList.size() -1; i >=0; i--)
            {
                scoreList[i] = activityReference.get().scoreList.get(i);
            }
            Score[] scoreList2 = new Score[scoreList.length];
            int count = 0;
            for (int i = scoreList.length - 1; i >= 0; i--)
            {
                scoreList2[count] = scoreList[i];
                count++;
            }

            if (scoreList.length > 10)
            {
                Score[] scoreList3 = new Score[10];
                for (int i=0; i<11; i++)
                {
                    scoreList3[i] = scoreList2[i];
                }
                if (result.equals("false"))
                {
                    activityReference.get().recyclerView.setAdapter(new com.davidsk.eartrainergame.model.Adapter(activityReference.get().getApplicationContext(), scoreList3, Helper.getInstance().getBackground()));
                }
            }
            if (result.equals("false"))
            {
                activityReference.get().recyclerView.setAdapter(new com.davidsk.eartrainergame.model.Adapter(activityReference.get().getApplicationContext(), scoreList2, Helper.getInstance().getBackground()));
            }

        }
    }

    @Override
    protected void onResume()
    {
        if (Helper.getInstance().getBackground() == 1)
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
