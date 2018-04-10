package club.playerfox.jsonparsetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String str = "[{'id':'5','version':'5.5'}," +
            "{'id':'6','version':'6.5'}," +
            "{'id':'7','version':'7.5'}]";

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.hello);
        //parseJson();
        gsonParser();
    }

    private void gsonParser() {
        textView.setText(null);
        Gson gson = new Gson();
        List<Card> cardList = gson.fromJson(str,new TypeToken<List<Card>>(){}.getType());
        for (Card card:cardList){
            String id = card.getId();
            String version = card.getVersion();
            String show = "id:" + id + " version:" + version + "\n";
            textView.append(show);
        }
    }

    private void parseJson() {
        textView.setText(null);
        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < jsonArray.length() ; i ++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("id");
                String version = object.getString("version");
                String show = "id:" + id + " version:" + version + "\n";
                textView.append(show);
            }
        }catch (Exception e){
            Log.e(TAG, "parseJson: " + e.toString());
        }
    }
}
