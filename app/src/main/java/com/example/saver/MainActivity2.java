package com.example.saver;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import com.example.saver.ui.dashboard.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    dbhelp dbhelper;
    private AppBarConfiguration mAppBarConfiguration;
    ListView listView;
    info info1 = new info();

    @Override
    public void onBackPressed() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), addsec.class);
                startActivity(intent);

            }
        });


        listView = findViewById(R.id.list);


        dbhelper = new dbhelp(MainActivity2.this);
        sqLiteDatabase = dbhelper.getWritableDatabase();
        contentValues = new ContentValues();
        String name;
        ArrayList ar1 = new ArrayList();
        int i = 0;
        Cursor cursor = sqLiteDatabase.query(dbhelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameind = cursor.getColumnIndex(dbhelper.NAME_COLUMN);

            do {

                name = cursor.getString(nameind);

                ar1.add(name);
                i++;
            }
            while (cursor.moveToNext());


        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ar1);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getBaseContext(), loadactiv.class);
                        String idd = (String) listView.getItemAtPosition(position);
                        getsec(sqLiteDatabase, idd);

                        startActivity(i);
                    }
                }).start();

            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                Intent intent = new Intent(this, renamesec.class);
                startActivity(intent);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }


    public void getsec(SQLiteDatabase sqLiteDatabase, String text) {
        String[] text1 = {text};
        String name;
        String mail;
        String dec;
        Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME,
                new String[]{dbhelp.NAME_COLUMN, dbhelp.MAIL_COLUMN, dbhelp.SEC_COLUMN},
                "name = ?",
                text1,
                null, null, null);

        if (cursor.moveToFirst()) {

            int nameind = cursor.getColumnIndex(dbhelp.NAME_COLUMN);
            int loginid = cursor.getColumnIndex(dbhelp.MAIL_COLUMN);
            int secid = cursor.getColumnIndex(dbhelp.SEC_COLUMN);


            name = cursor.getString(nameind);

            mail = cursor.getString(loginid);
            dec = cursor.getString(secid);

            info1.setNamee(name);
            info1.setLogin(mail);
            info1.setSec(dec);


        }
        cursor.close();
    }

}