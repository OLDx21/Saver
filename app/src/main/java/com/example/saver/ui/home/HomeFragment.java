package com.example.saver.ui.home;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.saver.R;
import com.example.saver.dbhelp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.*;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    dbhelp dbhelper;
    TextInputLayout name1;
    TextInputLayout login1;

    TextInputLayout sec1;
    Button btn;
    SQLiteDatabase sqLiteDatabase;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        name1 = root.findViewById(R.id.namesec);
        login1 = root.findViewById(R.id.loginsec);
        sec1 = root.findViewById(R.id.sec);
        btn = root.findViewById(R.id.buttonstart);


        dbhelper = new dbhelp(getActivity());

        sqLiteDatabase = dbhelper.getWritableDatabase();


        final ContentValues contentValues = new ContentValues();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name1.getEditText().getText().toString().isEmpty() || login1.getEditText().getText().toString().isEmpty() || sec1.getEditText().getText().toString().isEmpty()) {

                    Toast.makeText(getContext(), "Не все поля заполнены!", Toast.LENGTH_LONG).show();

                    return;
                }


                contentValues.put(dbhelper.NAME_COLUMN, name1.getEditText().getText().toString());
                contentValues.put(dbhelper.MAIL_COLUMN, login1.getEditText().getText().toString());
                contentValues.put(dbhelper.SEC_COLUMN, sec1.getEditText().getText().toString());

                sqLiteDatabase.insert(dbhelper.TABLE_NAME, null, contentValues);
                Toast.makeText(getContext(), "Успешно сохранено!", Toast.LENGTH_LONG).show();

                sqLiteDatabase.close();
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }


}