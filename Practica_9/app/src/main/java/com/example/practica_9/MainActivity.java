package com.example.practica_9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout caja, borde;
    private Button comprar;
    private ImageView monumentoImg;
    private WebView monumentowebView;
    private MapView mapa;
    private TextView titulo, latitud, longitud, fecha, monumentoText, mensajeText, ubicacion;


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
        ubicacion = findViewById(R.id.UbicacionText);
        mapa = findViewById(R.id.mapa);
        MapController mapController = (MapController) mapa.getController();
        Configuration.getInstance().setUserAgentValue("appIdMapPractice");


        GeoPoint startPoint = new GeoPoint(37.1773, -3.5986);
        mapa.getController().setCenter(startPoint);
        mapa.getController().setZoom(15.0);


        ControladorMonumento controlador = new ControladorMonumento(getApplicationContext());

        try {
            controlador.obtenerTodosMonumentos(new VolleyCallBack() {
                @Override
                public void onSuccess(Context context, ArrayList<Monumento> monumentos) {

                    for (Monumento monumento: monumentos){
                        double Latitud = Double.valueOf(monumento.latitud);
                        double Longitud = Double.valueOf(monumento.longitud);

                        GeoPoint point1 = new GeoPoint(Latitud, Longitud);
                        Marker marcador1 = new Marker(mapa);
                        marcador1.setPosition(point1);
                        mapa.getOverlays().add(marcador1);
                        mapa.invalidate();
                        marcador1.setOnMarkerClickListener((marker1, mapa) ->{

                            ViewGroup.LayoutParams params = mapa.getLayoutParams();

                            params.height = 500;

                            mapa.setLayoutParams(params);

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



                            return true;
                        });

                    }


                }

                @Override
                public void onError(String mensaje) {

                }
            });
        } catch (ServidorPHPException e) {
            throw new RuntimeException(e);
        }

    }


}
