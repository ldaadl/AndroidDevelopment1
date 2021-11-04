package com.example.mywork1_lda.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.mywork1_lda.activity.MainActivity2;
import com.example.mywork1_lda.service.PlayWhenSwitch;

public class MyReceiver extends BroadcastReceiver {
    private ServiceConnection connection;
    private PlayWhenSwitch.Mybinder mybinder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("receiver","BroadcastReceiver onReceive");
        if(connection==null) {
            Intent intent1 = new Intent(context, PlayWhenSwitch.class);
            intent1.setType("main");
            intent1.putExtra("who", "main");
            connection = new ServiceConnection() {  // 每次new新的对象
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Log.d("serviceWithBind", "onServiceConnected");
                    mybinder = (PlayWhenSwitch.Mybinder) iBinder;
                    mybinder.play();
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {  // 意外丢失连接时调用
                    Log.d("serviceWithBind", "onServiceDisconnected");
                }
            };
            context.bindService(intent1, connection, Context.BIND_AUTO_CREATE);
        }else{
            Toast.makeText(context,"已绑定",Toast.LENGTH_LONG).show();
        }

        Intent intent2 = new Intent(context, MainActivity2.class);
        Bundle data = intent.getExtras();
        intent2.putExtras(data);
        context.startActivity(intent2);

        // 5秒后停止音乐
       Thread t = new Thread(new playMusic(context));
       t.start();

    }

    class playMusic  implements Runnable{

        Context context;

        public playMusic(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            SystemClock.sleep(5000);
            if(connection!=null) {
                context.unbindService(connection);  // 解除绑定后，connection不会被置为null，很有可能还保留着上一次的绑定信息
                connection = null;
            }
            else
                Toast.makeText(context,"当前绑定已经解除或者还未绑定",Toast.LENGTH_LONG).show();
        }

    }
}