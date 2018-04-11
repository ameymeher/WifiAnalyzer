package com.example.amey.wifianalyzer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.amey.wifianalyzer.Entity.WifiList;
import com.example.amey.wifianalyzer.R;

import java.util.ArrayList;

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {

    private ArrayList<WifiList> wifiList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView SSID,BSSID,percentage;
        public ProgressBar wifiPower;

        public ViewHolder(View view) {
            super(view);
            SSID = (TextView) view.findViewById(R.id.SSID);
            BSSID = (TextView) view.findViewById(R.id.BSSID);
            percentage = (TextView) view.findViewById(R.id.percentage);
            wifiPower = (ProgressBar) view.findViewById(R.id.wifiPower);
        }
    }

    public WifiListAdapter(ArrayList<WifiList> wifiList){
        this.wifiList = wifiList;
    }

    //create new views
    @Override
    public WifiListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wifilist_item,parent,false);
        return new ViewHolder(itemView);
    }

    //replace the contents of the view
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        WifiList entry = wifiList.get(position);
        holder.SSID.setText(entry.getSSID());
        holder.BSSID.setText(entry.getBSSID());
        holder.percentage.setText(Integer.toString(entry.getPowerLevel()));
        holder.wifiPower.setProgress(100 + entry.getPowerLevel());
    }

    @Override
    public int getItemCount(){
        return wifiList.size();
    }
}
