package club.playerfox.mediause;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private NotificationManager manager;
    private Notification notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.notify);
        button.setOnClickListener(v -> {
            notifyMe();
        });
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> manager.cancel(1));
    }

    private void notifyMe() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new NotificationCompat.Builder(this,"default")
                .setContentTitle("TitleX")
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("this is a nogissssssssssssssssszzzzzzzzzzzzzzzz" +
//                        "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"))
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.bd_logo1)))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.all)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.all))
                .setAutoCancel(true)
//                .setVibrate(new long[]{0,1000,1000,1000})
//                .setLights(Color.GREEN,1000,1000)
                .build();
        manager.notify(1,notification);
    }
}
