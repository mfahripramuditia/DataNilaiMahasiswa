package com.example.datanilaimahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UbahData extends AppCompatActivity {

    DataHelper dataHelper;
    EditText nirm, namamhs, matakuliah, nilai;
    Button ubahdata;
    String intent_nirm;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data);

        Intent intent = getIntent();
        intent_nirm = intent.getStringExtra("nirm");

        nirm = (EditText) findViewById(R.id.nirm);
        namamhs = (EditText) findViewById(R.id.namamhs);
        matakuliah = (EditText) findViewById(R.id.matakuliah);
        nilai = (EditText) findViewById(R.id.nilai);
        ubahdata = (Button) findViewById(R.id.ubahdata);

        dataHelper = new DataHelper(UbahData.this, "nilai", null, 1);
        SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM mahasiswa WHERE nirm = '" + intent_nirm + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            cursor.moveToPosition(0);
            nirm.setText(cursor.getString(0));
            namamhs.setText(cursor.getString(1));
            matakuliah.setText(cursor.getString(2));
            nilai.setText(cursor.getString(3));
        }

        ubahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();
                String s_namamhs = namamhs.getText().toString();
                String s_matakuliah = matakuliah.getText().toString();
                String s_nilai = nilai.getText().toString();
                String sql = "UPDATE mahasiswa SET namamhs = '" + s_namamhs + "', matakuliah = '" + s_matakuliah +
                        "', nilai = '" + s_nilai +"' WHERE nirm = '" + intent_nirm + "';";

                sqLiteDatabase.execSQL(sql);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}