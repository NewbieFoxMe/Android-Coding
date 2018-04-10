package club.playerfox.netpractice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtilOld {
    public static void sendHttpRequest(final String addr, final HttpCallBackListener listener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(addr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = buffer.readLine()) != null) {
                        builder.append(line);
                    }
                    listener.onFinish(builder.toString());
                } catch (Exception e) {
                    listener.onError(e);
                }
            }

        }.start();
    }
}
