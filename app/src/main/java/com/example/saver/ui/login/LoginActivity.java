package com.example.saver.ui.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saver.*;
import com.google.android.material.textfield.TextInputLayout;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import static com.example.saver.dbhelp.*;
import static com.example.saver.dbhelp.SEC_COLUMN;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    Button btngo;
    Button reg;

    String path;

    TextInputLayout sec1;
    TextInputLayout login1;
    TextInputLayout kod;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);





       sec1 = findViewById(R.id.sec);
       login1 = findViewById(R.id.loginsec);

       reg = findViewById(R.id.login);
        btngo = findViewById(R.id.go);
        kod = findViewById(R.id.kod);
kod.setVisibility(-1);
        btngo.setVisibility(-1);
        btngo.setClickable(false);

kod.getEditText().setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }

                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login1.getEditText().getText().toString().isEmpty()||sec1.getEditText().getText().toString().isEmpty()||sec1.getEditText().getText().toString().length()<5){

                    Toast.makeText(LoginActivity.this, "???? ?????? ???????? ?????????????????? ?????? ?????????????? ?????????????????? ????????????!", Toast.LENGTH_LONG).show();
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
                 path = randomparol();

               // CompletableFuture.runAsync(()->sendmail(path, info.getMail()));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendmail(path, info.getMail());
                    }
                }).start();
                Toast.makeText(LoginActivity.this, "?????? ???? ?????????? ???????????? ?????? ??????????????????????????!", Toast.LENGTH_LONG).show();


            }
        });

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(path.equals(kod.getEditText().getText().toString())){

    new Thread(new Runnable() {
        @Override
        public void run() {
            write();
        }
    }).start();
    dbhelp dbhelper;
    SQLiteDatabase sqLiteDatabase;

    dbhelper = new dbhelp(LoginActivity.this);
    sqLiteDatabase = dbhelper.getWritableDatabase();




    Intent i = new Intent(LoginActivity.this, MainActivity2.class);
    startActivity(i);

}
else {
    Toast.makeText(LoginActivity.this, "???????????????? ??????!", Toast.LENGTH_LONG).show();
}

            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    public static void sendmail(String pas, String mli) {
        try {





            Properties properties = new Properties();

            try {
                properties.load(LoginActivity.class.getClassLoader().getResourceAsStream("assets/email.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Session mailsesion = Session.getDefaultInstance(properties);



            MimeMessage message = new MimeMessage(mailsesion);

            message.setFrom(new InternetAddress("*******@gmail.com"));
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(mli)));
            message.setSubject("WARNING");
            message.setText(pas);
            Transport tr = mailsesion.getTransport();
            tr.connect("********@gmail.com", "*********");

            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

        }  catch (MessagingException e) {
            e.printStackTrace();
       }




        }

    public static String randomparol(){
int ran = (int) (Math.random()*100000);
        String ok = String.valueOf(ran);
        return ok;
    }

    public void write(){


        try {
          String text = info.getSec2()+"\n"+loadactiv.creatkode(info.getMail());

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
