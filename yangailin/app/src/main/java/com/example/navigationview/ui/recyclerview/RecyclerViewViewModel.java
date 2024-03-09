package com.example.navigationview.ui.recyclerview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.navigationview.database.MyDatabase;
import com.example.navigationview.database.Thing;

import java.util.List;


public class RecyclerViewViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private MyDatabase myDatabase;
    private LiveData<List<Thing>> liveDataThing;


    public RecyclerViewViewModel(@NonNull Application application) {
        super(application);
        myDatabase=MyDatabase.getInstance(application);
        liveDataThing=myDatabase.thingsDao().getThingList();
    }

    public LiveData<List<Thing>> getLiveDataThing() {
        return liveDataThing;
    }
}
