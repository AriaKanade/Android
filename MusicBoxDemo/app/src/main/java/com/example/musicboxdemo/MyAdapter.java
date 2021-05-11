package com.example.musicboxdemo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicboxdemo.entity.Music;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Music> list = new ArrayList<>();


    public MyAdapter(List<Music> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Music music = list.get(position);
        holder.author.setText(music.getAuthor());
        holder.title.setText(music.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setAction(MainActivity.CHANGE_ACTION);
                intent.putExtra("current",this.getAdapterPosition());
                intent.putExtra("code",1);
                itemView.getContext().sendBroadcast(intent);
            });
        }
    }
}
