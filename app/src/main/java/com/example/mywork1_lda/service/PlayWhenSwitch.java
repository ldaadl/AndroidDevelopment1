package com.example.mywork1_lda.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.mywork1_lda.R;

public class PlayWhenSwitch extends Service {
    private MediaPlayer mediaPlayer;
    public PlayWhenSwitch() {
        Log.d("service", "playWhenSwitch has been new");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.the_sound_of_silence);
        Log.d("service","playWhenSwitch oncreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service","playWhenSwitch onstartcommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("service","playWhenSwitch ondestory");
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("service","playWhenSwitch unbind");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Bundle data = intent.getExtras();
        String who= data.getString("who");
        Log.d("service",who+" onbind with service2");
        // TODO: Return the communication channel to the service.
        return new Mybinder();
    }
    public class Mybinder extends Binder {  // 因为调用他的activity在其他包
        public void play(){
            mediaPlayer.start();
        }
    }
}