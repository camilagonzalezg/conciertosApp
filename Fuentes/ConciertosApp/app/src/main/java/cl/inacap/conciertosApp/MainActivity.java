package cl.inacap.conciertosApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.conciertosApp.dto.Concierto;

public class MainActivity extends AppCompatActivity {

    private EditText nombreTxt;
    private DatePicker fechaTxt;
    private Spinner generoTxt;
    private EditText valorTxt;
    private EditText calificacionTxt;
    private Button agregarBtn;
    private ListView conciertoTxt;

    private String[] generos = {"Rock", "Jazz", "Pop", "Regueton", "Salsa", "Metal"};
    // private ArrayAdapter<Concierto> conciertosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtener referencia del View
        this.nombreTxt = findViewById(R.id.id_nombre);
        this.fechaTxt = findViewById(R.id.id_fecha);
        this.generoTxt = findViewById(R.id.id_genero);
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
                        errores.add("El valor debe ser mayor a 0");
                    }

                //Validar Fecha



                //Validar Valor
                String valorStr = valorTxt.getText().toString().trim();
                int valor;
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
                int calificacion;
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
                    //Mostrar en el List
                } else{
                    //mostrarErrores();
                }

            }
        });
    }
}