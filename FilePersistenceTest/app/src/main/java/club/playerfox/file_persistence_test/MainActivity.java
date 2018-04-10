package club.playerfox.file_persistence_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button save = findViewById(R.id.save);
        Button read = findViewById(R.id.read);
        content = findViewById(R.id.content);
        readSomething();
        save.setOnClickListener(v -> {
            saveSomething();
        });
        read.setOnClickListener(v -> {
            readSomething();
        });
    }

    @Override
    protected void onStop() {
        saveSomething();
        super.onStop();
    }

    private void readSomething() {
        StringBuilder str = new StringBuilder();
        int len;
        byte[] bytes = new byte[1024];
        try (
                FileInputStream inputStream = openFileInput("data");
                BufferedInputStream in = new BufferedInputStream(inputStream);
        ){
            while ((len = in.read(bytes)) != -1){
                str.append(new String(bytes,0,len));
            }
        }catch (IOException e){
            Log.d(TAG, "readSomething: " + e.toString());
        }
        if (!TextUtils.isEmpty(str.toString())){
            content.setText(str.toString());
            content.setSelection(str.toString().length());
        }

    }

    private void saveSomething() {
        String data = content.getText().toString().trim();
        if (!TextUtils.isEmpty(data)){
            try(
                    FileOutputStream  outputStream = openFileOutput("data",MODE_PRIVATE);
                    BufferedOutputStream out = new BufferedOutputStream(outputStream);
            ){
                out.write(data.getBytes());
            }catch (IOException e){
                Log.d(TAG, "saveSomething: " + e.toString());
            }
        }

    }
}
