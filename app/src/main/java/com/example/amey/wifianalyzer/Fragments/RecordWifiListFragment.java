package com.example.amey.wifianalyzer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amey.wifianalyzer.Activity.ResultActivity;
import com.example.amey.wifianalyzer.Adapter.RecordListAdapter;
import com.example.amey.wifianalyzer.Entity.RecordList;
import com.example.amey.wifianalyzer.R;
import com.example.amey.wifianalyzer.Utilities.RecyclerItemTouchListener;

import java.io.File;
import java.util.ArrayList;

public class RecordWifiListFragment extends Fragment {

    public static ArrayList<RecordList> records = new ArrayList<RecordList>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OnFragmentInteractionListener mListener;
    public static RecordListAdapter recordListAdapter = new RecordListAdapter(records);

    public RecordWifiListFragment() {
    }

    public static RecordWifiListFragment newInstance(String param1, String param2) {
        RecordWifiListFragment fragment = new RecordWifiListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();


    }

    public void initialize(){
        records.clear();
        File file = new File(Environment.getExternalStorageDirectory(),"/WifiAnalyzer/Logs");
        file.mkdirs();

        File files[] = file.listFiles();
        if(files != null) {
            for (int i = 0; i < files.length; i++) {
                records.add(new RecordList(files[i].getName(), false));
            }
            recordListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_wifi_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recordListRecyclerWifi);
        recyclerView.setAdapter(recordListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(getContext(), recyclerView, new RecyclerItemTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ImageView imageView = (ImageView) view.findViewById(R.id.action);
                imageView.setImageResource(R.drawable.play);
                records.get(position).setStatus(false);
                initialize();
                Toast.makeText(getContext(),"File saved",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Intent intent = new Intent(getContext(), ResultActivity.class);
                TextView textView = (TextView) view.findViewById(R.id.name);
                intent.putExtra("file",textView.getText().toString());
                startActivity(intent);
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
