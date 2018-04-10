package club.playerfox.netpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView showHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendBtn = findViewById(R.id.send_btn);

        showHere = findViewById(R.id.show_here);
        sendBtn.setOnClickListener(v -> {
            OkHttpUtil.sendRequest("https://www.baidu.com", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    show(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    show(response.body().string());
                }
            });
        });

    }

    private void show(String s){
        runOnUiThread(() -> {
            showHere.setText(s);
        });
    }
}
