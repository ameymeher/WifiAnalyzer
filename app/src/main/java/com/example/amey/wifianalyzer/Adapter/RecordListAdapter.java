package com.example.amey.wifianalyzer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.amey.wifianalyzer.Entity.RecordList;
import com.example.amey.wifianalyzer.Entity.WifiList;
import com.example.amey.wifianalyzer.R;

import java.util.ArrayList;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.ViewHolder>
{
    private ArrayList<RecordList> records;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView action;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            action = (ImageView) view.findViewById(R.id.action);
        }
    }

    public RecordListAdapter(ArrayList<RecordList> records){
        this.records = records;
    }

    //create new views
    @Override
    public RecordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recordlist_item,parent,false);
        return new RecordListAdapter.ViewHolder(itemView);
    }

    //replace the contents of the view
    @Override
    public void onBindViewHolder(RecordListAdapter.ViewHolder holder, int position){
        RecordList entry = records.get(position);
        holder.name.setText(entry.getName());
        if(entry.getStatus()){
            holder.action.setImageResource(R.drawable.stop);
        }
        else{
            holder.action.setImageResource(R.drawable.play);
        }
    }

    @Override
    public int getItemCount(){
        return records.size();
    }
}
