package club.playerfox.my_data_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String createTable1 =
            "create table table1(" +
            "id integer primary key autoincrement," +
            "name text," +
            "info text)";
    private static final String createTable2 =
            "create table table2(" +
            "id integer primary key autoincrement," +
            "name text," +
            "info text)";
    private static final String dropTable1 = "drop table if exists table1";
    private static final String dropTable2 = "drop table if exists table2";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        Toast.makeText(mContext,"Create Database",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable1);
        db.execSQL(dropTable2);
        onCreate(db);
    }
}
