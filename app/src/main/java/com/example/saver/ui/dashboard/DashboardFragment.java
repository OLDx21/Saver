package com.example.saver.ui.dashboard;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.saver.*;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    SQLiteDatabase sqLiteDatabase;
     ContentValues contentValues;
   dbhelp dbhelper;
    private DashboardViewModel dashboardViewModel;
ListView listView;
info info1 = new info();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listView = root.findViewById(R.id.list);


        dbhelper = new dbhelp(getActivity());
        sqLiteDatabase = dbhelper.getWritableDatabase();
         contentValues = new ContentValues();
String name;
        ArrayList ar1 = new ArrayList();
int i =0;
        Cursor cursor = sqLiteDatabase.query(dbhelper.TABLE_NAME, null, null,null,null,null,null);
if (cursor.moveToFirst()){
    int nameind = cursor.getColumnIndex(dbhelper.NAME_COLUMN);

    do{

        name = cursor.getString(nameind);

        ar1.add(name);
i++;
    }
    while (cursor.moveToNext());





}
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, ar1);

        listView.setAdapter(adapter);





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getActivity(), MainActivity2.class);
                String idd = (String) listView.getItemAtPosition(position);
                getsec(sqLiteDatabase, idd);
                Fragment fragment = new DashboardFragment();
                fragment.onStop();
               startActivity(i);
            }
        });


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    public void getsec(SQLiteDatabase sqLiteDatabase, String text){
String[] text1 = {text};
        String name;
        String mail;
        String dec;
        Cursor cursor = sqLiteDatabase.query(dbhelp.TABLE_NAME,
                new String[] {dbhelp.NAME_COLUMN, dbhelp.MAIL_COLUMN, dbhelp.SEC_COLUMN},
                "name = ?",
                text1,
                null, null, null);

        if (cursor.moveToFirst()){

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