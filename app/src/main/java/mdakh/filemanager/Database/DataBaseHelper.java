package mdakh.filemanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mdakh on 11/24/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="TypeOfMyFile";
    public static final String TABLE_NAME="Types";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_FileExeption="FileExeption";
    public static final String COLUMN_FileType="FileType";
    public static final String COLUMN_CommendeType="CommendeType";
    public static final String COLUMN_Link="Link";
    public static final String COLUMN_Papularity="Papularity";

    public static final int DB_VERSION=1;

    public DataBaseHelper (Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TABLE_NAME+" ("+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_FileExeption+" VARCHAR, "+
                COLUMN_FileType+" VARCHAR, "+
                COLUMN_CommendeType+" VARCHAR, "+
                COLUMN_Link+" VARCHAR, "+
                COLUMN_Papularity+" INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean adduser(String COLUMN_FileExeption,String COLUMN_FileType,String COLUMN_CommendeType,String COLUMN_Link,int COLUMN_Papularity){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(this.COLUMN_FileExeption,COLUMN_FileExeption);
        contentValues.put(this.COLUMN_FileType,COLUMN_FileType);
        contentValues.put(this.COLUMN_CommendeType,COLUMN_CommendeType);
        contentValues.put(this.COLUMN_Link,COLUMN_Link);
        contentValues.put(this.COLUMN_Papularity,COLUMN_Papularity);

        return sqLiteDatabase.insert(TABLE_NAME,null,contentValues)!=-1;

    }

    public Cursor getuser(){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String sql="select * from "+TABLE_NAME+";";
        return sqLiteDatabase.rawQuery(sql,null);
    }

}
