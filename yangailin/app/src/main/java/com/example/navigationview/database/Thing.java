package com.example.navigationview.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "thing")
public class Thing {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "thingid",typeAffinity = ColumnInfo.TEXT)
    public String thingid;
    @ColumnInfo(name = "thingname",typeAffinity = ColumnInfo.TEXT)
    public String thingname;
    @ColumnInfo(name = "publishtime",typeAffinity = ColumnInfo.TEXT)
    public String publishtime;
    @ColumnInfo(name = "img",typeAffinity = ColumnInfo.BLOB)
    public byte[] img;

    public Thing(String thingid, String thingname, String publishtime, byte[] img){
        this.thingid=thingid;
        this.thingname=thingname;
        this.publishtime=publishtime;
        this.img=img;
    }
    @Ignore
    public Thing(String thingname, String publishtime, byte[] img){
        this.thingname=thingname;
    }

}
