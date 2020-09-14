package cl.inacap.conciertosApp;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cl.inacap.conciertosApp.dao.ConciertosDAO;
import cl.inacap.conciertosApp.dao.ConciertosDAOLocal;
import cl.inacap.conciertosApp.dto.Concierto;

public class MainActivity extends AppCompatActivity {

    private EditText nombreTxt;
    private TextView verFechaTxt;
    private EditText editFechaTxt;
    private Spinner generoTxt;
    private TextView verGeneroTxt;
    private EditText valorTxt;
    private EditText calificacionTxt;
    private Button agregarBtn;
    private ListView conciertoTxt;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtener referencia del View
        this.nombreTxt = findViewById(R.id.id_nombre);
        this.editFechaTxt = findViewById(R.id.et_date);
        this.verFechaTxt = findViewById(R.id.tv_date);
        this.generoTxt = findViewById(R.id.id_spinner);
        this.verGeneroTxt = findViewById(R.id.id_verSpinner);
        this.valorTxt = findViewById(R.id.id_valor);
        this.calificacionTxt = findViewById(R.id.id_calificacion);
        this.agregarBtn = findViewById(R.id.agregarBtn);
        this.conciertoTxt = findViewById(R.id.id_concierto);

        //Listener Boton agregar
        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> errores = new ArrayList<>();

                //Validar Nombre
                String nombreStr = nombreTxt.getText().toString().trim();
                if(nombreStr.isEmpty()){
                        errores.add("Ingrese nombre");
                    }

                //Validar Fecha
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                verFechaTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                MainActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                setListener,year,month,day);
                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
                    }
                });
                setListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        verFechaTxt.setText(date);
                    }
                };

                editFechaTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                            month=month+1;
                            String date = day+"/"+month+"/"+year;
                            editFechaTxt.setText(date);
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });

                //Validar Genero
                ArrayList<String> listaGeneros = new ArrayList<>();
                listaGeneros.add("Seleccione Genero");
                listaGeneros.add("Rock");
                listaGeneros.add("Jazz");
                listaGeneros.add("Pop");
                listaGeneros.add("Regueton");
                listaGeneros.add("Salsa");
                listaGeneros.add("Metal");

                generoTxt.setAdapter(new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,listaGeneros));

                generoTxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        if(position==0){
                            Toast.makeText(getApplicationContext(),
                                    "Seleccione Genero",Toast.LENGTH_SHORT).show();
                            verGeneroTxt.setText("");
                        }else{
                            String textoGenero = adapterView.getItemAtPosition(position).toString();
                            verGeneroTxt.setText(textoGenero);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                String generoStr = verGeneroTxt.getText().toString().trim();

                //Validar Valor
                String valorStr = valorTxt.getText().toString().trim();
                int valor=0;
                try{
                    valor = Integer.parseInt(valorStr);
                    if(valor < 0){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException ex){
                    errores.add("El valor debe ser mayor a 0");
                }
                //Validar Calificacion
                String calificacionStr = calificacionTxt.getText().toString().trim();
                int calificacion=0;
                try{
                    calificacion = Integer.parseInt(calificacionStr);
                    if (calificacion <0 || calificacion >7){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException ex){
                    errores.add("La calificación debe ser de 1 a 7");
                }

                //Validar Errores
                if(errores.isEmpty()){
                    //Ingresar Concierto
                    Concierto concierto = new Concierto();

                    concierto.setArtista(nombreStr);
                    //concierto.setFecha();
                    concierto.setGenero(generoStr);
                    concierto.setValor(valor);
                    concierto.setCalificacion(calificacion);

                    new ConciertosDAO().add(concierto);

                    //Mostrar en el List

                } else{
                    mostrarErrores(errores);
                }

            }
        });
    }

    private void mostrarErrores(List<String> errores){
        String mensaje = "";
        for(String e: errores){
            mensaje+= "-" + e + "\n";
        }
        //Mostrar-mensaje de alerta
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Error de validación")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .create()
                .show();

    }

}