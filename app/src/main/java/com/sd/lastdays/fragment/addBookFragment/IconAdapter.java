package com.sd.lastdays.fragment.addBookFragment;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Icons;

import java.util.ArrayList;
import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder>{

    private List<Icons> iconList = new ArrayList<Icons>();
    private LocalBroadcastManager localBroadcastManager;//本地广播管理器

    static class ViewHolder extends RecyclerView.ViewHolder {
        View iconView;
        ImageView iconImage;

        public ViewHolder(View view) {
            super(view);
            iconView = view;
            iconImage = (ImageView) view.findViewById(R.id.icon_image);
        }
    }

    public IconAdapter(List<Icons> icon) {
        iconList = icon;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        localBroadcastManager = LocalBroadcastManager.getInstance(parent.getContext());
        final Intent intent = new Intent("com.sd.lastdays.LOCAL_ICON_BROADCAST");

        holder.iconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Icons icon = iconList.get(position);

                //通过广播把数据传递给父碎片
                intent.putExtra("icon",icon.getImageId());
                localBroadcastManager.sendBroadcast(intent);

                Toast.makeText(v.getContext(), "选择图标成功", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Icons icon = iconList.get(position);
        holder.iconImage.setImageResource(icon.getImageId());
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

}