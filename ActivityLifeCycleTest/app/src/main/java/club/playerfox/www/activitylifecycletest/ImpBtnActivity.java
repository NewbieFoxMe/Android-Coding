package club.playerfox.www.activitylifecycletest;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ImpBtnActivity extends BaseActivity implements View.OnClickListener {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imp_btn);
        findViewById(R.id.btn_alert_dialog).setOnClickListener(this);
        findViewById(R.id.btn_process_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_alert_dialog:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Dialog Title");
                builder.setMessage("Message");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    Toast.makeText(context,"Choose OK",Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Cancel",((dialog, which) -> {
                    Toast.makeText(context,"Choose Cancel",Toast.LENGTH_SHORT).show();
                }));
                builder.show();
                break;
            case R.id.btn_process_dialog:
                ProgressDialog.Builder progressBuilder = new ProgressDialog.Builder(context);
                progressBuilder.setTitle("ProgressDialog Title");
                progressBuilder.setMessage("Loading...");
                progressBuilder.setCancelable(true);
                progressBuilder.show();
                break;
        }
    }
}
