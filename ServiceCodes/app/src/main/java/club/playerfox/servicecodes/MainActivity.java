package club.playerfox.servicecodes;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyService.MyBinder myBinder;
    private static final String TAG = "MainActivity";
    public static final int UPDATE_UI = 1;
    private Context context;
    private TextView textView;
    private Handler handler;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.hello);
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what){
//                    case UPDATE_UI:
//                        textView.setText("change");
//                        break;
//                }
//            }
//        };
//        new Thread(){
//            @Override
//            public void run() {
//                Message message = new Message();
//                message.what = UPDATE_UI;
//                handler.sendMessage(message);
//            }
//        }.start();
        Button startService = findViewById(R.id.start_service);
        startService.setOnClickListener(v -> {
            Intent intent = new Intent(this,MyService.class);
            startService(intent);

        });
        Button stopService = findViewById(R.id.stop_service);
        stopService.setOnClickListener(v -> {
            Intent intent = new Intent(this,MyService.class);
            stopService(intent);
        });

        Button bindBtn = findViewById(R.id.bind_service);
        Button unbindBtn = findViewById(R.id.unbind_service);

        bindBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this,MyService.class);
            bindService(intent,connection,BIND_AUTO_CREATE);
        });

        unbindBtn.setOnClickListener(v -> {
            unbindService(connection);
        });

        Button intentService = findViewById(R.id.intent_service);
        intentService.setOnClickListener(v -> {
            Log.e(TAG, "Main:" + Thread.currentThread());
            Intent intent = new Intent(this,MyIntentService.class);
            startService(intent);
        });
    }
}
