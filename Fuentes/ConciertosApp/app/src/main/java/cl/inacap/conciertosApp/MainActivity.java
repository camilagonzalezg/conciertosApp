package cl.inacap.conciertosApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import cl.inacap.conciertosApp.dto.Concierto;

public class MainActivity extends AppCompatActivity {

    private EditText nombre;
    private DatePicker fecha;
    String[] generos = { "Rock", "Jazz", "Pop", "Regueton", "Salsa", "Metal" };
    private ArrayAdapter<Concierto> conciertosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtener referencia del View
        this.nombre = findViewById(R.id.id_nombre_artista);
        this.fecha = findViewById(R.id.id_fecha);


        //Spinner
        Spinner spin = (Spinner) findViewById(R.id.id_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, generos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        //spin.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), "Genero Seleccionado: "+generos[position]
                ,Toast.LENGTH_SHORT).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }
}