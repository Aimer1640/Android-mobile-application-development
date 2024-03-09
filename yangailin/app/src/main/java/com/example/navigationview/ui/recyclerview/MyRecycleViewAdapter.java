package com.example.navigationview.ui.recyclerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationview.R;
import com.example.navigationview.database.Thing;

import java.util.List;
public class  MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder>
    {   private List<Thing> mData;
        private LayoutInflater layoutInflater;
        Context context;

        public MyRecycleViewAdapter(Context context,List<Thing> mData) {
            this.context=context;
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mData = mData;
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView img;
            public TextView thingid;
            public TextView thingname;
            public TextView publishtime;

            public ViewHolder(View convertView) {
                super(convertView);
                img =convertView.findViewById(R.id.picture);
                thingid = convertView.findViewById(R.id.thingid);
                thingname = convertView.findViewById(R.id.thingname);
                publishtime =convertView.findViewById(R.id.publishtime);
            }
        }
        @NonNull
        @Override
        public MyRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= layoutInflater.inflate(R.layout.item,parent, false);
            return new ViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull MyRecycleViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            //使用Room，有变化
            byte[] img=mData.get(position).img;
            Bitmap bitmap;
            if (img != null && img.length > 0) {
                bitmap= BitmapFactory.decodeByteArray(img, 0, img.length);
                holder.img.setImageBitmap(bitmap);
            } else {
                holder.img.setBackgroundResource(R.mipmap.ic_launcher);
            }
            holder.thingid.setText(mData.get(position).thingid);
            holder.thingname.setText(mData.get(position).thingname);
            holder.publishtime.setText(mData.get(position).publishtime);

            //导航到detailfragment
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController= Navigation.findNavController((Activity) context,R.id.nav_host_fragment);
                    Bundle bundle=new Bundle();
                    bundle.putString("thingid",mData.get(position).thingid);
                    navController.navigate(R.id.thingFragment2,bundle);
                }
            });
        }
        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

