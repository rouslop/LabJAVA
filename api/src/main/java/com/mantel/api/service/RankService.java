package com.mantel.api.service;

public interface RankService {
    public boolean valorarContenido(long idU, long idC, int puntaje);
    public double obtenerRank(long idC);
}
