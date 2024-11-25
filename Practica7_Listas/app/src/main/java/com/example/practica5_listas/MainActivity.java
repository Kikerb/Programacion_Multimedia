package com.example.practica5_listas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText cajaModelo, cajaUrl;
    private Spinner cajaTipo;
    private Button insertar;
    private BDMotos bdMotos;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bdMotos = new BDMotos(getApplicationContext());

        cajaModelo = findViewById(R.id.cajaModelo);
        cajaUrl = findViewById(R.id.cajaUrl);
        cajaTipo = findViewById(R.id.cajaTipo);
        insertar = findViewById(R.id.insertar);

        insertar.setOnClickListener(this);

        recyclerView = findViewById(R.id.recView);

        //Motos[] motos = new Motos[2];
       // motos[0] = new Motos("Yamaha YZ450F","Motocross","yz");
        //motos[1] = new Motos("Yamaha R1","Carretera","r1");

        //obtener las motos de la bd "obtenerTodosLasMotos()"
        ArrayList<Motos> motos = bdMotos.obtenerTodosLasMotos();

        MotorBikeAdapter adapter = new MotorBikeAdapter(motos);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        String opcionSeleccionada = cajaTipo.getSelectedItem().toString();
        String url = cajaUrl.getText().toString();
        String modelo = cajaModelo.getText().toString();
        bdMotos.insertMoto(modelo, opcionSeleccionada, url);

        ArrayList<Motos> motos = bdMotos.obtenerTodosLasMotos();
        MotorBikeAdapter adapter = new MotorBikeAdapter(motos);
        recyclerView.setAdapter(adapter);
    }

}