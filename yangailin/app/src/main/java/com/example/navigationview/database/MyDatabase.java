package com.example.navigationview.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={Thing.class},version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="MyThings";
    private static MyDatabase databaseInstance;
    public static synchronized MyDatabase getInstance(Context context){
        if (databaseInstance==null){
            databaseInstance=Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,DATABASE_NAME).build();
        }
        return databaseInstance;
    }
    public abstract ThingsDao thingsDao();
}
