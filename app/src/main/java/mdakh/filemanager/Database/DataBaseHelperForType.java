package mdakh.filemanager.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import mdakh.filemanager.models.ListFormat;

/**
 * Created by mdakh on 12/6/2017.
 */

public class DataBaseHelperForType extends SQLiteOpenHelper {

    public static final String DB_NAME= "typefile.sqlite";
    public static String DB_LOCATION;
    public static final String TABLE_NAME="Types";
    private Context context;
    private SQLiteDatabase Database;
    public static final int DB_VERSION=1;

    public DataBaseHelperForType (Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context=context;
        DB_LOCATION=context.getFilesDir().getPath()+"/";
        //copy database to device
        File database = new File(DB_LOCATION+DB_NAME);
        if (false==database.exists()){
            CopyDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public Cursor getuser(){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String sql="select * from "+TABLE_NAME+";";
        return sqLiteDatabase.rawQuery(sql,null);
    }

    public void OpenDatabase(){
        String dbpath = context.getDatabasePath(DB_NAME).getPath();
        if(Database != null && Database.isOpen()){
            return;
        }
        Database = SQLiteDatabase.openDatabase(DB_LOCATION+DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void CloseDatabase(){
        if(Database!=null){
            Database.close();
        }
    }

    public ListFormat getList(String FileExeption){
        ListFormat listFormat;
        OpenDatabase();
        Cursor cursor=Database.rawQuery("SELECT * FROM Types WHERE FileExeption like \""+FileExeption+"\"",null);
        cursor.moveToFirst();
        listFormat=new ListFormat(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5));
        cursor.close();
        CloseDatabase();
        return listFormat;
    }

    private boolean CopyDatabase(){
        try {
            InputStream inputStream = this.context.getAssets().open(DataBaseHelperForType.DB_NAME);
            String outFileName = DataBaseHelperForType.DB_LOCATION+DataBaseHelperForType.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff=new byte[2048];
            int lenght = 0;
            while ((lenght = inputStream.read(buff)) > 0){
                outputStream.write(buff, 0, lenght);
            }
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this.context,"DB copies",Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.context,"ff",Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}
