package com.example.ficheros;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio3 extends AppCompatActivity {

    private ListView listView;

    private Web[] datos = arrayWebs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new AdaptadorWebs(this));
    }

    class AdaptadorWebs extends ArrayAdapter<Web> {
        Activity context;
        AdaptadorWebs(Activity context) {
            super(context, R.layout.item_webs, datos);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.item_webs, null);

            TextView tvNombre = (TextView) item.findViewById(R.id.tvNombre);
            tvNombre.setText(datos[position].getNombre());

            TextView tvEnlace = (TextView) item.findViewById(R.id.tvEnlace);
            tvEnlace.setText(datos[position].getEnlace());

            ImageView ivLogo = (ImageView) item.findViewById(R.id.ivLogo);
            ivLogo.setImageResource(datos[position].getLogo());

            TextView tvId = (TextView) item.findViewById(R.id.tvId);
            tvId.setText(datos[position].getId());



            return (item);
        }


    }

    public Web[] arrayWebs(){
        ArrayList<Web> arrayList = new ArrayList<Web>();
        try {
            InputStream fraw =getResources().openRawResource(R.raw.fich_recurso);
            BufferedReader brin =new BufferedReader( new InputStreamReader(fraw));

            String linea= brin.readLine();
            while (linea!=null){
                String[] strsplit = linea.split(";");
                String nombre = strsplit[0];
                String enlace = strsplit[1];
                int imagen = Integer.parseInt(strsplit[2]);
                int id = Integer.parseInt(strsplit[3]);
                Web web = new Web(nombre,enlace,imagen,id);
                arrayList.add(web);
                Log.i("Ficheros", linea);
                linea=brin.readLine();
            }
            fraw.close();
        }catch (Exception ex) {
            Log.e ("Ficheros", "Error al leer fichero desde recurso raw");
        }

        return (Web[]) arrayList.toArray(new Web[0]);
    }
}
