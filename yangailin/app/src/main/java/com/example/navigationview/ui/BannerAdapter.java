package com.example.navigationview.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.navigationview.R;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context context;
    private List<Integer> imgUrl;
    public  BannerAdapter(Context context, List<Integer> imgUrl){
        this.context = context;
        this.imgUrl = imgUrl;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item,parent,false));  }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.iv_img.setBackgroundResource(imgUrl.get(position));
        Glide.with(context).load(imgUrl.get(position%3)).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.iv_img);//图片加载的第三方库,设置不带缓存diskCacheStrategy(DiskCacheStrategy.NONE)
    }
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;//无线循环
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.itemBanner_img);
        }
    }
}