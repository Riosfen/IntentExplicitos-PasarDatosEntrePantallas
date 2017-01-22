package com.example.samo_.intentexplicitos_pasardatosentrepantallas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by samo_ on 22/01/2017.
 */

public class NuevoCalculo extends AppCompatActivity {

    private EditText editCifra1, editCifra2;
    private RadioGroup rdgGrupo;
    private RadioButton rdbSuma, rdbResta, rdbMulti, rdbDiv;
    private Button btnReiniciar, btnCalcular;
    private TextView txtResultado, txtTotal;
    private int resultado, total;
    private static int acumulado = 0;

    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_calculo);

////////////////////////////////////////////////////////////////////////////////////////////////////
        // Creamos un nuevo Intent
        intent = new Intent();
////////////////////////////////////////////////////////////////////////////////////////////////////

        editCifra1 = (EditText) findViewById(R.id.editCifra1Nuevo);
        editCifra2 = (EditText) findViewById(R.id.editCifra2Nuevo);
        rdgGrupo = (RadioGroup) findViewById(R.id.rdbGrupoNuevo);
        // IMPORTANTE RADIOBUTTON
        //          HERE:
        // para que aparezca seleccionado hay que ponerchecked="true" en el XML
        rdbSuma = (RadioButton) findViewById(R.id.rdbSumaNuevo);
        rdbResta = (RadioButton) findViewById(R.id.rdbRestaNuevo);
        rdbMulti = (RadioButton) findViewById(R.id.rdbMultiNuevo);
        rdbDiv = (RadioButton) findViewById(R.id.rdbDivNuevo);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciarNuevo);
        btnCalcular = (Button) findViewById(R.id.btnCalcularNuevo);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtResultado = (TextView) findViewById(R.id.txtRecultadoNuevo);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        boolean reiniciado = extras.getBoolean("reiniciado");
        if(reiniciado){
            acumulado = 0;
        }
        resultado = extras.getInt("resultado");
        acumulado = acumulado + resultado;
        txtResultado.setText(String.valueOf(acumulado));

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (rdbSuma.isChecked()) {
                        total = Integer.parseInt(editCifra1.getText().toString()) + Integer.parseInt(editCifra2.getText().toString());
                    } else if (rdbResta.isChecked()) {
                        total = Integer.parseInt(editCifra1.getText().toString()) - Integer.parseInt(editCifra2.getText().toString());
                    } else if (rdbMulti.isChecked()) {
                        total = Integer.parseInt(editCifra1.getText().toString()) * Integer.parseInt(editCifra2.getText().toString());
                    } else {
                        total = Integer.parseInt(editCifra1.getText().toString()) / Integer.parseInt(editCifra2.getText().toString());
                    }
                    txtTotal.setVisibility(View.VISIBLE);
                    txtResultado.setVisibility(View.VISIBLE);
                    acumulado = acumulado + total;
////////////////////////////////////////////////////////////////////////////////////////////////////
                    // ejecuta el metodo sobreescrito de abajo para volver al principal
                    finish();
////////////////////////////////////////////////////////////////////////////////////////////////////
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Introduce un número", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCifra1.setText("");
                editCifra2.setText("");
                txtResultado.setText("0");
                acumulado = 0;
                txtTotal.setVisibility(View.INVISIBLE);
                txtResultado.setVisibility(View.INVISIBLE);
                intent.putExtra("reiniciado", true);
            }
        });
    }

/////////////////////////////////////////////////////////////////////////////////
    // Metodo necesario para devolver algo a la clase principal
    @Override
    public void finish() {
        intent.putExtra("resultado", total);
        // RESULT_OK: si se va a mandar algo se pone y si no tambien (palabras textuales de juan)
        setResult(RESULT_OK, intent);
        super.finish();
    }
/////////////////////////////////////////////////////////////////////////////////
}
