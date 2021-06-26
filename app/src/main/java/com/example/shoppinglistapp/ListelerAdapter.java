package com.example.shoppinglistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Bundle;

import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;

public class ListelerAdapter extends RecyclerView.Adapter<ListelerAdapter.MyViewHolder>  {

    ArrayList<Alisveris> listelerim;
    LayoutInflater inflater;
    ImageButton btnSettings;

    public ListelerAdapter(Context context, ArrayList<Alisveris> listelerim) {
        inflater = LayoutInflater.from(context);
        this.listelerim = listelerim;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.liste_isim, parent, false);
        btnSettings=view.findViewById(R.id.imageButton6);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Alisveris seciliListe= listelerim.get(position);

        holder.setData(seciliListe, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(inflater.getContext(),UrunEkle.class);
                intent.putExtra("listeadi",seciliListe);
                inflater.getContext().startActivity(intent);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogMaker dm=new DialogMaker();
                dm.showAlertDialog(view.getContext(),listelerim.get(position),inflater);
                Log.d(TAG, "onClick: closing dialog");
            }
        });

    }

    @Override
    public int getItemCount() {
        return listelerim.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView listeAdi;



        public MyViewHolder(View itemView) {
            super(itemView);
            listeAdi = (TextView) itemView.findViewById(R.id.textViewListeIsim);
        }

        public void setData(Alisveris seciliListe, int position) {

            this.listeAdi.setText(seciliListe.getListeAdi());
        }


        @Override
        public void onClick(View v)  {
        }


    }


}

