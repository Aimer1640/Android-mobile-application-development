package com.example.navigationview.ui.addthing;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.navigationview.R;

import com.example.navigationview.database.Thing;
import com.example.navigationview.database.MyDatabase;
import com.linchaolong.android.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class EditBookFragment extends Fragment {
    // 自动完成文本框
    AutoCompleteTextView autoCompleteTextViewthingid;
    String[] thingids = {"9787302288664", "9787302288665", "9787302288666"};
    // 退出按钮
    static final int EXIT_DIALOG_ID = 0;
    // 出版日期
    EditText publishtime;
    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    //picture
    private ImagePicker imagePicker = new ImagePicker();
    ImageView picture;
    byte[] photobytes = null;
    //save
    EditText thingname;
    String txtthingid;
    Thing thing;
//    Book book;
    byte[] b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editbook, container, false);
        txtthingid = getArguments().getString("thingid");
        // 自动完成文本框
        autoCompleteTextViewthingid = view.findViewById(R.id.thingid);
        ArrayAdapter<String> adapterauto = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, thingids);
        autoCompleteTextViewthingid.setAdapter(adapterauto);

        // 退出按钮
        Button exitButton = view.findViewById(R.id.btn_delete);
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onCreateDialog(EXIT_DIALOG_ID);

            }
        });
        // 出版日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        publishtime = (EditText) view.findViewById(R.id.publishtime);
        publishtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onCreateDialog(DATE_DIALOG_ID);
            }
        });

        // picture功能
        picture = view.findViewById(R.id.photo);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraOrGallery();
            }
        });

        //save(thingid,publishtime,picture已有)
        thingname = view.findViewById(R.id.thingname);
        //显示数据
        showdata();

        Button save = view.findViewById(R.id.btn_add);
        save.setOnClickListener(v -> {
            String txtthingid = autoCompleteTextViewthingid.getText().toString();
            String txtthingname = thingname.getText().toString();
            String txtpublishertime = publishtime.getText().toString();

            //用修改后数据,更新数据库
            new UpdateStudentTask(txtthingid, txtthingname, txtpublishertime,photobytes).execute();


            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_home);
        });
        return view;
    }

    private void showdata() {
        Bundle bundle = getArguments();
        String thingid = bundle.getString("thingid");
        //查询数据库获得数据，展示在页面对应的UI组件
        new SelectThingTask(thingid).execute();

        if (photobytes != null && photobytes.length > 0) {
            Bitmap image = BitmapFactory.decodeByteArray(photobytes, 0, photobytes.length);
            picture.setImageBitmap(image);
        } else {
            picture.setImageResource(R.mipmap.ic_launcher);
        }
    }

    // 对话框创建
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case EXIT_DIALOG_ID:// 退出对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());
                builder.setIcon(R.drawable.ic_menu_gallery);
                builder.setTitle("你确定要离开吗？");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 这里添加点击确定后的逻辑
                                getActivity().finish();
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 这里添加点击取消后的逻辑
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case DATE_DIALOG_ID:// 出版日期对话框
                new DatePickerDialog(getActivity(), mDateSetListener, mYear, mMonth,
                        mDay).show();
                break;

        }
        return null;
    }

    // 出版日期
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    // 出版日期 updates the date in the TextView
    private void updateDisplay() {
        publishtime.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mYear).append("-").append(mMonth + 1).append("-")
                .append(mDay));
    }

    //picture
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void startCameraOrGallery() {
        new AlertDialog.Builder(getActivity()).setTitle("设置图片")
                .setItems(new String[]{"从相册中选取图片", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 回调
                        ImagePicker.Callback callback = new ImagePicker.Callback() {
                            @Override
                            public void onPickImage(Uri imageUri) {
                            }

                            @Override
                            public void onCropImage(Uri imageUri) {
                                //picture.setImageURI(imageUri);
                                Glide.with(getActivity()).load(new File(imageUri.getPath())).into(picture);
                                Glide.with(getActivity()).load(new File(imageUri.getPath())).asBitmap().into(new SimpleTarget<Bitmap>(100, 100) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                                        //savedb
                                        photobytes = stream.toByteArray();
                                    }
                                });

                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(EditBookFragment.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(EditBookFragment.this, callback);
                        }
                    }
                })
                .show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }


    //查询数据的异步任务
    private class SelectThingTask extends AsyncTask<Void, Void, Void>
    {
        String thingid;
        Thing thing;
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
            autoCompleteTextViewthingid.setText(thingid);
            thingname.setText(thing.thingname);
            publishtime.setText(thing.publishtime);
            photobytes = thing.img;
        }
    }


    // 更新数据库的异步任务
    private class UpdateStudentTask extends AsyncTask<Void, Void, Void>
    {
        String thingid;
        String thingname;
        String publishtime ;
        byte[] photobytes;

        public UpdateStudentTask(String thingid, String thingname,String publishtime,byte[] photobytes)
        {
            this.thingid = thingid;
            this.thingname = thingname;
            this.publishtime = publishtime;
            this.photobytes=photobytes;
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            MyDatabase.getInstance(getContext()).thingsDao().updateThing(new Thing(thingid,thingname,publishtime,photobytes));
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
        }
    }
}