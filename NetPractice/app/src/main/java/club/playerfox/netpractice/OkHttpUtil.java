package club.playerfox.netpractice;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {
    public static void sendRequest(String spec, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(spec).build();
        client.newCall(request).enqueue(callback);
    }
}
