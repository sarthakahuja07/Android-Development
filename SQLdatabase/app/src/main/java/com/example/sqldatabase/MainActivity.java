package com.example.sqldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {


            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("Friends", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS newcolleges(name VARCHAR,age INT(3),id INTEGER PRIMARY KEY)");
            //sqLiteDatabase.execSQL("INSERT INTO newcolleges(name, age)VALUES ('Anmol',18)");
            //sqLiteDatabase.execSQL("INSERT INTO newcolleges(name, age) VALUES ('sid',19)");
            //sqLiteDatabase.execSQL("INSERT INTO newcolleges(name, age) VALUES ('dikky',18)");
            sqLiteDatabase.execSQL("DELETE FROM newcolleges WHERE id=3 ");
            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM newcolleges ",null);
            int nameIndex=c.getColumnIndex("name");
            int ageIndex=c.getColumnIndex("age");
            int idIndex=c.getColumnIndex("id");
            c.moveToFirst();
            while(!c.isAfterLast()){
                Log.i("name",c.getString(nameIndex));
                Log.i("age",c.getString(ageIndex));
                Log.i("id",c.getString(idIndex));
                c.moveToNext();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
