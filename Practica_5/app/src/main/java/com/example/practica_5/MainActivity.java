package com.example.practica_5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button botonContinuar;
    private EditText cajaCorreo;
    private EditText cajaPass;
    private TextView textoOculto;
    private Switch recordar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        botonContinuar = findViewById(R.id.BotonContinuar);
        cajaCorreo = findViewById(R.id.CajaTextoCorreo);
        cajaPass = findViewById(R.id.CajaTextoPass);
        textoOculto = findViewById(R.id.textoOculto);
        recordar = findViewById(R.id.recordar);

        botonContinuar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.BotonContinuar){
            if(cajaCorreo.getText().toString().equals("correo@correo.com") && cajaPass.getText().toString().equals("123")) {
                textoOculto.setVisibility(View.VISIBLE);
                textoOculto.setTextColor(getColor(R.color.green));
                textoOculto.setText("Usuario y Contraseña CORRECTOS :)");
                if(recordar.isChecked() == true){
                    textoOculto.setText(textoOculto.getText() + "\n" + "Almacenados para siguientes accesos");
                }
            }else{
                textoOculto.setVisibility(View.VISIBLE);
                textoOculto.setTextColor(getColor(R.color.red));
                textoOculto.setText("Usuario y/o Contraseña INCORRECTOS :(");

            }

        }
    }
}