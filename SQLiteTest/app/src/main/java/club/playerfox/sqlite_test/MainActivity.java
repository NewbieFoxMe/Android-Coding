package club.playerfox.sqlite_test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyDatabaseHelper helper;
    private TextView queryRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        Button create = findViewById(R.id.create);
        create.setOnClickListener(v -> {
            helper.getWritableDatabase();
        });

        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            addData();
        });

        Button update = findViewById(R.id.update);
        update.setOnClickListener(v -> {
            updateData();
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            deleteData();
        });
        queryRes = findViewById(R.id.query_res);
        Button query = findViewById(R.id.query);
        query.setOnClickListener(v -> {
            queryData();
        });


    }

    private void queryData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        queryRes.setText(null);
        Cursor cursor = db.query("Book",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                String show = "name:" + name + ",author:" + author + ",pages:" + pages + "\n";
                Log.e(TAG, "queryData: "+ show );
                queryRes.append(show);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void deleteData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("Book","pages > ?",new String[]{"500"});
        Toast.makeText(this,"Delete value",Toast.LENGTH_SHORT).show();
    }

    private void updateData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pages",600);
        db.update("Book",values,"name=?",new String[]{"ShaBiLiShenYang"});
        Toast.makeText(this,"Update value",Toast.LENGTH_SHORT).show();
    }

    private void addData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name","ShaBiLiShenYang");
        values.put("author","LiShenYang");
        values.put("pages",456);
        db.insert("Book",null,values);
        Toast.makeText(this,"Add value",Toast.LENGTH_SHORT).show();
    }
}
