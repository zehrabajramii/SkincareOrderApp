package com.mk.skincareorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Kjo klasë përfaqëson një ndihmës për menaxhimin e bazës së të dhënave SQLite për aplikacionin tuaj.
    // Ajo zgjerohet nga SQLiteOpenHelper për të menaxhuar krijimin dhe përditësimin e bazës së të dhënave.

    private static final String DATABASE_NAME = "SkincareOrders.db";
    // Emri i skedarit të bazës së të dhënave që do të krijohet në pajisjen e përdoruesit.

    private static final int DATABASE_VERSION = 1;
    // Versioni i bazës së të dhënave. Përditësimet në strukturën e bazës së të dhënave kërkojnë ndryshimin e këtij versioni.

    private static final String TABLE_ORDERS = "orders";
    // Emri i tabelës ku do të ruhen porositë.

    private static final String COLUMN_ID = "_id";
    // Emri i kolonës që përmban ID-në unike për çdo rresht në tabelën e porosive.

    private static final String COLUMN_ORDER_NAME = "order_name";
    // Emri i kolonës që përmban emrin e porosisë.

    private static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    // Emri i kolonës që përmban shumën totale të porosisë.

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Konstruktori për klasën DatabaseHelper që inicializon SQLiteOpenHelper me emrin e bazës së të dhënave dhe versionin.

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_NAME + " TEXT,"
                + COLUMN_TOTAL_AMOUNT + " REAL" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
        // Krijon tabelën 'orders' me kolonat për ID, emrin e porosisë dhe shumën totale. Kolona ID është çelësi kryesor dhe rritet automatikisht.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
        // Nëse struktura e tabelës ndryshohet, kjo metodë përdoret për të përmirësuar bazën e të dhënave.
        // Ajo fshin tabelën ekzistuese dhe e krijon atë përsëri, duke përfshirë çdo ndryshim të ri.
    }

    public long addOrder(String orderName, float totalAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_NAME, orderName);
        values.put(COLUMN_TOTAL_AMOUNT, totalAmount);
        // Përgatitja e të dhënave për të futur një rresht të ri në tabelën 'orders'.
        // Emri i porosisë dhe shuma totale futen në një objekt ContentValues.

        // Fut rreshtin e ri në tabelë dhe kthen ID-në e rreshtit të sapo futur.
        long result = db.insert(TABLE_ORDERS, null, values);
        db.close(); // Mbyll lidhjen me bazën e të dhënave.
        return result;
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Merr një lidhje të lexueshme me bazën e të dhënave.

        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
        // Kthen të gjitha rreshtat nga tabela 'orders' si një objekt Cursor për iterim.
    }
}
