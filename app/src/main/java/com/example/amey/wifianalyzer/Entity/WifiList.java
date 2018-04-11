package com.example.amey.wifianalyzer.Entity;

public class WifiList
{
    String SSID;
    int powerLevel;
    String BSSID;

    public WifiList(String SSID, int powerLevel, String BSSID) {
        this.SSID = SSID;
        this.powerLevel = powerLevel;
        this.BSSID = BSSID;
    }

    public WifiList() {
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    @Override
    public String toString() {
        return "WifiList{" +
                "SSID='" + SSID + '\'' +
                ", powerLevel=" + powerLevel +
                ", BSSID='" + BSSID + '\'' +
                '}';
    }
}
