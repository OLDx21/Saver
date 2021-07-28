package com.example.saver;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.saver.ui.dashboard.DashboardFragment;
import com.google.android.material.textfield.TextInputLayout;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class loadactiv extends AppCompatActivity {
    TextInputLayout name;
    TextInputLayout login;
    TextInputLayout sec;
Button rename;
Button delete;
    dbhelp dbhelper ;
    SQLiteDatabase  sqLiteDatabase;
    ContentValues   contentValues ;
    ListView listView;
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
                sqLiteDatabase.close();
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadactiv);
        name = findViewById(R.id.nametext);
        login = findViewById(R.id.logintext);
        sec = findViewById(R.id.sectext);
        rename = findViewById(R.id.repat);
        delete= findViewById(R.id.delete);
        listView = findViewById(R.id.list);

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                String pas = readcode(info.getSec());


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sec.getEditText().setText(pas);

                    }
                });

            }
        }).start();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        name.getEditText().setText(info.getNamee());
        login.getEditText().setText(info.getLogin());







        dbhelper = new dbhelp(this);
        sqLiteDatabase = dbhelper.getWritableDatabase();
        contentValues = new ContentValues();
        rename.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(check(sqLiteDatabase,name.getEditText().getText().toString() )==1){
                    Toast.makeText(getBaseContext(), "Пароль с таким навзанием уже существет!", Toast.LENGTH_LONG).show();
                    return;

                }

               contentValues.put(dbhelp.NAME_COLUMN, name.getEditText().getText().toString());
                contentValues.put(dbhelp.MAIL_COLUMN, login.getEditText().getText().toString());
                contentValues.put(dbhelp.SEC_COLUMN, loadactiv.creatkode(sec.getEditText().getText().toString()));




                sqLiteDatabase.update(dbhelp.TABLE_NAME, contentValues, "name = ?", new String[]{info.getNamee()});



                Toast.makeText(getBaseContext(), "Успешно обновлено!", Toast.LENGTH_LONG).show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                sqLiteDatabase.delete(dbhelp.TABLE_NAME, "name = ?", new String[]{info.getNamee()});



                Toast.makeText(getBaseContext(), "Пароль успешно удален!", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getBaseContext(), MainActivity2.class);
                startActivity(i);

            }
        });
    }

    public int  check(SQLiteDatabase sqLiteDatabase, String text){
        String[] text1 = {text};

        int i = 0;
        Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME,
                new String[] {dbhelp.NAME_COLUMN, dbhelp.MAIL_COLUMN, dbhelp.SEC_COLUMN},
                "name = ?",
                text1,
                null, null, null);

        if (cursor.moveToFirst()){

            i++;




        }
        cursor.close();
        return i;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String readcode(String pass){
        String password = Passwords.getInsatnce().getAPas();
        if (password == null) {
            throw new IllegalArgumentException("Run with -Dpassword=<password>");
        }


        byte[] salt = new String(Passwords.getInsatnce().getASalt()).getBytes();


        int iterationCount = Passwords.getInsatnce().getACount();

        int keyLength = Passwords.getInsatnce().getALength();
        SecretKeySpec key = null;
        try {
            key = createSecretKey(password.toCharArray(),
                    salt, iterationCount, keyLength);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        String encryptedPassword = null;
        try {

            encryptedPassword = decrypt(pass, key);
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        catch (IOException e){

        }

        return  encryptedPassword;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String creatkode(String pass){
        String password = Passwords.getInsatnce().getAPas();
        if (password == null) {
            throw new IllegalArgumentException("Run with -Dpassword=<password>");
        }


        byte[] salt = new String(Passwords.getInsatnce().getASalt()).getBytes();


        int iterationCount = Passwords.getInsatnce().getACount();

        int keyLength = Passwords.getInsatnce().getALength();
        SecretKeySpec key = null;
        try {
            key = createSecretKey(password.toCharArray(),
                    salt, iterationCount, keyLength);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }



        String encryptedPassword = null;
        try {

            encryptedPassword = encrypt(pass, key);
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return  encryptedPassword;

    }

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = null;
        try {
            keyTmp = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String encrypt(String property, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
}
