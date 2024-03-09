package com.example.navigationview.ui;

import com.example.navigationview.R;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerModel {
    public List<Integer> getData() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(R.drawable.welcome);
        list.add(R.drawable.welcome2);
        list.add(R.drawable.welcome3);

        return list;
    }
}