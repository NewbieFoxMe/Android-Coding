package club.playerfox.servicecodes;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivities(this,0,new Intent[]{intent},0);
        Notification notification = new NotificationCompat.Builder(this,"default")
                .setContentTitle("这是一个通知")
                .setContentText("这是通知内容")
                .setSmallIcon(R.drawable.sun)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .build();

        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service", "onStartCommand" );
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("service", "onDestroy" );
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("bind:", "onBind: ");
        return myBinder;
    }

    class MyBinder extends Binder{

    }
}
