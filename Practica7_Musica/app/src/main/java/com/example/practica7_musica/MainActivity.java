package com.example.practica7_musica;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button botonPlay;
    private Button botonPause;
    private TextView textoOculto1;
    private TextView textoOculto2;
    private pl.droidsonroids.gif.GifImageView gif;
    private ImageView ImagenPausa;

    MediaPlayer mp;

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
        mp = MediaPlayer.create(this, R.raw.music);
        botonPlay = findViewById(R.id.buttonPlay);
        botonPause = findViewById(R.id.buttonPause);
        textoOculto1 = findViewById(R.id.textOculto1);
        textoOculto2 = findViewById(R.id.textOculto2);
        gif = findViewById(R.id.gifView);
        ImagenPausa = findViewById(R.id.imageView);


        botonPlay.setOnClickListener(this);
        botonPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonPlay){
            textoOculto2.setVisibility(View.GONE);
            ImagenPausa.setVisibility(View.GONE);
            textoOculto1.setVisibility(View.VISIBLE);
            gif.setVisibility(View.VISIBLE);
            mp.start();

        }

        if (view.getId() == R.id.buttonPause) {
            textoOculto1.setVisibility(View.GONE);
            gif.setVisibility(View.GONE);
            textoOculto2.setVisibility(View.VISIBLE);
            ImagenPausa.setVisibility(View.VISIBLE);
            mp.pause();

        }
    }

}