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
import com.sd.lastdays.beans.booksBeans.Photos;
import com.sd.lastdays.beans.booksBeans.Photos;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

    private List<Photos> photoList = new ArrayList<Photos>();
    private LocalBroadcastManager localBroadcastManager;//本地广播管理器

    static class ViewHolder extends RecyclerView.ViewHolder {
        View photoView;
        ImageView photoImage;

        public ViewHolder(View view) {
            super(view);
            photoView = view;
            photoImage = (ImageView) view.findViewById(R.id.photo_image);
        }
    }

    public PhotoAdapter(List<Photos> photo) {
        photoList = photo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        localBroadcastManager = LocalBroadcastManager.getInstance(parent.getContext());
        final Intent intent = new Intent("com.sd.lastdays.LOCAL_BROADCAST");
        holder.photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Photos photo = photoList.get(position);

                //通过广播把数据传递给父碎片
                intent.putExtra("photo",photo.getImageId());
                localBroadcastManager.sendBroadcast(intent);
                Toast.makeText(v.getContext(), "选择封面成功", Toast.LENGTH_SHORT).show();

            }
        });
        return holder;
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        Photos photo = photoList.get(position);
        holder.photoImage.setImageResource(photo.getImageId());

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

}