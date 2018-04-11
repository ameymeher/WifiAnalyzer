package com.example.amey.wifianalyzer.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.amey.wifianalyzer.Adapter.PagerAdapter;
import com.example.amey.wifianalyzer.Fragments.RecordWifiListFragment;
import com.example.amey.wifianalyzer.Fragments.WifiListFragment;
import com.example.amey.wifianalyzer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WifiListFragment.OnFragmentInteractionListener,RecordWifiListFragment.OnFragmentInteractionListener{

    public static WifiManager wifiManager;
    public static ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        initialize();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Nearby Wifi"));
        tabLayout.addTab(tabLayout.newTab().setText("Record Activity"));

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initialize(){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public boolean checkPermission() {
        System.out.println("Build Version "  + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }

        List<String> permissionList = new ArrayList<String>(Arrays.asList(
                Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_WIFI_STATE)){
            permissionList.remove(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,Manifest.permission.CHANGE_WIFI_STATE)){
            permissionList.remove(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionList.remove(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)){
            permissionList.remove(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            permissionList.remove(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            permissionList.remove(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        System.out.println("Number of wifi states:" +  permissionList.size());

        if(permissionList.size() > 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_WIFI_STATE)) {
                showExplanation("Permission Needed", "Rationale", permissionList);
            }else {
                requestPermissions(permissionList.toArray(new String[permissionList.size()]), 100);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults != null ) {
                for(int i= 0; i< grantResults.length;i++){
                    if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getApplicationContext(),"permission: " + permissions[i] + " is denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final List<String> permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(List<String> permissionName) {
        ActivityCompat.requestPermissions(this,
                permissionName.toArray(new String[permissionName.size()]), 100);
    }
}
