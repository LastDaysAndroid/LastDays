package com.sd.lastdays.fragment.manageBookFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.BookType;

import java.util.List;

public class BookTypeAdapter extends RecyclerView.Adapter<BookTypeAdapter.ViewHolder> {
    private List<BookType> bookTypeList;

    public BookTypeAdapter(List<BookType> bookList) {
        bookTypeList = bookList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView bookTypeImage;
        TextView bookTypeName;

        public ViewHolder(View view) {
            super(view);
            fruitView = view;
            bookTypeImage = (ImageView) view.findViewById(R.id.book_type_image);
            bookTypeName = (TextView) view.findViewById(R.id.book_type_name);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_type_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final BookType bookType = bookTypeList.get(position);
        holder.bookTypeImage.setImageResource(bookType.getImageId());
        holder.bookTypeName.setText(bookType.getName());

        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "you clicked view " + bookType.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.bookTypeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "you clicked image " + bookType.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookTypeList.size();
    }
}