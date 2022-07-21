package com.mantel.api.model;

import java.util.List;

public class DtSub {
    private List<Suscripcion> suscripciones;
    private List<SuscripcionPerPayView> payperview;

    public DtSub() {
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    public void setSuscripciones(List<Suscripcion> suscripciones) {
        this.suscripciones = suscripciones;
    }

    public List<SuscripcionPerPayView> getPayperview() {
        return payperview;
    }

    public void setPayperview(List<SuscripcionPerPayView> payperview) {
        this.payperview = payperview;
    }
}
