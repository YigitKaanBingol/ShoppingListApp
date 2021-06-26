package com.example.shoppinglistapp;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAccumulator;

public class Alisveris implements Serializable {
    private Date tarih;
    private double  lat;
    private double  lng;
    private byte[] foto;
    private Integer alisverisId;
    private String listeAdi;
    public Alisveris(Integer alisverisId,String listeAdi,Date tarih, Double lat, Double lng, byte[] foto){
        this.tarih = tarih;
        this.lat = lat;
        this.lng = lng;
        this.foto = foto;
        this.alisverisId=alisverisId;
        this.listeAdi=listeAdi;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Integer getAlisverisId() { return alisverisId; }

    public void setAlisverisId(Integer alisverisId) { this.alisverisId = alisverisId;}

    public String getListeAdi() {
        return listeAdi;
    }

    public void setListeAdi(String listeAdi) {
        this.listeAdi = listeAdi;
    }
}
