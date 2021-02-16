package com.example.saver;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.saver.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.*;

import static com.example.saver.dbhelp.*;

public class checkedclass extends AppCompatActivity {
    int pipitki;
    TextInputLayout sec1;
    Button start;
    String path;
    String mai;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checked_layout);
        sec1 = findViewById(R.id.sec);
        start = findViewById(R.id.buttonstart);
        read();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sec1.getEditText().getText().toString().equals(addsec.readcode(path))){
                    Intent i = new Intent(checkedclass.this, MainActivity2.class);
                    startActivity(i);
                }
                else {
                    pipitki++;
if (pipitki!=4){
    Toast.makeText(checkedclass.this, "Неверный пароль!", Toast.LENGTH_LONG).show();
}

                    if(pipitki==4){


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                path = LoginActivity.randomparol();
                                LoginActivity.sendmail(path,addsec.readcode(mai) );
                                path = addsec.creatkode(path);
                            }
                        }).start();
                        Toast.makeText(checkedclass.this, "Вам на почту пришел новый временный пароль!", Toast.LENGTH_LONG).show();
                    }
                    if (pipitki==8){

                      dbhelp  dbhelper = new dbhelp(checkedclass.this);

                       SQLiteDatabase sqLiteDatabase =  dbhelper.getWritableDatabase();

                        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
                        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + NAME_COLUMN + " text," + MAIL_COLUMN + " text,"+SEC_COLUMN+" text"+")");
                        write();
                        System.exit(1);


                    }


                }
            }
        });
    }
    public  void read (){
        String output = "";
        try {
            FileInputStream fileinput = this.openFileInput("file.txt");
            InputStreamReader reader = new InputStreamReader(fileinput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            int i=0;
            while((str = buffer.readLine())!=null){
               if(i==0){
                   path = str;
               }else {
                   mai = str;
               }




i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();

        }


    }
    public void write(){


        try {
            String text ="";

            FileOutputStream fileOutputStream = this.openFileOutput("file.txt", MODE_PRIVATE);

            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }




    }
}
