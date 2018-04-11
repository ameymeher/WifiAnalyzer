package com.example.amey.wifianalyzer.Activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.amey.wifianalyzer.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String fileName =(String) getIntent().getExtras().get("file");
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File(sdcard + "/WifiAnalyzer/Logs",fileName);

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        TextView tv = (TextView)findViewById(R.id.result);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(text.toString());
    }


}
