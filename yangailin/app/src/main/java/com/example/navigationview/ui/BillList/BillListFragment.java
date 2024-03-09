package com.example.navigationview.ui.BillList;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.navigationview.ManageActivity;
import com.example.navigationview.R;
import com.example.navigationview.database.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillListFragment extends Fragment {


    private BillListViewModel mViewModel;

    RecyclerView recyclerView;
    List<Map<String, Object>> mData;

    private static final String DATABASE_NAME = "MyThings.db";
    private static final String TABLE_NAME = "record";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_STATE = "state";


    public static BillListFragment newInstance() {
        return new BillListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bill_list, container, false);

        // 添加按钮
        Button addButton = view.findViewById(R.id.addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent1=new Intent(getActivity(), ManageActivity.class);
                startActivity(intent1);

            }
        });

        recyclerView=view.findViewById(R.id.recycler_view);
        mData=getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直线性布局
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));//线性宫格显示，类似gridview
        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));//线性宫格显示类似瀑布流
        recyclerView.setAdapter(new BillListFragment.MyRecycleViewAdapter());

        return view;

    }

    @SuppressLint("Range")
    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        byte[] b = null;
        Bitmap image = null;
        //查询数据库获得数据
        DBHelper dbOpenHelper =new DBHelper((getActivity()));
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from record",null);
        //把查询数据封装到Cursor
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from record", null);
//        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        //用while循环遍历Cursor，再把它分别放到map中，最后统一存入list中，便于调用
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
            float money = cursor.getFloat(cursor.getColumnIndex(COLUMN_MONEY));
            String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));
            HashMap<String,Object> map=new HashMap<>();
//            Map<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(id));
            map.put("date", date);
            map.put("type", type);
            map.put("money", String.valueOf(money));
            map.put("state", state);
            list.add(map);
        }
        cursor.close();
        db.close();

        return list;
    }
    class  MyRecycleViewAdapter extends RecyclerView.Adapter<BillListFragment.MyRecycleViewAdapter.ViewHolder>
    {


        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView id;
            public TextView date;
            public TextView type;
            public TextView money;
            public TextView state;

            public ViewHolder(View convertView) {
                super(convertView);
                id= (TextView)convertView.findViewById(R.id.list_id);
                date= (TextView)convertView.findViewById(R.id.list_date);
                type= (TextView)convertView.findViewById(R.id.list_type);
                money= (TextView)convertView.findViewById(R.id.list_money);
                state = (TextView)convertView.findViewById(R.id.list_state);
            }
        }
        @NonNull
        @Override
        public BillListFragment.MyRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getActivity()).inflate(R.layout.record_item_layout,parent, false);
            final RecyclerView recyclerView=v.findViewById(R.id.recycler_view);

            return new BillListFragment.MyRecycleViewAdapter.ViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull BillListFragment.MyRecycleViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.id.setText((String)mData.get(position).get("id"));
            holder.date.setText((String)mData.get(position).get("date"));
            holder.type.setText((String)mData.get(position).get("type"));
            holder.money.setText((String)mData.get(position).get("money"));
            holder.state.setText((String)mData.get(position).get("state"));
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("id",(String)mData.get(position).get("id"));
//                    navController.navigate(R.id.detailFragment,bundle);
//
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BillListViewModel.class);
        // TODO: Use the ViewModel
    }


}