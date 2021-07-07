package com.example.datanilaimahasiswa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE mahasiswa(nirm text, namamhs text, matakuliah text, nilai text)";
        db.execSQL(sql);
        sql = "INSERT INTO mahasiswa (nirm, namamhs, matakuliah, nilai) VALUES('2019030088', 'M FAHRI PRAMUDITYA', 'PEMPROGRAMAN MOBILE', '80');";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
