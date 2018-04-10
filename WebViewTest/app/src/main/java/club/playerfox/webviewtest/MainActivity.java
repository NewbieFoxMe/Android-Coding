package club.playerfox.webviewtest;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button load = findViewById(R.id.http_conn);
        textView = findViewById(R.id.text_res);
        showWebView();
        load.setOnClickListener(v -> {
            new Thread(){
                @Override
                public void run() {
                    httpConnection();
                }
            }.start();
        });

    }

    private void httpConnection() {
        byte[] bytes = new byte[1024];
        int len;


        try {
            URL url = new URL("http://www.baidu.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedInputStream buffer = new BufferedInputStream(in);
            StringBuilder response = new StringBuilder();
            while ((len = buffer.read(bytes)) != -1){
                response.append(new String(bytes,0,len));
            }
            showUIRes(response.toString());
            buffer.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            Log.e(TAG, "httpConnection: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "httpConnection: " + e.toString());
        }
    }

    private void showUIRes(String s) {
        runOnUiThread(() -> {
            textView.setText(s);
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showWebView() {
        WebView webView = findViewById(R.id.web_view);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webView.loadUrl("http://www.playerfox.club");
    }
}
