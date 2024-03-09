package com.example.navigationview.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ThingsDao {
    @Insert
    void insertThing(Thing thing);

    @Delete
    void deleteThing(Thing thing);

    @Update
    void updateThing(Thing thing);

    @Query("SELECT * FROM thing")
    LiveData<List<Thing>> getThingList();
    @Query("SELECT * FROM thing WHERE thingid=:thingid")
    Thing getThingByThingId(String thingid);
}
