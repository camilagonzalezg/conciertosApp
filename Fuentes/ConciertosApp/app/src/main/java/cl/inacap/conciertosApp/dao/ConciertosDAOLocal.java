package cl.inacap.conciertosApp.dao;

import java.util.List;

import cl.inacap.conciertosApp.dto.Concierto;

public interface ConciertosDAOLocal {

    public void add (Concierto c);
    public List<Concierto> getAll();

}
