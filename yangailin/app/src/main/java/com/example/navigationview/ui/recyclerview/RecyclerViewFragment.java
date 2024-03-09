package com.example.navigationview.ui.recyclerview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationview.LoginActivity;
import com.example.navigationview.ManageActivity;
import com.example.navigationview.R;
import com.example.navigationview.SearchRecordActivity;
import com.example.navigationview.database.Thing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RecyclerViewFragment extends Fragment {

    private RecyclerViewViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<Thing> thingList;
    List<Map<String,Object>> mydata;
    MyRecycleViewAdapter myRecycleViewAdapter;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直线性布局
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));//线性宫格显示，类似gridview
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));//线性宫格显示类似瀑布流
        thingList = new ArrayList<>();
        myRecycleViewAdapter = new MyRecycleViewAdapter(getContext(),thingList);
        recyclerView.setAdapter(myRecycleViewAdapter);

        //从RecyclerViewViewModel获取数据，观察livedata
        RecyclerViewViewModel recyclerViewViewModel = new ViewModelProvider(this).get(RecyclerViewViewModel.class);
        recyclerViewViewModel.getLiveDataThing().observe(getViewLifecycleOwner(), new Observer<List<Thing>>() {
            @Override
            public void onChanged(List<Thing> things) {
                thingList.clear();
                thingList.addAll(things);
                myRecycleViewAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }


    // 定义一个RecyclerView专属的数据适配器
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);

            Button addButton = view.findViewById(R.id.addbutton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent1=new Intent(getActivity(), ManageActivity.class);
                    startActivity(intent1);

                }
            });

            Button searchButton = view.findViewById(R.id.searchbutton);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent1=new Intent(getActivity(), SearchRecordActivity.class);
                    startActivity(intent1);
                }
            });

            Button addthingButton = view.findViewById(R.id.addthingbutton);
            addthingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent1=new Intent(getActivity(), SearchRecordActivity.class);
                    startActivity(intent1);
                }
            });

            //退出按键
            Button btn_exit=view.findViewById(R.id.btn_exit);
            btn_exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("退出操作")
                            .setMessage("确定退出，不继续玩玩？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("继续留下！", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    dialog.show();

                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            final String title=(String)mydata.get(position).get("title");
            final String info= (String) mydata.get(position).get("info");
            final int img= (Integer) mydata.get(position).get("img");
//            holder.title.setText(title);
//            holder.info.setText(info);
//            holder.img.setBackgroundResource(img);
            //新增代码
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController= Navigation.findNavController(getActivity(),R.id.thingFragment);
                    Bundle bundle=new Bundle();
                    bundle.putString("title",title);
                    bundle.putString("info",info);
                    bundle.putInt("img",img);
                    navController.navigate(R.id.action_detailFragment_to_showbookthingFragment,bundle);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mydata.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title,info;
            ImageView img;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                info=itemView.findViewById(R.id.info);
                img=itemView.findViewById(R.id.img);

            }
        }
    }

}