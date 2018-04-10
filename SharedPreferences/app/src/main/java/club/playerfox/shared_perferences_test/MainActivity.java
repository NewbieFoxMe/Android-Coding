package club.playerfox.shared_perferences_test;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private EditText saveKey;
    private EditText saveValue;
    private EditText readKey;
    private EditText readValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("key_values",MODE_PRIVATE);
        saveKey = findViewById(R.id.save_key);
        saveValue = findViewById(R.id.save_value);
        readKey = findViewById(R.id.read_key);
        readValue = findViewById(R.id.read_value);
        readValue.setEnabled(false);

        Button save = findViewById(R.id.save);
        Button read = findViewById(R.id.read);

        save.setOnClickListener(v -> {
            saveSomething();
        });

        read.setOnClickListener(v -> {
            readSomething();
        });
        readKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                readSomething();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void readSomething() {
        String value = sharedPreferences.getString(readKey.getText().toString(),null);
        readValue.setText(value);
    }

    private void saveSomething() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(saveKey.getText().toString(),saveValue.getText().toString());
        saveKey.setText(null);
        saveValue.setText(null);
        editor.apply();
    }

}
