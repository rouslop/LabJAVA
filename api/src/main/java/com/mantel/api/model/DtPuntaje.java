package com.mantel.api.model;

public class DtPuntaje {
    private double puntaje;
    private long idC;

    public DtPuntaje(double puntaje, long idC) {
        this.puntaje = puntaje;
        this.idC = idC;
    }

    public DtPuntaje() {
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }
}
