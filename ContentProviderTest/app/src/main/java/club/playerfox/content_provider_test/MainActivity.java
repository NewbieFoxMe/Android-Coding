package club.playerfox.content_provider_test;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView showText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button call = findViewById(R.id.call);
        call.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
            }else {
                callPhone();
            }
        });

        Button show =  findViewById(R.id.show);
        showText = findViewById(R.id.show_text);
        show.setOnClickListener(v -> {
            showText.setText(null);
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
            }else {
                readContacts();
            }
        });

        Button rAdd = findViewById(R.id.add_data);
        Button rUpdate = findViewById(R.id.update_data);
        Button rDelete = findViewById(R.id.delete_data);
        Button rQuery = findViewById(R.id.query_data);

        rAdd.setOnClickListener(v -> {
            Uri uri = Uri.parse("content://club.playerfox.my_data_provider.provider/table1");
            ContentValues values = new ContentValues();
            values.put("name","WeGame");
            values.put("info","Wxz");
            getContentResolver().insert(uri,values);
            showText.append("add successfully\n");
        });

        rUpdate.setOnClickListener(v -> {
            Uri uri = Uri.parse("content://club.playerfox.my_data_provider.provider/table1");
            ContentValues values = new ContentValues();
            values.put("info","xxz");
            getContentResolver().update(uri,values,null,null);
            showText.append("update successfully\n");
        });

        rDelete.setOnClickListener(v -> {
            Uri uri = Uri.parse("content://club.playerfox.my_data_provider.provider/table1");
            getContentResolver().delete(uri,null,null);
            showText.append("delete successfully\n");
        });

        rQuery.setOnClickListener(v -> {
            showText.setText(null);
            Uri uri = Uri.parse("content://club.playerfox.my_data_provider.provider/table1");
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String info = cursor.getString(cursor.getColumnIndex("info"));
                    String text = "id:" + id + " name:" + name + " info:" + info + "\n";
                    showText.append(text);
                }
                cursor.close();
            }
        });
    }

    private void readContacts() {
        Cursor cursor;
        cursor = getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                showText.append("name:" + name + ",number:" + number + "\n");
            }
            cursor.close();
        }
    }

    private void callPhone() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        }catch (SecurityException e){
            Log.e(TAG, "callPhone: "+ e.toString() );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //callPhone();
                    readContacts();
                }else {
                    Toast.makeText(this,"denied permission",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }
}
