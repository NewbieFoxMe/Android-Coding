package club.playerfox.sqlite_test;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static final String sql = "create table Book (" +
            "id integer primary key autoincrement," +
            "author text," +
            "pages integer," +
            "name text)";

    private static final String sql2 = "create table Category (" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        db.execSQL(sql2);
        Toast.makeText(mContext,"create successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
