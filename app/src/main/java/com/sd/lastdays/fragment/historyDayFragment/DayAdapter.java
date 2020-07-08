package com.sd.lastdays.fragment.historyDayFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.historyDayBeans.HistoryDays;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{

    private List<HistoryDays> daysList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View iconView;
        TextView dayYear;
        TextView dayEvent;

        public ViewHolder(View view) {
            super(view);
            iconView = view;
            dayYear = (TextView) view.findViewById(R.id.day_year);
            dayEvent = (TextView) view.findViewById(R.id.day_event);
        }
    }

    public DayAdapter(List<HistoryDays> days) {
        daysList = days;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_day_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryDays day = daysList.get(position);
//        Log.d("abc", day.getYear());
//        Log.d("abc",day.getEvent());
        holder.dayYear.setText(day.getYear());
        holder.dayEvent.setText(day.getEvent());
    }

    @Override
    public int getItemCount() {
//        Log.d("abc", String.valueOf(daysList.size()));
        return daysList.size();
    }

}