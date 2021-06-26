package com.example.shoppinglistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    String tablename = "Urunler";
    String tablename2 = "Listeler";

    public DatabaseHelper(Context context) {
        super(context, "ShoppingListApp", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create2 = " CREATE TABLE " + tablename + "(id INTEGER PRIMARY KEY AUTOINCREMENT, alisverisListId INTEGER, urunAdi VARCHAR(256),adet INTEGER, fiyat FLOAT,FOREIGN KEY (\"alisverisListId\") REFERENCES Listeler(\"id\"))";

        String create = " CREATE TABLE " + tablename2 + "(id INTEGER PRIMARY KEY AUTOINCREMENT,listeAdi VARCHAR(256),tarih VARCHAR(256),lat DOUBLE,lng DOUBLE,foto BLOB)";
        sqLiteDatabase.execSQL(create);
        sqLiteDatabase.execSQL(create2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void instertUrun(Urun urun, int AlisverisListId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cd = new ContentValues();
        cd.put("alisverisListId", AlisverisListId);
        cd.put("urunAdi", urun.getUrunAdi());
        cd.put("adet", urun.getUrunAdeti());
        cd.put("fiyat", urun.getUrunFiyati());
        db.insert("Urunler", null, cd);
    }


    public void ListeyeEkle(Alisveris alisveris) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cd = new ContentValues();
        Date tarih = alisveris.getTarih();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(tarih);


        cd.put("listeAdi", alisveris.getListeAdi());
        cd.put("tarih", strDate);
        cd.put("lat", alisveris.getLat());
        cd.put("lng", alisveris.getLng());
        cd.put("foto", alisveris.getFoto());
        db.insert("Listeler", null, cd);
    }

    public ArrayList<Alisveris> readData() throws ParseException {


        ArrayList<Alisveris> listeler = new ArrayList<Alisveris>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from Listeler";
        Cursor result = db.rawQuery(query, null);

        if (result.moveToFirst()) {

            do {

                Alisveris liste = new Alisveris(null, null, null, 5.0, 5.0, null);
                liste.setAlisverisId(Integer.parseInt(result.getString(result.getColumnIndex("id"))));
                liste.setListeAdi(result.getString(result.getColumnIndex("listeAdi")));
                Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(result.getString(result.getColumnIndex("tarih")));
                liste.setTarih(date1);
                liste.setLat(Double.parseDouble(result.getString(result.getColumnIndex("lat"))));
                liste.setLng(Double.parseDouble(result.getString(result.getColumnIndex("lng"))));
                liste.setFoto(result.getBlob(result.getColumnIndex("foto")));

                listeler.add(liste);


            } while (result.moveToNext());

        }
        result.close();
        db.close();
        return listeler;

    }

    public ArrayList<Urun> readUrun(int alisverisId) throws ParseException {


        ArrayList<Urun> urunler = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from Urunler where alisverisListId=" + alisverisId;
        Cursor result = db.rawQuery(query, null);

        if (result.moveToFirst()) {

            do {

                //     Alisveris liste=new Alisveris(null,null,null,5.0,5.0,null) ;
                Urun urun = new Urun("elma", 0, 0.0f);
                urun.setUrunAdi(result.getString(result.getColumnIndex("urunAdi")));
                urun.setUrunAdeti(Integer.parseInt(result.getString(result.getColumnIndex("adet"))));
                urun.setUrunFiyati(Float.parseFloat(result.getString(result.getColumnIndex("fiyat"))));

                urunler.add(urun);


            } while (result.moveToNext());

        }
        result.close();
        db.close();
        return urunler;

    }

    public void setPhoto(int alisverisListId, byte[] foto) {

        ContentValues cv = new ContentValues();
        cv.put("foto", foto);

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Listeler SET foto =... Where id=" + alisverisListId;
        db.update("Listeler", cv, "id = ?", new String[]{String.valueOf(alisverisListId)});
    }

    public void setDate(int alisverisListId, String tarih) {

        ContentValues cv = new ContentValues();

        cv.put("tarih", tarih);

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Listeler SET foto =... Where id=" + alisverisListId;
        db.update("Listeler", cv, "id = ?", new String[]{String.valueOf(alisverisListId)});
    }


    public void deleteRow(String listeAdi, int alisverisId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.execSQL("delete from "+ tablename);
        //  db.delete(tablename,"soru=?",new String[]{"soru"});
        db.execSQL("DELETE FROM " + tablename2 + " WHERE listeAdi" + "='" + listeAdi + "'");
        db.execSQL("DELETE FROM " + tablename + " WHERE alisverisListId" + "='" + alisverisId + "'");

        Log.d(null, "silindi");
    }


    public ArrayList<Alisveris> readSpecific(String listeAdi) throws ParseException {

        ArrayList<Alisveris> liste = new ArrayList<Alisveris>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from Listeler where listeAdi='" + listeAdi + "'";
        Cursor result = db.rawQuery(query, null);
        if (result.moveToFirst()) {

            do {

                Alisveris listem = new Alisveris(null, null, null, 5.0, 5.0, null);
                listem.setAlisverisId(Integer.parseInt(result.getString(result.getColumnIndex("id"))));
                listem.setListeAdi(result.getString(result.getColumnIndex("listeAdi")));
                Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(result.getString(result.getColumnIndex("tarih")));
                listem.setTarih(date1);
                listem.setLat(Double.parseDouble(result.getString(result.getColumnIndex("lat"))));
                listem.setLng(Double.parseDouble(result.getString(result.getColumnIndex("lng"))));
                listem.setFoto(result.getBlob(result.getColumnIndex("foto")));


                liste.add(listem);
            } while (result.moveToNext());

        }
        result.close();
        db.close();
        return liste;
    }

/*
    public void DatabaseSil() {
        //SQLiteDatabase db = this.getWritableDatabase();

    }*/


}