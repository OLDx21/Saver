package com.example.saver;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class addsec extends AppCompatActivity {
    dbhelp dbhelper;
    TextInputLayout name1;
    TextInputLayout login1;

    TextInputLayout sec1;
    Button btn;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, MainActivity2.class);
        sqLiteDatabase.close();
        startActivity(i);
        this.finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent i = new Intent(this, MainActivity2.class);
                startActivity(i);
                sqLiteDatabase.close();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        name1 = findViewById(R.id.namesec);
        login1 = findViewById(R.id.loginsec);
        sec1 = findViewById(R.id.sec);
        btn = findViewById(R.id.buttonstart);


        dbhelper = new dbhelp(this);

        sqLiteDatabase = dbhelper.getWritableDatabase();


        final ContentValues contentValues = new ContentValues();
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (name1.getEditText().getText().toString().isEmpty() || login1.getEditText().getText().toString().isEmpty() || sec1.getEditText().getText().toString().isEmpty()) {

                    Toast.makeText(getBaseContext(), "Не все поля заполнены!", Toast.LENGTH_LONG).show();

                    return;
                }
                if (check(sqLiteDatabase, name1.getEditText().getText().toString()) == 1) {

                    Toast.makeText(getBaseContext(), "Пароль с таким навзанием уже существет!", Toast.LENGTH_LONG).show();
                    return;
                }


                contentValues.put(dbhelper.NAME_COLUMN, name1.getEditText().getText().toString());
                contentValues.put(dbhelper.MAIL_COLUMN, login1.getEditText().getText().toString());
                contentValues.put(dbhelper.SEC_COLUMN, loadactiv.creatkode(sec1.getEditText().getText().toString()));

                sqLiteDatabase.insert(dbhelper.TABLE_NAME, null, contentValues);
                Toast.makeText(getBaseContext(), "Успешно сохранено!", Toast.LENGTH_LONG).show();


            }
        });

    }

    public int check(SQLiteDatabase sqLiteDatabase, String text) {
        String[] text1 = {text};
        int i = 0;
        Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME,
                new String[]{dbhelp.NAME_COLUMN, dbhelp.MAIL_COLUMN, dbhelp.SEC_COLUMN},
                "name = ?",
                text1,
                null, null, null);

        if (cursor.moveToFirst()) {

            i++;


        }
        cursor.close();
        return i;
    }

}
