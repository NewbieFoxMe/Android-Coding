package club.playerfox.my_data_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

public class MyContentProvider extends ContentProvider {
    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    private static UriMatcher uriMatcher;
    private static final String AUTHORITY = "club.playerfox.my_data_provider.provider";
    private MyDatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"table1", TABLE1_DIR);
        uriMatcher.addURI(AUTHORITY,"table1/#", TABLE1_ITEM);
        uriMatcher.addURI(AUTHORITY,"table2", TABLE2_DIR);
        uriMatcher.addURI(AUTHORITY,"table2/#", TABLE1_ITEM);
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int row = 0;

        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                row = db.delete("table1",selection,selectionArgs);
                break;
            case TABLE1_ITEM:
                row = db.delete("table1","id=?"
                        ,new String[]{uri.getPathSegments().get(1)});
                break;
            case TABLE2_DIR:
                row = db.delete("table2",selection,selectionArgs);
                break;
            case TABLE2_ITEM:
                row = db.delete("table2","id=?"
                        ,new String[]{uri.getPathSegments().get(1)});
                break;
            default:
                break;
        }
        return row;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.club.playerfox.my_data_provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.club.playerfox.my_data_provider.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.club.playerfox.my_data_provider.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.club.playerfox.my_data_provider.table2";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        Log.e("insert:","x1x");
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
            case TABLE1_ITEM:
                long table1Id = db.insert("table1",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/table1/" + table1Id);
                break;
            case TABLE2_DIR:
            case TABLE2_ITEM:
                long table2Id = db.insert("table2",null,values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/table2/" + table2Id);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(),"Tables.db",null,1);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                cursor = db.query("table1",projection,selection,selectionArgs
                        ,null,null,sortOrder);
                break;
            case TABLE1_ITEM:
                cursor = db.query("table1",projection,"id=?"
                        ,new String[]{uri.getPathSegments().get(1)},null,null,sortOrder);
                break;
            case TABLE2_DIR:
                cursor = db.query("table2",projection,selection,selectionArgs
                        ,null,null,sortOrder);
                break;
            case TABLE2_ITEM:
                cursor = db.query("table2",projection,"id=?"
                        ,new String[]{uri.getPathSegments().get(1)},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int row = 0;
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                row = db.update("table1",values,selection,selectionArgs);
                break;
            case TABLE1_ITEM:
                row = db.update("table1",values,"id=?"
                        ,new String[]{uri.getPathSegments().get(1)});
                break;
            case TABLE2_DIR:
                row = db.update("table2",values,selection,selectionArgs);
                break;
            case TABLE2_ITEM:
                row = db.update("table2",values,"id=?"
                        ,new String[]{uri.getPathSegments().get(1)});
                break;
            default:
                break;
        }
        return row;
    }
}
