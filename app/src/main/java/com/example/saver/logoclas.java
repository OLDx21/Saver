package com.example.saver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.saver.ui.login.LoginActivity;

import java.io.*;

public class logoclas extends AppCompatActivity {
    private Animation logoanim;
    private ImageView imagelogo;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        setContentView(R.layout.logo_lay);
        logoanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logoanim);
        imagelogo = findViewById(R.id.imagelogo);
        imagelogo.startAnimation(logoanim);
        startmainact();

    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (prefs.getBoolean("firstrun", true)) {
//            Intent i = new Intent(logoclas.this, LoginActivity.class);
//            startActivity(i);
//            prefs.edit().putBoolean("firstrun", false).commit();
//        }
//        else {
//            Intent i = new Intent(logoclas.this, LoginActivity.class);
//            startActivity(i);
//        }
//    }
    private void startmainact() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String file = read();

                if (file.length() == 0) {
                    file = null;
                    Intent i = new Intent(logoclas.this, LoginActivity.class);
                    startActivity(i);

                } else {
                    file = null;
                    Intent i = new Intent(logoclas.this, checkedclass.class);
                    startActivity(i);
                }
            }
        }).start();


    }

    public String read() {
        String output = "";
        try {
            FileInputStream fileinput = this.openFileInput("file.txt");
            InputStreamReader reader = new InputStreamReader(fileinput);
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while ((str = buffer.readLine()) != null) {

                stringBuffer.append(str + "\n");


            }
            output = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return output;
    }
}
