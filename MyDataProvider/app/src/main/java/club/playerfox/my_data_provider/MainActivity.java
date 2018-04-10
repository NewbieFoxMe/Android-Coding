package club.playerfox.my_data_provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"Tables.db",null,1);
        Button create = findViewById(R.id.create);
        Button show = findViewById(R.id.show);
        Button add = findViewById(R.id.addx);
        TextView data = findViewById(R.id.data);

        create.setOnClickListener(v -> {
            dbHelper.getWritableDatabase();
            String str = "create successful";
            data.setText(str);
        });

        show.setOnClickListener(v -> {
            data.setText(null);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("table1",null,null,null,null,null,null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String info = cursor.getString(cursor.getColumnIndex("info"));
                    String text = "id:" + id + " name:" + name + " info:" + info + "\n";
                    data.append(text);
                }
                cursor.close();
            }
        });

        add.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name","test");
            values.put("info","test");
            db.insert("table1",null,values);
        });

    }
}
