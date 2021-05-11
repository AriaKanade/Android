package com.example.musicboxdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.musicboxdemo.entity.Music;
import com.example.musicboxdemo.utils.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service {

    private MusicServiceReceiver musicServiceReceiver;

    private Status status;

    private int current = -1;

    MediaPlayer mediaPlayer;

    private AssetManager assetManager;

    private List<Music> list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        status = Status.NOT_INITIALIZED;
        assetManager = getAssets();
        dataInit();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivity.CONTROL_ACTION);
        intentFilter.addAction(MainActivity.CHANGE_ACTION);

        musicServiceReceiver = new MusicServiceReceiver();

        registerReceiver(musicServiceReceiver,intentFilter);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;
                if (current >= list.size()){
                    current = 0;
                }
                Intent sendIntent = new Intent(MainActivity.UPDATE_ACTION);
                sendIntent.putExtra("current", current);
                sendBroadcast(sendIntent);
                prepareAndPlay(list.get(current).getFileName());
            }
        });
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MusicServiceReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int receiveCurrent = intent.getIntExtra("current",-1);
            int code = intent.getIntExtra("code",-1);
            if(receiveCurrent != -1){
                current = receiveCurrent;
                prepareAndPlay(list.get(current).getFileName());
            }

            switch (code){
                case 1:
                    if(status == Status.PLAYING){
                        mediaPlayer.pause();
                        status = Status.PAUSING;
                    }else if(status == Status.PAUSING){
                        mediaPlayer.start();
                        status = Status.PLAYING;
                    }else{
                        prepareAndPlay(list.get(0).getFileName());
                        current = 0;
                        status = Status.PLAYING;
                    }
                    break;
                case 2:
                    if(status == Status.NOT_INITIALIZED){
                        Toast.makeText(getBaseContext(),"请先开始播放!",Toast.LENGTH_SHORT);
                    }
                    else{
                        current++;
                        if (current >= list.size()){
                            current = 0;
                        }
                        prepareAndPlay(list.get(current).getFileName());
                        status = Status.PLAYING;
                    }
                    break;
                case 3:
                    if(status == Status.NOT_INITIALIZED){
                        Toast.makeText(getBaseContext(),"请先开始播放!",Toast.LENGTH_SHORT);
                    }
                    else {
                        current--;
                        if (current < 0){
                            current = list.size() - 1;
                        }
                        prepareAndPlay(list.get(current).getFileName());
                        status = Status.PLAYING;
                    }
                    break;

            }
            Intent sendIntent = new Intent(MainActivity.UPDATE_ACTION);
            sendIntent.putExtra("update", status.getIndex());
            sendIntent.putExtra("current", current);
            sendBroadcast(sendIntent);
        }
    }

    private void prepareAndPlay(String music)
    {
        try
        {
            AssetFileDescriptor afd = assetManager.openFd(music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            status = Status.PLAYING;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
