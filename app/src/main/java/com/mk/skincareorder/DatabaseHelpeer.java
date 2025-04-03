package com.mk.skincareorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelpeer extends SQLiteOpenHelper {
    // Kjo klasë përfaqëson një ndihmës për menaxhimin e bazës së të dhënave SQLite për përdoruesit e aplikacionit.

    private static final String DATABASE_NAME = "users.db";
    // Emri i skedarit të bazës së të dhënave që do të krijohet në pajisjen e përdoruesit.

    private static final String TABLE_NAME = "user_table";
    // Emri i tabelës ku do të ruhen të dhënat e përdoruesve.

    private static final String COL_1 = "ID";
    // Emri i kolonës që përmban ID-në unike për çdo përdorues në tabelën e përdoruesve.

    private static final String COL_2 = "USERNAME";
    // Emri i kolonës që përmban emrin e përdoruesit.

    private static final String COL_3 = "PASSWORD";
    // Emri i kolonës që përmban fjalëkalimin e përdoruesit.

    public DatabaseHelpeer(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    // Konstruktori për klasën DatabaseHelpeer që inicializon SQLiteOpenHelper me emrin e bazës së të dhënave dhe versionin.

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
        // Krijon tabelën 'user_table' me kolonat për ID, emrin e përdoruesit dhe fjalëkalimin.
        // Kolona ID është çelësi kryesor dhe rritet automatikisht.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        // Nëse struktura e tabelës ndryshohet, kjo metodë përdoret për të përmirësuar bazën e të dhënave.
        // Ajo fshin tabelën ekzistuese dhe e krijon atë përsëri, duke përfshirë çdo ndryshim të ri.
    }

    // Metodë për të shtuar një përdorues të ri në bazën e të dhënave.
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;  // Kthen true nëse futja është e suksesshme.
    }

    // Metodë për të kontrolluar nëse një emër përdoruesi ekziston tashmë në bazën e të dhënave.
    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;  // Kthen true nëse emri i përdoruesit ekziston.
    }

    // Metodë për të verifikuar kredencialet e hyrjes së përdoruesit.
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME = ? AND PASSWORD = ?", new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;  // Kthen true nëse emri i përdoruesit dhe fjalëkalimi janë të sakta.
    }

    // Metodë për të marrë të dhënat e një përdoruesi të caktuar.
    public Cursor getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME = ?", new String[]{username});
        // Kthen një Cursor që përmban të dhënat e përdoruesit të kërkuar.
    }

    // Metodë për të përditësuar fjalëkalimin e një përdoruesi (ose çdo fushë tjetër nëse është e nevojshme).
    public boolean updateUserPassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, newPassword);
        int result = db.update(TABLE_NAME, contentValues, "USERNAME = ?", new String[]{username});
        return result > 0;  // Kthen true nëse përditësimi është i suksesshëm.
    }

    // Metodë për të fshirë një llogari përdoruesi.
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "USERNAME = ?", new String[]{username});
        return result > 0;  // Kthen true nëse fshirja është e suksesshme.
    }
}
