package com.example.navigationview.ui.mypage;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationview.LoginActivity;
import com.example.navigationview.R;
import com.example.navigationview.User;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {



    private MyPageViewModel mViewModel;

    public static MyPageFragment newInstance() {
        return new MyPageFragment();
    }
    ArrayList<User> list;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_page, container, false);
        Intent intent = getActivity().getIntent();

        list =intent.getParcelableArrayListExtra("LoginUser");
        User user=list.get(0);
        final String username=user.getUserId();

        TextView tv_welcome=view.findViewById(R.id.tv_welcome);
        tv_welcome.setText("欢迎您 ,用户"+username);

        //退出按键
        ImageView btn_exit=view.findViewById(R.id.btn_exit);
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


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyPageViewModel.class);
        // TODO: Use the ViewModel
    }

}