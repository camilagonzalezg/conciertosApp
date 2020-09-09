package cl.inacap.conciertosApp.dao;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.conciertosApp.dto.Concierto;

public class ConciertosDAO {

    private static List<Concierto> conciertos = new ArrayList<Concierto>();

    public ConciertosDAO() {
    }

    public void add(Concierto c) {
        conciertos.add(c);
    }

    public List<Concierto> getAll() {
        return conciertos;
    }

}
