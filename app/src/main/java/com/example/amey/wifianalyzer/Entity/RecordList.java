package com.example.amey.wifianalyzer.Entity;

public class RecordList
{
    String name;
    Boolean status;

    public RecordList(String name, Boolean status) {
        this.name = name;
        this.status = status;
    }

    public RecordList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RecordList{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
