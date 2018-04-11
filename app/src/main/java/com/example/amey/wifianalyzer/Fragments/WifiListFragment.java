package com.example.amey.wifianalyzer.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amey.wifianalyzer.Adapter.WifiListAdapter;
import com.example.amey.wifianalyzer.Entity.RecordList;
import com.example.amey.wifianalyzer.Entity.WifiList;
import com.example.amey.wifianalyzer.Activity.MainActivity;
import com.example.amey.wifianalyzer.R;
import com.example.amey.wifianalyzer.Utilities.RecyclerItemTouchListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WifiListFragment extends Fragment {
    private RecyclerView recyclerView;
    private WifiListAdapter wifiListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private OnFragmentInteractionListener mListener;
    private List<ScanResult> wifiListScanResult;
    private ArrayList<WifiList> wifiList;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiScanReceiver;
    FileOutputStream f;
    public static boolean flag;
    PrintWriter pw;

    public WifiListFragment() {
    }

    public static WifiListFragment newInstance(RecyclerView param1) {
        WifiListFragment fragment = new WifiListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    wifiManager.startScan();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally{
                    handler.postDelayed(this, 1000);
                }
            }
        };

        handler.post(runnable);
    }

    public void initialize(){

        wifiList = new ArrayList<WifiList>();
        wifiManager = MainActivity.wifiManager;
        wifiListAdapter = new WifiListAdapter(wifiList);

        wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                    wifiList.clear();

                    wifiListScanResult = wifiManager.getScanResults();
                    for(int i=0;i<wifiListScanResult.size();i++){
                        //if(!wifiNameList.contains(wifiList.get(i).SSID) && wifiList.get(i).level > -100) {
                            wifiList.add(new WifiList(wifiListScanResult.get(i).SSID,wifiListScanResult.get(i).level,wifiListScanResult.get(i).BSSID));
                            //System.out.println(wifiList.get(i));
                       // }
                    }
                    wifiListAdapter.notifyDataSetChanged();
                }
            }
        };
        if(!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);

        getActivity().registerReceiver(wifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wifi_list, container, false);
        recyclerView = view.findViewById(R.id.wifiListRecyclerView);
        recyclerView.setAdapter(wifiListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(getContext(),recyclerView,new RecyclerItemTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position) {
                RecordWifiListFragment.records.add(new RecordList(wifiList.get(position).getSSID(),true));
                RecordWifiListFragment.recordListAdapter.notifyDataSetChanged();

                File root = android.os.Environment.getExternalStorageDirectory();
                File dir = new File (root.getAbsolutePath() + "/WifiAnalyzer/Logs");
                dir.mkdirs();
                final File newFile = new File(dir,wifiList.get(position).getSSID() + new Date().getTime()+".txt");

                try {
                    newFile.createNewFile();
                    f = new FileOutputStream(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainActivity.viewPager.setCurrentItem(1);

                flag = true;
                final Handler handler = new Handler();
                pw = new PrintWriter(f);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if(RecordWifiListFragment.records.get(RecordWifiListFragment.records.size()-1).getStatus()){
                                pw.println(wifiList.get(position).getSSID() + "\t" + wifiList.get(position).getPowerLevel());
                                handler.postDelayed(this,1000);
                            }
                            else {
                                pw.flush();
                                pw.close();
                                f.close();
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.post(r);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getContext(),Integer.toString(position),Toast.LENGTH_LONG).show();
            }
        }));
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
