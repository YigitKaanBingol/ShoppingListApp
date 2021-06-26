package com.example.shoppinglistapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UrunlerAdapter extends RecyclerView.Adapter<UrunlerAdapter.MyViewHolder>  {

    ArrayList<Urun> urunlerim;
    LayoutInflater inflater;

    public UrunlerAdapter(Context context, ArrayList<Urun> urunlerim) {
        inflater = LayoutInflater.from(context);
        this.urunlerim = urunlerim;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.liste_karti, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Urun seciliUrun= urunlerim.get(position);
        holder.setData(seciliUrun, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return urunlerim.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView UrunAdi;
        TextView UrunAdedi;
        TextView UrunFiyati;


        public MyViewHolder(View itemView) {
            super(itemView);
            UrunAdi = (TextView) itemView.findViewById(R.id.textView2);
            UrunAdedi = (TextView) itemView.findViewById(R.id.textView3);
            UrunFiyati = (TextView) itemView.findViewById(R.id.textView4);
        }

        public void setData(Urun seciliUrun, int position) {

            this.UrunAdi.setText(seciliUrun.getUrunAdi());
          //  this.UrunAdi.setText(String.valueOf(urunlerim.size()));
           this.UrunAdedi.setText(String.valueOf(seciliUrun.getUrunAdeti()));
            this.UrunFiyati.setText(String.valueOf(seciliUrun.getUrunFiyati()));

        }


        @Override
        public void onClick(View v)  {
        }


    }


}

