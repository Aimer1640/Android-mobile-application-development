package com.example.navigationview.ui.thing;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.navigationview.database.MyDatabase;
import com.example.navigationview.database.Thing;

import java.util.List;

public class ThingViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MyDatabase myDatabase;
    private LiveData<List<Thing>> liveDataBook;


    public ThingViewModel(@NonNull Application application){
        super();
        myDatabase=MyDatabase.getInstance(application);
        liveDataBook=myDatabase.thingsDao().getThingList();
    }

    public LiveData<List<Thing>> getLiveDataBook(){
        return liveDataBook;
    }

}