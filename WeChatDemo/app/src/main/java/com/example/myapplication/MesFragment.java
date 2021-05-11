package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MesFragment extends Fragment {

    private List<Userdata> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mes, container, false);
        init();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.MesListView);
        recyclerView.setLayoutManager(linearLayoutManager);
        UserAdapter userAdapter = new UserAdapter(list);
        recyclerView.setAdapter(userAdapter);
        return view;
    }


    private void init() {
        if(list.size() != 0){
            list.clear();
        }
        Userdata u1 = new Userdata();
        u1.setImageId(R.drawable.tab_weixin_normal);
        u1.setMessage("你好！");
        u1.setUserName("小明");
        list.add(u1);
        Userdata u2 = new Userdata();
        u2.setImageId(R.drawable.tab_weixin_pressed);
        u2.setMessage("你好！");
        u2.setUserName("小红");
        list.add(u2);
    }
}