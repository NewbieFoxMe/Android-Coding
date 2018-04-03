package club.playerfox.www.activitylifecycletest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private Context context = this;
    private EditText etInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNormal = findViewById(R.id.btn_normal);
        ImageView ivImg = findViewById(R.id.iv_img);
        ProgressBar pbProcess = findViewById(R.id.pb_process);
        etInput = findViewById(R.id.et_input);
        btnNormal.setOnClickListener(v -> {
            Intent intent = new Intent(context,NormalActivity.class);
            startActivity(intent);
        });

        Button btnDialog = findViewById(R.id.btn_iml_page);
        btnDialog.setOnClickListener(v -> {
            Intent intent = new Intent(context,ImpBtnActivity.class);
            startActivity(intent);
        });

        Button btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(v -> {
            ActivityCollector.finishAll();
        });

        Button btnChangeImg = findViewById(R.id.btn_change_img);
        btnChangeImg.setOnClickListener(v -> {
            ivImg.setImageResource(R.drawable.index2);
            int progess = pbProcess.getProgress();
            progess += 10;
            pbProcess.setProgress(progess);
        });

        if (savedInstanceState != null){
            String input = savedInstanceState.getString(TAG);
            etInput.setText(input);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG, String.valueOf(etInput.getText()));
    }
}
