package com.example.practica5_listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MotorBikeAdapter extends RecyclerView.Adapter<MotorBikeAdapter.MotorViewHolder>

{
    private ArrayList<Motos> listaMotos;
    public MotorBikeAdapter(ArrayList<Motos> listaMotos){
        this.listaMotos = listaMotos;

    }

    public static class MotorViewHolder extends RecyclerView.ViewHolder {
        public TextView modeloView;

        public TextView tipoView;

        public ImageView imageView;

        private Context context ;

        public MotorViewHolder(View view, Context context ){
            super(view);
            this.context = context;

            modeloView = view.findViewById(R.id.modelo);
            tipoView = view.findViewById(R.id.tipo);
            imageView = view.findViewById(R.id.fotoMoto);

        }
        public void BindMotorbikes(Motos motos){
            modeloView.setText(motos.modelo);
            tipoView.setText(motos.tipo);
            Picasso.get().load(motos.image).into(imageView);    //CAPTURA 16

        }

    }

    @NonNull
    @Override
    public MotorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moto_item, parent, false);
        return new MotorViewHolder(view, parent.getContext());

    }

    @Override
    public void onBindViewHolder(@NonNull MotorViewHolder holder, int position){
        holder.BindMotorbikes(this.listaMotos.get(position));
    }

    @Override
    public int getItemCount(){
        return this.listaMotos.size();
    }


}