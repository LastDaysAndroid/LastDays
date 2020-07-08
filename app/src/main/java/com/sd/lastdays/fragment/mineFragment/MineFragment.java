package com.sd.lastdays.fragment.mineFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sd.lastdays.R;
import com.sd.lastdays.otherActivity.mineActivity.MineActivity;
import com.sd.lastdays.util.ActivityCollector;

/**
 * 我的碎片
 */

public class MineFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mine_1, container, false);

        Button viewById = (Button) view.findViewById(R.id.login_2);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineActivity.actionStart(getContext());
            }
        });
        Button viewById2 = (Button) view.findViewById(R.id.login_out);
        viewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishAll();
            }
        });
        return view;
    }

/*    //将内容显示在界面上
    public void refresh_view(String account) {
        View mineLayout = view.findViewById(R.id.mine_layout_2);
        mineLayout.setVisibility(View.VISIBLE);
        TextView username = (TextView) view.findViewById(R.id.username_2);
        username.setText(account); // 刷新标题
    }*/

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
