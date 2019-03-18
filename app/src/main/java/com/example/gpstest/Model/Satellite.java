package com.example.gpstest.Model;

public class Satellite {
    private int prn;
    private float azimuth;
    private float elevation;
    private float snr;

    public Satellite() {
    }

    public Satellite(int prn, float azimuth, float elevation, float snr) {
        this.prn = prn;
        this.azimuth = azimuth;
        this.elevation = elevation;
        this.snr = snr;
    }

    public int getPrn() {
        return prn;
    }

    public float getAzimuth() {
        return azimuth;
    }

    public float getElevation() {
        return elevation;
    }

    public float getSnr() {
        return snr;
    }

    public void setPrn(int prn) {
        this.prn = prn;
    }

    public void setAzimuth(float azimuth) {
        this.azimuth = azimuth;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public void setSnr(float snr) {
        this.snr = snr;
    }
}