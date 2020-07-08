package com.sd.lastdays.fragment.historyDayFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sd.lastdays.R;
import com.sd.lastdays.beans.historyDayBeans.HistoryDays;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 导航栏第二项
 */

public class HistoryDayFragment extends Fragment {

    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private String[] mDec;
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;

    private RecyclerView recyclerView;
    private List<HistoryDays> day = new ArrayList<HistoryDays>();
    private View view;
    private  Context selfContent;
    private DayAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_day_fragment, container,false);
        selfContent = getActivity();


        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        sendRequestWithOKHttp();//实现获取json数据进行显示
        initLoopView();  //实现轮播图

        return view;
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            day = (List<HistoryDays>) msg.obj;
            adapter = new DayAdapter(day);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();//通知adapter刷新数据
        };
    };

    //进行网络请求
    private void sendRequestWithOKHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://www.liuzhi.org.cn/android_study/get_data.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
//                    Log.d("tag",responseData);
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        day = gson.fromJson(jsonData,new TypeToken<List<HistoryDays>>(){}.getType());
        Message message = Message.obtain();
        message.obj = day;
        mHandler.sendMessage(message);
    }

//    轮播图实现部分
    private void initLoopView() {

        viewPager = (ViewPager) view.findViewById(R.id.loopviewpager);
        ll_dots_container = (LinearLayout)view.findViewById(R.id.ll_dots_loop);
        loop_dec = (TextView)view.findViewById(R.id.loop_dec);

        // 图片资源id数组
        mImg = new int[]{
                R.drawable.t1,
                R.drawable.t2,
                R.drawable.t3,
                R.drawable.t4,
                R.drawable.t5
        };

        // 文本描述
        mDec = new String[]{
                "1",
                "2",
                "3",
                "4",
                "5"
        };

        mImg_id = new int[]{
                R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                R.id.pager_img4,
                R.id.pager_img5
        };

        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View dotView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<mImg.length;i++){
            //初始化要显示的图片对象
            imageView = new ImageView(selfContent);
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new pagerOnClickListener(selfContent.getApplicationContext()));
            mImgList.add(imageView);
            //加引导点
            dotView = new View(selfContent);
            dotView.setBackgroundResource(R.drawable.gray_dot);
            layoutParams = new LinearLayout.LayoutParams(10,10);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            //设置默认所有都不可用
            dotView.setEnabled(false);
            ll_dots_container.addView(dotView,layoutParams);
        }

        ll_dots_container.getChildAt(0).setEnabled(true);
        loop_dec.setText(mDec[0]);
        previousSelectedPosition=0;
        //设置适配器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) %mImgList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int newPosition = i % mImgList.size();
                loop_dec.setText(mDec[newPosition]);
                ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
                ll_dots_container.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 开启轮询
        new Thread(){
            @Override
            public void run(){
                isRunning = true;
                while(isRunning){
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }.start();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
