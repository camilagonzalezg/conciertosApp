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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cl.inacap.conciertosApp.dao.ConciertosDAO;
import cl.inacap.conciertosApp.dao.ConciertosDAOLocal;
import cl.inacap.conciertosApp.dto.Concierto;

public class MainActivity extends AppCompatActivity {

    private List<Concierto> conciertos = new ArrayList<>();
    private EditText nombreTxt;
    private TextView verFechaTxt;
    private EditText editFechaTxt;
    private Spinner generoTxt;
    private TextView verGeneroTxt;
    private EditText valorTxt;
    private EditText calificacionTxt;
    private Button agregarBtn;
    private ListView conciertosLv;
    private ArrayAdapter<Concierto> conciertosAdapter;

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
        this.conciertosLv = findViewById(R.id.id_concierto);
        this.conciertosAdapter = new ArrayAdapter<>(this
                ,android.R.layout.simple_list_item_1, conciertos);
        this.conciertosLv.setAdapter(conciertosAdapter);


        //Fecha
        final Calendar calendar = Calendar.getInstance();
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
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
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

        //Genero
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

        //Listener Boton agregar
        this.agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> errores = new ArrayList<>();
                int dia =0, mes=0,anno=0;
                //Validar Nombre
                String nombreStr = nombreTxt.getText().toString().trim();
                if(nombreStr.isEmpty()){
                        errores.add("Ingrese nombre");
                    }

                //Validar Fecha
                    if (editFechaTxt.getText().toString().isEmpty()){
                        errores.add("Debe ingresar una fecha");
                    } else{
                        dia = Integer.parseInt(editFechaTxt.getText().toString().substring(0,2));
                        mes = Integer.parseInt(editFechaTxt.getText().toString().substring(3,5));
                        anno = Integer.parseInt(editFechaTxt.getText().toString().substring(6,10));
                    }

                //Validar Genero
                String generoStr = verGeneroTxt.getText().toString().trim();
                if(generoStr.isEmpty()){
                    errores.add("Ingrese género");
                }

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
                    concierto.setFecha(new Date (anno,mes,dia));
                    concierto.setGenero(generoStr);
                    concierto.setValor(valor);
                    concierto.setCalificacion(calificacion);

                    new ConciertosDAO().add(concierto);

                    conciertosAdapter.notifyDataSetChanged();

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