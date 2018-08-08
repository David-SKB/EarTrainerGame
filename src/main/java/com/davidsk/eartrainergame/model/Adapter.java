package com.davidsk.eartrainergame.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidsk.eartrainergame.R;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;
    Score[] items;
    int bg;

    public Adapter(Context context, Score[] items, int bg)
    {
        this.context = context;
        this.items = items;
        this.bg = bg;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.score_row,parent,false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        String output = "Score: " + items[position].getScore() + " / " + items[position].getRounds();
        if (bg == 1)
        {
            ((Item)holder).textView.setTextColor(Color.rgb(45, 22, 3));
        }
        else
        {
            ((Item)holder).textView.setTextColor(Color.rgb(255, 218, 191));
        }
        ((Item)holder).textView.setTypeface(null, Typeface.BOLD);
        ((Item)holder).textView.setText(output);
    }

    @Override
    public int getItemCount()
    {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder
    {
        TextView textView;
        public Item(View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.item);
        }

    }
}
