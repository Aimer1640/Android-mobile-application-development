package com.example.navigationview.ui.thing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationview.R;
import com.example.navigationview.database.MyDatabase;
import com.example.navigationview.database.Thing;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ThingFragment extends Fragment {

    private String thingid;
    private Thing thing;
    String thingsname;
    String publishertime;
    byte[] b;
    CollapsingToolbarLayout collapsingToolbar;
    TextView thingname;
    TextView thingpublishertime;
    ImageView picture;



    public static ThingFragment newInstance() {
        return new ThingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_thing, container, false);
        //查询数据
        Bundle bundle=getArguments();
        thingid=bundle.getString("thingid");
        Toast.makeText(getContext(),""+thingid,Toast.LENGTH_SHORT).show();
        collapsingToolbar =view.findViewById(R.id.toolbar_layout);
        thingname=view.findViewById(R.id.thingname);
        thingpublishertime= view.findViewById(R.id.publishtime);
        picture=view.findViewById(R.id.picture);
        //异步任务完成数据库查询操作
        new SelectThingTask(thingid).execute();


        Button delthing= view.findViewById(R.id.delthing);
        delthing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //异步任务完成数据库删除操作
                new DeleteThingTask(thing).execute();

                Toast.makeText(getActivity(), "删除物品成功！", Toast.LENGTH_LONG).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_home);

            }
        });
        Button editthing= (Button) view.findViewById(R.id.editthing);
        editthing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                Bundle bundle=new Bundle();
                bundle.putString("thingid",thingid);
                navController.navigate(R.id.nav_editbook,bundle);
            }
        });
        return  view;
    }

    //查询数据的异步任务
    public class SelectThingTask extends AsyncTask<Void, Void, Void>
    {
        String thingid;
        public SelectThingTask(String thingid)
        {
            this.thingid=thingid ;
        }
        //不能在这里更新UI,否则有异常
        @Override
        protected Void doInBackground(Void... arg0)
        {
            thing= MyDatabase.getInstance(getContext()).thingsDao().getThingByThingId(thingid);
            return null;
        }
        //onPostExecute用于doInBackground执行完后，可以更新界面UI。
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            thingsname=thing.thingname;
            publishertime=thing.publishtime;
            b=thing.img;
            collapsingToolbar.setTitle(thingsname);
            thingname.setText(thingid);
            thingpublishertime.setText(publishertime);
            if (b != null && b.length > 0) {
                Bitmap image = BitmapFactory.decodeByteArray(b, 0, b.length);
                picture.setImageBitmap(image);
            } else {
                picture.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }


    //删除数据的异步任务
    private class DeleteThingTask extends AsyncTask<Void, Void, Void>{
        Thing thing;
        public DeleteThingTask(Thing thing)
        {
            this.thing =thing ;
        }
        @Override
        protected Void doInBackground(Void... arg0)    {        MyDatabase.getInstance(getContext()).thingsDao().deleteThing(thing);
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
        }
    }









}
