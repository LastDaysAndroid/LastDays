package com.sd.lastdays.fragment.booksFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.lastdays.MainActivity;
import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Icons;
import com.sd.lastdays.beans.booksBeans.Photos;
import com.sd.lastdays.fragment.caoyuan.IconAdapter;
import com.sd.lastdays.fragment.caoyuan.PhotoAdapter;
import com.sd.lastdays.otherActivity.LastDayShowActivity.BookContentActivity;
import com.sd.lastdays.otherActivity.LastDayShowActivity.EditBookActivity;
import com.sd.lastdays.util.PenultimateContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditBookFragment extends Fragment {

    private List<Icons> iconsList  = new ArrayList<Icons>();//图标
    private List<Photos> photoList = new ArrayList<Photos>();//封面
    private View view;
    private IntentFilter intentFilter;
    private EditBookFragment.LocalReceiver localReceiver;     //本地广播接收者BroadcastReceiver
    private LocalBroadcastManager localBroadcastManager;
    private int iconId;
    private int photoId;
    private String bookTitle;//BookContentActivity传过来的
    private String bookTitle1;//AddThingActivity传过来的
    private String account = "admin";
    public void setAccount(String account) {
        this.account = account;
    }

    public void recieveInfo(String account1,String booName,String bookName1){
        bookTitle = booName;
        account = account1;
        bookTitle1 = bookName1;
    }
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.edit_book_fragment,container,false);
        initIcons();
        initPhotos();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView recyclerView1 =(RecyclerView) view.findViewById(R.id.photo_recycle_view);

        //利用网格布局来显示图标
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),4);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),4);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);

        //获取LocalBroadcastManager   本地广播管理者实例
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localReceiver = new LocalReceiver();

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.sd.lastdays.LOCAL_BROADCAST");   //添加photo action
        intentFilter.addAction("com.sd.lastdays.LOCAL_ICON_BROADCAST");

        localBroadcastManager.registerReceiver(localReceiver,intentFilter);   //注册本地广播
        IconAdapter adapter = new IconAdapter(iconsList);
        recyclerView.setAdapter(adapter);

        PhotoAdapter adapter1 = new PhotoAdapter(photoList);
        recyclerView1.setAdapter(adapter1);
//        //接收到此倒数本的名称
        EditText editText = view.findViewById(R.id.book_name);
        editText.setText(bookTitle);
        if (bookTitle1 != null){
            editText.setText(bookTitle1);
        }
        Button reset = view.findViewById(R.id.reset_button);
        Button save = view.findViewById(R.id.save_button);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"chongzhi",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                alert.setMessage("确认重置？");
                alert.setCancelable(true);
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"点击了取消",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("重置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),"点击了重置",Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookText = editText.getText().toString();
                PenultimateContent penultimateContent = new PenultimateContent();
                if (penultimateContent.editBookInfo(bookTitle,account,bookText,iconId,photoId)){
                    Toast.makeText(getActivity(),"编辑成功",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
//                    Intent intent = new Intent(getActivity(), BookListFragment.class);
//                    intent.putExtra("bookname",bookText);
//                    intent.putExtra("bookicon",iconId);
//                    intent.putExtra("bookphoto",photoId);
                    //startActivity(intent);
                    getActivity().finish();
                    //Intent intent = new Intent("android.intent.action.CART_BROADCAST");
//                    intent.putExtra("data","refresh");
//                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
//                    getActivity().sendBroadcast(intent);
//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    Fragment fragment = new BookListFragment();
//                    fm.beginTransaction().replace(R.id.book_fragment,fragment).commit();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        return view;
    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.sd.lastdays.LOCAL_BROADCAST")){
                int photo = intent.getIntExtra("photo",1);
                if(photo!=1) {
                    Log.d("tag", String.valueOf(photo));
                    photoId = photo;
                }
                Log.d("tag","执行了photoReceiver");
            }
            if(action.equals("com.sd.lastdays.LOCAL_ICON_BROADCAST")) {
                int icon = intent.getIntExtra("icon",1);
                if(icon!=1) {
                    Log.d("tag", String.valueOf(icon));
                    iconId = icon;
                }
                Log.d("tag","执行了iconlocalReceiver");
            }

            Log.d("tag","执行了receive");


        }
    }

    private void initPhotos() {
        Photos a = new Photos(R.drawable.photo1);
        photoList.add(a);

        Photos icon2 = new Photos(R.drawable.photo2);
        photoList.add(icon2);

        Photos icon3 = new Photos(R.drawable.photo3);
        photoList.add(icon3);

        Photos icon4 = new Photos(R.drawable.photo4);
        photoList.add(icon4);

        Photos icon5 = new Photos(R.drawable.photo5);
        photoList.add(icon5);


        Photos orange = new Photos(R.drawable.photo6);
        photoList.add(orange);


        Photos photo7 = new Photos(R.drawable.photo7);
        photoList.add(photo7);

        Photos photo8 = new Photos(R.drawable.photo8);
        photoList.add(photo8);

        Photos photo9 = new Photos(R.drawable.photo9);
        photoList.add(photo9);

        Photos photo10 = new Photos(R.drawable.photo10);
        photoList.add(photo10);

        Photos photo11 = new Photos(R.drawable.photo11);
        photoList.add(photo11);

        Photos photo12 = new Photos(R.drawable.photo12);
        photoList.add(photo12);
    }

    private void initIcons() {
        Icons icon1 = new Icons(R.drawable.icon1);
        iconsList.add(icon1);

        Icons icon2 = new Icons(R.drawable.icon2);
        iconsList.add(icon2);

        Icons icon3 = new Icons(R.drawable.icon3);
        iconsList.add(icon3);

        Icons icon4 = new Icons(R.drawable.icon4);
        iconsList.add(icon4);

        Icons icon5 = new Icons(R.drawable.icon5);
        iconsList.add(icon5);


        Icons orange = new Icons(R.drawable.icon6);
        iconsList.add(orange);
        Icons watermelon = new Icons(R.drawable.icon7);
        iconsList.add(watermelon);
        Icons pear = new Icons(R.drawable.icon8);
        iconsList.add(pear);
        Icons grape = new Icons(R.drawable.icon9);
        iconsList.add(grape);
        Icons pineapple = new Icons(R.drawable.icon10);
        iconsList.add(pineapple);
        Icons strawberry = new Icons(R.drawable.icon11);
        iconsList.add(strawberry);
        Icons cherry = new Icons(R.drawable.icon12);
        iconsList.add(cherry);
        Icons mango = new Icons(R.drawable.icon13);
        iconsList.add(mango);

        Icons icon14 = new Icons(R.drawable.icon14);
        iconsList.add(icon14);

        Icons icon15 = new Icons(R.drawable.icon15);
        iconsList.add(icon15);

        Icons icon16 = new Icons(R.drawable.icon16);
        iconsList.add(icon16);

    }

}
