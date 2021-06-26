package com.example.shoppinglistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;


public class DialogMaker extends Fragment{
    Button hatirlat;
    Button delete,tamamla;
    Activity listeleActivity;
    LayoutInflater inflater;
    private static final String TAG = "MyCustomDialog";
    private TextView mActionOk, mActionCancel;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activitydialog, container, false);
        hatirlat = view.findViewById(R.id.buttonHatirlat);
        delete = view.findViewById(R.id.buttonSil);
        tamamla=view.findViewById(R.id.buttonTam);

        hatirlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                //get().dismiss();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");
                // getDialog().dismiss();
            }
        });

        return view;
    }

    public void showAlertDialog(Context context,Alisveris alisveris,LayoutInflater inflater2) {
        inflater=inflater2;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Aksiyonu belirleyiniz");
        String[] items = {"Sil","Hatirlat","Tamamla"};
        int checkedItem = 1;
        Log.d(TAG, "onClick: closing dialog");
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//dialog.cancel();
                Log.d("null",items[which]);

                if(items[which].equals("Sil")){

                    DatabaseHelper dbhelper=new DatabaseHelper(context);
                    dbhelper.deleteRow(alisveris.getListeAdi(),alisveris.getAlisverisId());

                    Intent intent=new Intent(inflater.getContext(),MainActivity.class);
                    inflater.getContext().startActivity(intent);

                }

                if(items[which].equals("Hatirlat")){
                    DatabaseHelper dbhelper=new DatabaseHelper(context);
                    try {
                        ArrayList<Alisveris>liste=dbhelper.readSpecific(alisveris.getListeAdi());
                        Date date=liste.get(0).getTarih();
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                        String strDate = formatter.format(date);
                        Log.println(Log.ASSERT,"Tarih",strDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


//Toast.makeText(context,"sa",Toast.LENGTH_LONG).show();


                }

//dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
    /*
    public void updateDetail() {

        Intent intent=new Intent(inflater.getContext(),SoruEkle.class);
        intent.putExtra("soru",);
        inflater.getContext().startActivity(intent);

    }
    */

}
