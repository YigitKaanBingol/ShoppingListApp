package com.example.shoppinglistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;


public class MainActivity extends AppCompatActivity {
    FloatingActionButton ekleme;
    RecyclerView recyclerView;
    ListelerAdapter listelerAdapter;
    public static final byte[] IEFRAME = new byte[] { (byte)0x80, 0x53, 0x1c,
            (byte)0x87, (byte)0xa0, 0x42, 0x69, 0x10, (byte)0xa2, (byte)0xea, 0x08,
            0x00, 0x2b, 0x30, 0x30, (byte)0x9d };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ekleme=findViewById(R.id.floatingActionButton);

        DatabaseHelper db=new DatabaseHelper(this);
        LocalDate localDate = LocalDate.of(2016, 8, 19);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        Alisveris alisveris=new Alisveris(1,"elmaci",date,4564.0,535.0,IEFRAME);
       // db.ListeyeEkle(alisveris);
        Urun urun=new Urun("elma",5,12.8F);
        //db.instertUrun(urun);




        DatabaseHelper dbhelper=new DatabaseHelper(this);
        try {
            ArrayList<Alisveris> listelerim=dbhelper.readData();
            recyclerView = (RecyclerView) findViewById(R.id.mainrecycler);


            listelerAdapter = new ListelerAdapter(this, listelerim);
            recyclerView.setAdapter(listelerAdapter) ;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*
        if(listelerim.size()==0){
            kontrol=findViewById(R.id.textView9);
            kontrol.setText("Daha önce bir soru eklemediniz.");
        }
*/


        ekleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                alert.setMessage("Yeni Liste");
                alert.setTitle("Lista Adı");

                alert.setView(edittext);

                alert.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Editable YouEditTextValue = edittext.getText();
                        Alisveris alisveris=new Alisveris(1,YouEditTextValue.toString(),date,4564.0,535.0,IEFRAME);
                        db.ListeyeEkle(alisveris);
                        listelerAdapter.notifyDataSetChanged();
                        Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        //OR
                        //String YouEditTextValue = edittext.getText().toString();
                    }
                });

                alert.setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();

                ekleme.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {




                       return true;
                    }
                });



            }
        });

       // this.deleteDatabase("ShoppingListApp");
    }
}