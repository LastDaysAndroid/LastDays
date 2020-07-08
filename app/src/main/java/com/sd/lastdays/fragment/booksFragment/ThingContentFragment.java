package com.sd.lastdays.fragment.booksFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sd.lastdays.R;


public class ThingContentFragment extends Fragment {

    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thing_content, container, false);
        return view;
    }

    //将内容显示在界面上
    public void refresh(String newsTitle, String remainingTime, String targetDate) {

        View dateContentLayout = view.findViewById(R.id.dateContent_layout);
        dateContentLayout.setVisibility(View.VISIBLE);
        TextView title1 = (TextView) view.findViewById(R.id.dateContent_title);
        TextView remainingTime1 = (TextView) view.findViewById(R.id.dateContent_remainingTime);
        TextView targetDate1 = (TextView) view.findViewById(R.id.dateContent_targetDate);
        title1.setText(newsTitle); // 刷新标题
        remainingTime1.setText(remainingTime); // 刷新时间内容
        targetDate1.setText(targetDate);//刷新目标日期
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //移除碎片的视图时调用
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //碎片与活动解除关联时调用
    @Override
    public void onDetach() {
        super.onDetach();
    }
}