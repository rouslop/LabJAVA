package com.mantel.api.service;

import com.mantel.api.model.SuscripcionPerPayView;

public interface SuscripcionPPVService {

    public void agregarSuscripcionPPV(SuscripcionPerPayView suscripcionPerPayView);

    public SuscripcionPerPayView obtenerSuscripcionPPV(long id);

}
