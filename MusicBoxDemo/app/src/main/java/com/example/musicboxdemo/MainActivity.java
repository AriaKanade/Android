package com.example.musicboxdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicboxdemo.entity.Music;
import com.example.musicboxdemo.utils.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainBroadcastReceiver mainBroadcastReceiver;

    private Status status;

    private TextView author,title;

    private RecyclerView songList;

    private List<Music> list = new ArrayList<>();

    private ImageButton play,next,previous;

    private AssetManager assetManager;

    public static final String CONTROL_ACTION = "control_music_play";
    public static final String CHANGE_ACTION = "change_by_click";
    public static final String UPDATE_ACTION = "update_by_service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = Status.NOT_INITIALIZED;
        dataInit();

        songList = findViewById(R.id.songlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        songList.setLayoutManager(linearLayoutManager);
        MyAdapter myAdapter = new MyAdapter(list);
        songList.setAdapter(myAdapter);


        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

        author = findViewById(R.id.authorPlaying);
        title = findViewById(R.id.titlePlaying);

        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UPDATE_ACTION);

        mainBroadcastReceiver = new MainBroadcastReceiver();

        registerReceiver(mainBroadcastReceiver,intentFilter);

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);

    }

    public void dataInit(){
        try {
            assetManager = getAssets();
            for (String fileName:assetManager.list("")){
                if(fileName.contains(".mp3")){
                    String musicName = fileName.replace(".mp3","");
                    if(fileName.contains("-")){
                        list.add(new Music(musicName.split("-")[0],musicName.split("-")[1],fileName));
                    }else{
                        list.add(new Music(musicName,"无相关信息",fileName));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(CONTROL_ACTION);
        int code = -1;
        switch (v.getId()){
            case R.id.play:
                code = 1;
                break;
            case R.id.next:
                code = 2;
                break;
            case R.id.previous:
                code = 3;
                break;
        }
        intent.putExtra("code",code);
        sendBroadcast(intent);
    }

    class MainBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int update = intent.getIntExtra("update", -1);
            int current = intent.getIntExtra("current", -1);
            if (current >= 0)
            {
                title.setText(list.get(current).getTitle());
                author.setText(list.get(current).getAuthor());
            }
            switch (update){
                case 1:
                    status = Status.PLAYING;
                    play.setImageResource(R.drawable.pause);
                    break;
                case 2:
                    status = Status.PAUSING;
                    play.setImageResource(R.drawable.play);
                    break;
            }
        }
    }
}