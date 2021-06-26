package com.example.shoppinglistapp;

public class Urun {

    private String urunAdi;
    private int urunAdeti;
    private float urunFiyati;


    public Urun(String urunAdi, int urunAdeti, float urunFiyati) {
        this.urunAdi = urunAdi;
        this.urunAdeti = urunAdeti;
        this.urunFiyati = urunFiyati;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public int getUrunAdeti() {
        return urunAdeti;
    }

    public void setUrunAdeti(int urunAdeti) {
        this.urunAdeti = urunAdeti;
    }

    public float getUrunFiyati() {
        return urunFiyati;
    }

    public void setUrunFiyati(float urunFiyati) {
        this.urunFiyati = urunFiyati;
    }
}
