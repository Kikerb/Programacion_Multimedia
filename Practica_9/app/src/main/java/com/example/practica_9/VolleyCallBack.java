package com.example.practica_9;

import android.content.Context;

import java.util.ArrayList;

public interface VolleyCallBack {
    void onSuccess(Context context, ArrayList<Monumento> monumentos);
    void onError(String mensaje);
}
