package com.sd.lastdays.fragment.countdownDayFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sd.lastdays.R;

/**
 * 倒数日内容碎片
 */
public class DateContentFragment extends Fragment {
    private View view;

    //碎片与活动关联时调用
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //给碎片加载布局时调用
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将R.layout.fragment_a动态的加载到container中
        view = inflater.inflate(R.layout.date_content_frag, container, false);
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

    //确报与碎片相关联的活动一定已经创建完毕时调用
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

    //保存临时数据
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*String name = "123";
        bundle.putString("data_key",name);
        在oncreate中取出
        if(savedInstanceState!=null){
            String name = savedInstanceState.getStirng("data_key);
            Log.d(TAG,name);
        }
        */
    }
}
