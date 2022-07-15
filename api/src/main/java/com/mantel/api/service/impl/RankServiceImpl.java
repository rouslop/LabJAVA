package com.mantel.api.service.impl;

import com.mantel.api.model.Rank;
import com.mantel.api.service.RankService;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.List;

@Service
@Transactional
public class RankServiceImpl implements RankService {
    @PersistenceContext
    EntityManager em;

    public Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }
    @Override
    public boolean valorarContenido(long idU, long idC, int p) {
        Rank nuevo = new Rank();
        nuevo.setIdContenido(idC);
        nuevo.setIdUsuario(idU);
        nuevo.setPuntaje(p);
        this.em.persist(nuevo);
        Query q = this.em.createQuery("SELECT r FROM Rank r WHERE r.idContenido=:idc AND r.idUsuario=:idu");
        q.setParameter("idc",idC);
        q.setParameter("idu",idU);
        if(q.getResultList().isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public double obtenerRank(long idC) {
        Query q = this.em.createQuery("SELECT r FROM Rank r WHERE r.idContenido =:idc");
        q.setParameter("idc",idC);
        List<Rank> valoraciones = q.getResultList();
        if(valoraciones.isEmpty()){
            return 0;
        }
        int total = 0;
        for (Rank r: valoraciones){
            total = total+r.getPuntaje();
        }
        double res = total/valoraciones.size();
        return res;
    }
}
