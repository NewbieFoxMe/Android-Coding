package club.playerfox.okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView showHere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn_ok);
        showHere = findViewById(R.id.show_here);
        button.setOnClickListener(v -> {
            new Thread(){
                @Override
                public void run() {
                    startConnection();
                }
            }.start();

        });
    }

    private void startConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.w3school.com.cn/example/xmle/note.xml")
                .build();
        Response response = null;
        String responseData = null;
        try {
            response = client.newCall(request).execute();
            responseData = response.body().string();
            //showText(responseData);
            //paserXML(responseData);
            parseSAX(responseData);
        } catch (Exception e) {
            Log.e(TAG, "startConnection: " + e.toString());
        }
    }

    private void parseSAX(String responseData) {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();
            SAXParserHandler handler = new SAXParserHandler(this);
            reader.setContentHandler(handler);
            reader.parse(new InputSource(new StringReader(responseData)));
        }catch (Exception e){
            Log.e(TAG, "parseSAX: " + e.toString());
        }
    }

    private void paserXML(String responseData) {

        String to = "";
        String from = "";
        String heading = "";
        String body = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(responseData));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if ("to".equals(nodeName)){
                            to = parser.nextText();
                        }else if ("from".equals(nodeName)){
                            from = parser.nextText();
                        }else if ("heading".equals(nodeName)){
                            heading = parser.nextText();
                        }else if ("body".equals(nodeName)){
                            body = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("note".equals(nodeName)){
                            String showText = "to:" + to + "\nfrom:" + from + "\nheading:" +
                                    heading + "\nbody:" + body;
                            showText(showText);
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            Log.e(TAG, "paserXML: " + e.toString());
        }
    }

    public void showText(String s) {
        runOnUiThread(() -> {
            showHere.setText(s);
        });
    }
}
