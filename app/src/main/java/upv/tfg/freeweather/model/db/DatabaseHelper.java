package upv.tfg.freeweather.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorAdapter;

import java.util.ArrayList;
import java.util.List;

import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public class DatabaseHelper {

    private DBInitialization myhelper;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context){
        myhelper = new DBInitialization(context);
        db = myhelper.getWritableDatabase();
    }

    public void saveFavourite(String location) {
        Integer codPro;
        Integer codMun;
        String code;
        Integer codigo;

        String[] args = new String[] {location};
        Cursor c = db.rawQuery(" SELECT * FROM tblLocalidades WHERE nombre=? ", args);
        if(c.moveToFirst() && c.getCount() >= 1) {
            codPro = c.getInt(2);
            codMun = c.getInt(3);
            code = codPro.toString()+""+codMun.toString();
            codigo = Integer.parseInt(code);
            db.execSQL("INSERT INTO tblFavourites VALUES(" +location+ "," +codigo+ ")" );
        }
        c.close();
    }

    public void deleteFavourite(String location){
        db.delete("tblFavourites","nombre=?",new String[]{location});
    }

    public List<String> getAllFavourites() {
        List<String> list = null;

        Cursor cursor = db.rawQuery("select * from tblFavourites",null);
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(5);
                list.add(name);
                cursor.moveToNext();
            }
        }
        return  list;
    }

    public List<String> findPossibleLocation(String newText) {
        List<String> list = new ArrayList<>();

        String[] selectionArgs = new String[] { newText + "%" };

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
        return  list;

    }

    public String getCodeByLocation(String location) {
        Integer codPro;
        Integer codMun;
        String code = null;

        String[] args = new String[] {location};
        Cursor c = db.rawQuery(" SELECT * FROM tblLocalidades WHERE nombre=? ", args);
        if(c.moveToFirst() && c.getCount() >= 1) {
            codPro = c.getInt(2);
            codMun = c.getInt(3);
            code = complete2Digits(codPro)+""+complete3Digits(codMun);
        }
        c.close();

        return code;
    }

    public void onAttachPresenter(I_HomePresenter presenter) {

    }

    public void onDetachPresenter() {

    }

    //UTILS METHODS
    public String complete2Digits(Integer code){
        String res = code.toString();
        if(code.toString().length() < 2){
            if(code.toString().length() == 1) {
                res = "0" + code.toString();
            }
        }
        return res;
    }
    public String complete3Digits(Integer code){
        String res = code.toString();
        if(code.toString().length() < 3){
            if(code.toString().length() == 2){
                res = "0" + code.toString();
            }else if(res.toString().length() == 1){
                res = "00" + code.toString();
            }
        }
        return res;
    }
}
