package com.example.datanilaimahasiswa;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DataHelper dataHelper;
    Cursor cursor;
    String[] daftarmahasiswa;
    ListView listView;
    Button tambahdata;
    public ActivityResultLauncher<Intent> intentActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambahdata = (Button) findViewById(R.id.tambahdata);

        dataHelper = new DataHelper(MainActivity.this, "nilai", null, 1);
        tampilmahasiswa();

        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahData.class);
                intentActivityResultLauncher.launch(intent);
            }
        });
    }

    public void tampilmahasiswa(){
        SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM mahasiswa", null);
        daftarmahasiswa = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            daftarmahasiswa[i] = cursor.getString(1).toString();
        }

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, daftarmahasiswa));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition((int) id);
                Log.d("HASILNYA APA", cursor.getString(0));
                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setMessage("PILIHAN")
                        .setPositiveButton("Ubah data", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, UbahData.class);
                                intent.putExtra("nirm", cursor.getString(0));
                                intentActivityResultLauncher.launch(intent);
                            }
                        })
                        .setNegativeButton("Hapus data", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();
                                String sql = "DELETE FROM mahasiswa WHERE nirm = '" + cursor.getString(0) + "';";
                                sqLiteDatabase.execSQL(sql);
                                finish();
                                startActivity(getIntent());

                            }
                        })
                        .show();

            }
        });
    }
}