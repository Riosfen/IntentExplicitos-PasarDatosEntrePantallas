package com.example.samo_.intentexplicitos_pasardatosentrepantallas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
////////////////////////////////////////////////////////////////////////////////////////////////////
    // si el requestCode del metodo onActivityResult
    // es igual al REQUEST_CODE entonces es que ha ido bien
    private static final int REQUEST_CODE = 10;
////////////////////////////////////////////////////////////////////////////////////////////////////

    private EditText editCifra1, editCifra2;
    private RadioGroup rdgGrupo;
    private RadioButton rdbSuma, rdbResta, rdbMulti, rdbDiv;
    private Button btnReiniciar, btnCalcular;
    private TextView txtResultado, txtTotal;
    private static int acumulado = 0;

    private Intent intent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////////////////////////////////////////////////////////////////////////////////////////////////////
        // Se crea un nuevo Intent y le pasamos el Context()
        // de la aplicacion y de donde recibira el intent .class
        // hace falta definirla en el manifest
        intent = new Intent(getApplicationContext(), NuevoCalculo.class);
////////////////////////////////////////////////////////////////////////////////////////////////////

        editCifra1 = (EditText) findViewById(R.id.editCifra1);
        editCifra2 = (EditText) findViewById(R.id.editCifra2);
        rdgGrupo = (RadioGroup) findViewById(R.id.rdbGrupo);
        // IMPORTANTE RADIOBUTTON
        //          HERE:
        // para que aparezca seleccionado hay que ponerchecked="true" en el XML
        rdbSuma = (RadioButton) findViewById(R.id.rdbSuma);
        rdbResta = (RadioButton) findViewById(R.id.rdbResta);
        rdbMulti = (RadioButton) findViewById(R.id.rdbMulti);
        rdbDiv = (RadioButton) findViewById(R.id.rdbDiv);
        btnReiniciar = (Button) findViewById(R.id.btnReiniciar);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        txtResultado = (TextView) findViewById(R.id.txtRecultado);
        txtResultado.setVisibility(View.INVISIBLE);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotal.setVisibility(View.INVISIBLE);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total;
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
                    acumulado = acumulado + total;

                    intent.putExtra("resultado", total);
////////////////////////////////////////////////////////////////////////////////////////////////////
                    // metodo para ir al .class que se le indico arriba
                    startActivityForResult(intent, REQUEST_CODE);
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
////////////////////////////////////////////////////////////////////////////////////////////////////
                // para guardar algo en el intent (se le pone un String para
                // identificarlo y lo que queremos pasar)
                intent.putExtra("reiniciado", true);
////////////////////////////////////////////////////////////////////////////////////////////////////
                txtTotal.setVisibility(View.INVISIBLE);
                txtResultado.setVisibility(View.INVISIBLE);
            }
        });
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            editCifra1.setText("");
            editCifra2.setText("");
            // Para recoger los datos se utiliza el intent (data) .getExtras +
            // lo que queremos obtener
            boolean reiniciado = data.getExtras().getBoolean("reiniciado");
            // si se pulso el boton reiniciar, el valor acumulado = 0
            if(reiniciado){
                acumulado = 0;
            }

            acumulado = acumulado + data.getExtras().getInt("resultado");
            txtResultado.setText(String.valueOf(acumulado));
            txtTotal.setVisibility(View.VISIBLE);
            txtResultado.setVisibility(View.VISIBLE);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}