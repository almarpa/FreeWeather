package upv.tfg.freeweather.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private DBCreation myhelper;
    private SQLiteDatabase db;
    private AssetManager manager;

    public DatabaseHelper(Context context) {
        myhelper = new DBCreation(context);
        db = myhelper.getWritableDatabase();
        manager = context.getAssets();
    }

    /**
     *  Fill the database with all the locations from the .csv file.
     */
    public void putLocationsInDB() {
        String mCSVfile = "codmunicip_v1.csv";

        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream, "ISO-8859-1"));
            String line;

            db = myhelper.getWritableDatabase();
            db.beginTransaction();

            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");

                ContentValues cv = new ContentValues();
                cv.put("codAuto", colums[0].trim());
                cv.put("cPro", colums[1].trim());
                cv.put("cMun", colums[2].trim());
                cv.put("DC", colums[3].trim());
                cv.put("nombre", colums[4].trim());
                db.insert("tblLocalidades", null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * Find possibles locations that matches with the text introduced
     * @param newText text introduced by the application user
     * @return list of possible locations
     */
    public List<String> findPossibleLocation(String newText) {
        List<String> list = new ArrayList<>();

        String[] selectionArgs = new String[]{newText + "%"};

        Cursor cursor = db.rawQuery("SELECT * FROM tblLocalidades WHERE nombre like ?", selectionArgs);
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            int cont = 0;
            while (!cursor.isAfterLast() && cont != 3) {
                String name = cursor.getString(5);
                list.add(name);
                cursor.moveToNext();
                cont++;
            }
        }
        return list;

    }

    /**
     * Obtain the search code equivalent to a location
     * @param location the location we want to obtain predictions
     * @return the location's code
     */
    public String getCodeByLocation(String location) {
        Integer codPro;
        Integer codMun;
        String code = null;

        String[] args = new String[]{location};
        Cursor c = db.rawQuery(" SELECT * FROM tblLocalidades WHERE nombre=? ", args);
        if (c.moveToFirst() && c.getCount() >= 1) {
            codPro = c.getInt(2);
            codMun = c.getInt(3);
            code = complete2Digits(codPro) + "" + complete3Digits(codMun);
        }
        c.close();

        return code;
    }

    // UTILS METHODS
    public String complete2Digits(Integer code) {
        String res = code.toString();
        if (code.toString().length() < 2) {
            if (code.toString().length() == 1) {
                res = "0" + code.toString();
            }
        }
        return res;
    }
    public String complete3Digits(Integer code) {
        String res = code.toString();
        if (code.toString().length() < 3) {
            if (code.toString().length() == 2) {
                res = "0" + code.toString();
            } else if (res.toString().length() == 1) {
                res = "00" + code.toString();
            }
        }
        return res;
    }

}
