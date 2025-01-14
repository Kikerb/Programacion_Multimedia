package com.example.practica_9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Idmonumento;
    private LinearLayout caja, borde;
    private Button buscar, comprar;
    private ImageView monumentoImg;
    private WebView monumentowebView;
    private TextView titulo,latitud,longitud,fecha,monumentoText,mensajeText, ubicacion;


    @SuppressLint("MissingInflatedId")
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
        Idmonumento = findViewById(R.id.MonumentId);
        buscar = findViewById(R.id.btnSearch);
        caja = findViewById(R.id.caja);
        monumentoImg = findViewById(R.id.imgMonument);
        monumentowebView = findViewById(R.id.webViewMonument);
        monumentoText = findViewById(R.id.MonumentText);
        mensajeText = findViewById(R.id.mensajeText);
        titulo = findViewById(R.id.titleText);
        fecha = findViewById(R.id.fechaText);
        latitud = findViewById(R.id.LatitudText);
        longitud = findViewById(R.id.LongitudText);
        comprar = findViewById(R.id.btnbuy);
        borde = findViewById(R.id.borde);
        ubicacion = findViewById( R.id.UbicacionText);

        buscar.setOnClickListener(this);

    }

    ControladorMonumento controlador = new ControladorMonumento(this);


    @Override
    public void onClick(View view) {

        String monumentId = Idmonumento.getText().toString();

        if (monumentId.isEmpty()) {
            mensajeText.setText("Por favor, introduce un ID válido");
            mensajeText.setVisibility(View.VISIBLE);
        }
        else{
            try {
                mensajeText.setVisibility(View.GONE);
                controlador.obtenerMonumentoID(monumentId, new VolleyCallBack() {
                    @Override
                    public void onSuccess(Context context, ArrayList<Monumento> monumentos) {
                        Monumento monumento = monumentos.get(0);

                        caja.setVisibility(View.VISIBLE);
                        borde.setVisibility(View.VISIBLE);
                        //MOSTRAR INFORMACION DEL CONTROLADOR

                        titulo.setText(monumento.nombre);
                        fecha.setText("Construido: "+ monumento.fecha);
                        Picasso.get().load(monumento.imagen).into(monumentoImg);
                        monumentoText.setText(monumento.descripcion);
                        latitud.setText("Latitud: "+ monumento.latitud);
                        longitud.setText("Longitud: "+ monumento.longitud);
                        ubicacion.setText(monumento.ciudad);
                        comprar.setText("Comprar tu entrada desde: "+ monumento.precio + "€");
                        String html = monumento.video;
                        WebSettings settings = monumentowebView.getSettings();
                        settings.setJavaScriptEnabled(true);
                        monumentowebView.loadData(html,"text/html","UTF-8");
                    }
                });
            } catch (ServidorPHPException e) {
                throw new RuntimeException(e);
            }
        }





    }
}