package com.example.datanilaimahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TambahData extends AppCompatActivity {

    DataHelper dataHelper;
    EditText nirm, namamhs, matakuliah, nilai;
    Button tambahdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        nirm = (EditText) findViewById(R.id.nirm);
        namamhs = (EditText) findViewById(R.id.namamhs);
        matakuliah = (EditText) findViewById(R.id.matakuliah);
        nilai = (EditText) findViewById(R.id.nilai);
        tambahdata = (Button) findViewById(R.id.tambahdata);

        tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHelper = new DataHelper(TambahData.this, "nilai", null, 1);
                SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();
                String s_nirm = nirm.getText().toString();
                String s_namamhs = namamhs.getText().toString();
                String s_matakuliah = matakuliah.getText().toString();
                String s_nilai = nilai.getText().toString();
                String sql = "INSERT INTO mahasiswa (nirm, namamhs, matakuliah, nilai) VALUES('" +s_nirm + "', '" + s_namamhs + "', '" + s_matakuliah +
                        "', '" + s_nilai +"')";
                sqLiteDatabase.execSQL(sql);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}