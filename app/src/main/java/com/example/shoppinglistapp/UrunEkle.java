package com.example.shoppinglistapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.example.shoppinglistapp.MainActivity.IEFRAME;

public class UrunEkle extends AppCompatActivity {
    Alisveris listem;
    EditText UrunAdi;
    EditText UrunAdedi;
    EditText UrunFiyati;
    TextView ListeAdi;
    Button ekle;
    RecyclerView recyclerView;
    UrunlerAdapter urunlerAdapter;
    ImageButton fotograf_cek,btnHome,btnTarih,btnMap;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urunekle);
        ListeAdi=findViewById(R.id.ListeAdi);
        UrunAdedi=findViewById(R.id.UrunAdedi);
        UrunAdi=findViewById(R.id.UrunAdi);
        UrunFiyati=findViewById(R.id.UrunFiyati);
        UrunFiyati=findViewById(R.id.UrunFiyati);
        ekle=findViewById(R.id.button3);
        fotograf_cek=findViewById(R.id.imageButton);
        btnHome=findViewById(R.id.imageButton3);
        btnTarih=findViewById(R.id.imageButton4);
        btnMap=findViewById(R.id.imageButton2);

       /*
        DatabaseHelper db=new DatabaseHelper(this);
        LocalDate localDate = LocalDate.of(2016, 8, 19);
        LocalDate defaultZoneId = ZoneId.systemDefault();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        Alisveris alisveris=new Alisveris(1,date,4564.0,535.0,IEFRAME);
        db.ListeyeEkle(alisveris);
        Urun urun=new Urun("elma",5,12.8F);
        db.instertUrun(urun);
*/
        Intent intent=getIntent();
        if(intent!=null) {
            listem = (Alisveris) getIntent().getSerializableExtra("listeadi");
        }


        ListeAdi.setText(listem.getListeAdi());


        DatabaseHelper dbhelper=new DatabaseHelper(this);
        try {
            ArrayList<Urun> urunlerim=dbhelper.readUrun(listem.getAlisverisId());
             recyclerView = (RecyclerView) findViewById(R.id.ListView2);


             urunlerAdapter = new UrunlerAdapter(this, urunlerim);
            recyclerView.setAdapter(urunlerAdapter) ;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Urun urun=new Urun(UrunAdi.getText().toString(),Integer.parseInt(UrunAdedi.getText().toString()),Integer.parseInt(UrunFiyati.getText().toString()));

                DatabaseHelper db=new DatabaseHelper(UrunEkle.this);
                db.instertUrun(urun,listem.getAlisverisId());
                Intent intent =new Intent(UrunEkle.this,UrunEkle.class);
                intent.putExtra("listeadi",listem);
                startActivity(intent);



            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UrunEkle.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        fotograf_cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(UrunEkle.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        UrunEkle.this,
                        android.R.style.Theme_Black_NoTitleBar_Fullscreen,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                dbhelper.setDate(listem.getAlisverisId(),date);

            }
        };


        }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){


            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] byteArray = stream.toByteArray();
            DatabaseHelper db=new DatabaseHelper(UrunEkle.this);
            db.setPhoto(listem.getAlisverisId(),byteArray);

            bitmap.recycle();

    }
}


}