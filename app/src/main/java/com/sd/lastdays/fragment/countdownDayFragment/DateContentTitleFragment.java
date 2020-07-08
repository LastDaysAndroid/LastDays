package com.sd.lastdays.fragment.countdownDayFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.countdownDayBeans.DateContent;
import com.sd.lastdays.otherActivity.countdownDayActivity.DateContentActivity;
import com.sd.lastdays.util.DateContentTitleData;
import java.util.List;

/**
 * 倒数日标题碎片
 */
public class DateContentTitleFragment extends Fragment {

    private static String dateAccount = "admin";
    private static String card="noCard";

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        DateContentTitleFragment.card = card;
    }

    public String getDateAccount() {
        return dateAccount;
    }

    public void setDateAccount(String dateAccount) {
        DateContentTitleFragment.dateAccount = dateAccount;
    }

    private List<DateContent> list = new DateContentTitleData().getNews(dateAccount);

    public List<DateContent> getList() {
        return list;
    }

    public void setList(List<DateContent> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //System.out.println("dateAccount" + dateAccount);
        View view = inflater.inflate(R.layout.date_content_list, container, false);
        //1.获取RecyclerView实例  2.指定为线性布局 3.创建适配器实例传入数据  4.setAdapter调用完成配置，建立关联
        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.date_content_title_recycler_view);
        if ("card".equals(card)){
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
            newsTitleRecyclerView.setLayoutManager(layoutManager);
        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            newsTitleRecyclerView.setLayoutManager(layoutManager);
        }
        NewsAdapter adapter = new NewsAdapter(list);
        newsTitleRecyclerView.setAdapter(adapter);
        return view;
    }

    //内部RecyclerView的适配器
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<DateContent> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;
            TextView lastDaays;

            //最外层布局
            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
                lastDaays = (TextView) view.findViewById(R.id.last_day);
            }
        }

        //数据传入
        public NewsAdapter(List<DateContent> newsList) {
            mNewsList = newsList;
        }

        //以下三个重写方法
        //onCreateViewHolder ： 加载date_content_list_item布局 再传入ViewHolder的构造方法中
        //监听点击事件
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if ("card".equals(card)){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_content_list_item_card, parent, false);
            }else{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_content_list_item, parent, false);
            }
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateContent news = mNewsList.get(holder.getAdapterPosition());
                    DateContentActivity.actionStart(getActivity(), news.getTitle(), news.getRemainingTime(), news.getTargetDate(), news.getAccount(), news.getLastBook());
                }
            });
            return holder;
        }

        //会在每个子项滚动到屏幕时执行，进行赋值操作
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DateContent news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
            holder.lastDaays.setText(news.getRemainingTime());
        }

        //返回RecyclerView一共多少子项（长度）
        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

    }
}
