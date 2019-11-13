package com.example.ficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ejercicio2 extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        spinner =  findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }


    public String[] arraySpinner(){
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            InputStream fraw =getResources().openRawResource(R.raw.fich_recurso);
            BufferedReader brin =new BufferedReader( new InputStreamReader(fraw));

            String linea= brin.readLine();
            while (linea!=null){
                arrayList.add(linea);
                Log.i("Ficheros", linea);
                linea=brin.readLine();
            }
            fraw.close();
        }catch (Exception ex) {
            Log.e ("Ficheros", "Error al leer fichero desde recurso raw");
        }

        return (String[])arrayList.toArray(new String[0]);
    }
}
