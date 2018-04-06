package club.playerfox.broadcasttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"local reveiver",Toast.LENGTH_SHORT).show();
        }
    }

    private LocalBroadcastManager manager;
    private LocalReceiver receiver;
    private IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = LocalBroadcastManager.getInstance(this);
        Button button = findViewById(R.id.send);
        button.setOnClickListener(v -> {
            Intent intent = new Intent("club.playerfox.broadcasttest2.LOCAL_BROADCAST");
            manager.sendBroadcast(intent);
        });

        filter = new IntentFilter();
        filter.addAction("club.playerfox.broadcasttest2.LOCAL_BROADCAST");
        receiver = new LocalReceiver();
        manager.registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(receiver);
    }
}
