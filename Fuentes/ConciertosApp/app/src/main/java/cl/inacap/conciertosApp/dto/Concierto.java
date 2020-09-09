package cl.inacap.conciertosApp.dto;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class Concierto {

    private String artista;
    private DatePickerDialog fecha;
    private String genero;
    private int valor;
    private int calificacion;

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public DatePickerDialog getFecha() {
        return fecha;
    }

    public void setFecha(DatePickerDialog fecha) {
        this.fecha = fecha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}
