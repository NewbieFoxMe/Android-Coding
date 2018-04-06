package club.playerfox.broadcastpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.login);
        EditText email = findViewById(R.id.email);
        EditText pass = findViewById(R.id.pass);
        login.setOnClickListener(v -> {
            if (email.getText().toString().equals("123@gmail.com") && pass.getText().toString().equals("123")){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this,"login failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
