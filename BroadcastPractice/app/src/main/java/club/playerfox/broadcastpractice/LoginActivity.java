package club.playerfox.broadcastpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private EditText email;
    private EditText pass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        CheckBox remember = findViewById(R.id.remember);

        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        boolean remembered = sharedPreferences.getBoolean("remembered",false);

        if (remembered){
            remember.setChecked(remembered);
            email.setText(sharedPreferences.getString("email",null));
            pass.setText(sharedPreferences.getString("pass",null));
        }

        login.setOnClickListener(v -> {
            String emailStr = email.getText().toString();
            String passStr = pass.getText().toString();
            if (emailStr.equals("123@gmail.com") && passStr.equals("123")){
                if (!sharedPreferences.getBoolean("remembered",false) && remember.isChecked()){
                    saveInfo(emailStr, passStr);
                }
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this,"login failed",Toast.LENGTH_SHORT).show();
            }
        });

        remember.setOnClickListener(v -> {
            if (sharedPreferences.getBoolean("remembered",false)){
                deleteInfo();
                email.setText(null);
                pass.setText(null);
            }
        });
    }

    private void deleteInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("pass");
        editor.putBoolean("remembered",false);
        editor.apply();
    }

    private void saveInfo(String emailStr, String passStr) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("remembered",true);
        editor.putString("email",emailStr);
        editor.putString("pass",passStr);
        editor.apply();
    }
}
