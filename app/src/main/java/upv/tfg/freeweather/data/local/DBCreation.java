package upv.tfg.freeweather.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Create a the unique table of the database with all the localities.
 */
public class DBCreation extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DBCreation(Context context) {
        super(context,"myDataBase.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tblLocalidades ("
                + " id integer PRIMARY KEY autoincrement, "
                + " codAuto integer not null, "
                + " cPro integer not null, "
                + " cMun integer not null, "
                + " DC integer not null, "
                + " nombre text not null); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
