package club.playerfox.broadcastpractice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.offline);
        button.setOnClickListener(v -> {
            Intent intent = new Intent("club.playerfox.broadcastpractice.OFFLINE");
            sendBroadcast(intent);
        });
    }
}
