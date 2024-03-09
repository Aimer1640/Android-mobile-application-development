package com.example.navigationview.ui.viewpages2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;


import com.example.navigationview.R;
import com.example.navigationview.ui.BannerAdapter;
import com.example.navigationview.ui.ViewpagerModel;

import java.util.ArrayList;
import java.util.List;

public class viewpages2Fragment extends Fragment {
    private ViewPager2 view_banner;
    private LinearLayout layout_dot;

    private List<Integer> imgUrl;
    private List<ImageView> dotList;
    private BannerAdapter bannerAdapter;
    private Viewpages2ViewModel mViewModel;

    public static viewpages2Fragment newInstance() {
        return new viewpages2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewpages2, container, false);
        view_banner=view.findViewById(R.id.bannerVp);
        layout_dot=view.findViewById(R.id.ViewpagerFragment_view_dot);
        imgUrl=new ViewpagerModel().getData();
        bannerAdapter=new BannerAdapter(getContext(),imgUrl);
        view_banner.setAdapter(bannerAdapter);
        dotList=new ArrayList<>();
        initIndicatorDots();
        view_banner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for(int i = 0; i <imgUrl.size(); i++){
                    if(i==position%imgUrl.size()){
                        dotList.get(i).setBackgroundResource(R.drawable.shape_dot_red);
                    }else{
                        dotList.get(i).setBackgroundResource(R.drawable.shape_dot_white);
                    }
                }
            }
        });
        return view;
    }

    //轮播图效果的实现
    private void initViews(View view) {
        imgUrl=new ViewpagerModel().getData();//获取轮播图的图片数据
        view_banner = view.findViewById(R.id.bannerVp);
        layout_dot= view.findViewById(R.id.ViewpagerFragment_view_dot);
        bannerAdapter = new BannerAdapter(getContext(),imgUrl);
        view_banner.setAdapter(bannerAdapter);
        dotList=new ArrayList<>();
        initIndicatorDots();
        //注册轮播图的滚动事件监听器
        }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Viewpages2ViewModel.class);
        // TODO: Use the ViewModel
    }




    //初始化指示点
    private void initIndicatorDots(){
        for(int i = 0; (i)< imgUrl.size(); i++){
            ImageView imageView = new ImageView(getContext());
            if (i == 0) imageView.setBackgroundResource(R.drawable.shape_dot_red);
            else imageView.setBackgroundResource(R.drawable.shape_dot_white);
            //为指示点添加间距
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,10,0);
            imageView.setLayoutParams(layoutParams);
            //单击dot切换轮播图图片
//            final int index = i%(imgUrl.size());
//            imageView.setOnClickListener(new View.OnClickListener() {//点击知识点实现轮播图的图片切换功能
//                @Override
//                public void onClick(View v) {
//                    view_banner.setCurrentItem(index);
//                    for(ImageView iv :dotList){
//                        iv.setBackgroundResource(R.drawable.shape_dot_white);
//                    }
//                    v.setBackgroundResource(R.drawable.shape_dot_red);
//
//                }
//            });
            dotList.add(imageView);
            layout_dot.addView(imageView);  //将指示点添加进容器
        }
    }


}