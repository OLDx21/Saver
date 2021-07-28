package com.example.saver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.saver.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class renamesec extends AppCompatActivity {

    Button btngo;
    Button reg;
    String path;


    TextInputLayout sec1;
    TextInputLayout login1;
    TextInputLayout kod;

    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, MainActivity2.class);

        startActivity(i);
        this.finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent i = new Intent(this, MainActivity2.class);
                startActivity(i);

                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rename_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        sec1 = findViewById(R.id.sec12);
        login1 = findViewById(R.id.loginsec1);

        reg = findViewById(R.id.regstart);
        btngo = findViewById(R.id.gostart);
        kod = findViewById(R.id.kod1);
        kod.setVisibility(-1);
        btngo.setVisibility(-1);
        btngo.setClickable(false);

        kod.getEditText().setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        reg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (login1.getEditText().getText().toString().isEmpty() || sec1.getEditText().getText().toString().isEmpty() || sec1.getEditText().getText().toString().length() < 5) {

                    Toast.makeText(renamesec.this, "Не все поля заполнены или слишком маленький пароль!", Toast.LENGTH_LONG).show();
                    return;
                }
                info.setMail(login1.getEditText().getText().toString());
                info.setSec2(loadactiv.creatkode(sec1.getEditText().getText().toString()));

                reg.setClickable(false);
                sec1.setVisibility(-1);
                reg.setVisibility(-1);

                login1.setVisibility(-1);
                kod.setVisibility(1);

                btngo.setVisibility(1);
                btngo.setClickable(true);
                path = LoginActivity.randomparol();


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LoginActivity.sendmail(path, info.getMail());
                    }
                }).start();
                Toast.makeText(renamesec.this, "Вам на почту пришел код подтверждения!", Toast.LENGTH_LONG).show();


            }
        });
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (path.equals(kod.getEditText().getText().toString())) {

                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void run() {
                            write();
                        }
                    }).start();


                    Intent i = new Intent(renamesec.this, MainActivity2.class);
                    startActivity(i);

                } else {
                    Toast.makeText(renamesec.this, "Неверный код!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void write() {


        try {
            String text = info.getSec2() + "\n" + loadactiv.creatkode(info.getMail());

            FileOutputStream fileOutputStream = this.openFileOutput("file.txt", MODE_PRIVATE);

            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
