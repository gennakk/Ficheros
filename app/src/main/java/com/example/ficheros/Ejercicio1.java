package com.example.ficheros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ejercicio1 extends AppCompatActivity {

    private static final int SOLICITUD_PERMISO_WRITE_SD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio1);

        if (comprobarPermisos()){
            Toast.makeText(Ejercicio1.this, "Tenemos permisos para escribir",Toast.LENGTH_SHORT).show();
            if (sdDisponible()) {
                Toast.makeText(Ejercicio1.this, "Tarjeta lista para poder escribir",Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(Ejercicio1.this, "Tarjeta NO lista para poder escribir",Toast.LENGTH_SHORT).show();
        }else {
            solicitarPermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE,"Sin este permiso no se puede ESCRIBIR en el dispositivo externo",SOLICITUD_PERMISO_WRITE_SD, Ejercicio1.this);
        }


        final EditText editText = findViewById(R.id.editText);


        Button btnAniadirInterno = findViewById(R.id.btnAniadirInterno);
        Button btnAniadirExterno = findViewById(R.id.btnAniadirExterno);
        Button btnLeerInterno = findViewById(R.id.btnLeerInterno);
        Button btnLeerExterno = findViewById(R.id.btnLeerExterno);
        Button btnLeerRecurso = findViewById(R.id.btnLeerRecurso);
        Button btnBorrarInterno = findViewById(R.id.btnBorrarInterno);
        Button btnBorrarExterno = findViewById(R.id.btnBorrarExterno);


        final TextView tvContenido = findViewById(R.id.tvContenido);


        btnAniadirInterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    OutputStreamWriter osw=new OutputStreamWriter(openFileOutput("fich_interno.txt", Context.MODE_APPEND));
                    osw.write(editText.getText().toString()+'\n');
                    osw.close();
                } catch (Exception e) {
                    Log.e ("Ficheros", "ERROR!! al escribir fichero enmemoria interna");
                }
            }
        });

        btnAniadirExterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File ruta_sd = Environment.getExternalStorageDirectory();
                    File f = new File(ruta_sd.getAbsolutePath(),"fich_externo.txt");
                    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f,true));
                    osw.write(editText.getText().toString()+'\n');
                    osw.close();
                    Log.i ("Ficheros", "fichero escrito correctamente");
                }catch (Exception ex) {
                    Log.e ("Ficheros", "Error al escribir fichero en tarjeta SD")
                    ;
                }
            }
        });

        btnLeerInterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    BufferedReader fin =new BufferedReader(new InputStreamReader(openFileInput("fich_interno.txt")));

                    tvContenido.setText("");
                    String texto = "";
                    String linea = fin.readLine();
                    while(linea!=null){

                        texto=texto + linea+"\n";

                        linea=fin.readLine();
                    }
                    fin.close();
                    tvContenido.setText(texto);
                    Log.i("Ficheros", "Fichero leido!");
                    Log.i("Ficheros", "Texto: " + texto);

                }catch (Exception ex){
                    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
                }
            }
        });

        btnLeerExterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File ruta_sd = Environment.getExternalStorageDirectory();
                    File f= new File(ruta_sd.getAbsolutePath(), "fich_externo.txt");
                    BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    String texto="";
                    String linea= br.readLine();
                    while (linea!=null){

                        texto=texto+linea+"\n";

                        linea= br.readLine();
                    }
                    br.close();
                    tvContenido.setText(texto);

                }catch (Exception ex){
                    Log.e("Ficheros", "ERROR!! en la lectura del ficheroen SD");
                }
            }
        });

        btnLeerRecurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputStream fraw =getResources().openRawResource(R.raw.fich_recurso);
                    BufferedReader brin =new BufferedReader( new InputStreamReader(fraw));
                    String texto="";
                    String linea= brin.readLine();
                    while (linea!=null){
                        texto=texto+linea+"\n";
                        Log.i("Ficheros", linea);
                        linea=brin.readLine();
                    }
                    fraw.close();
                    tvContenido.setText(texto);
                }catch (Exception ex) {
                    Log.e ("Ficheros", "Error al leer fichero desde recurso raw");
                }

            }
        });

        btnBorrarInterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File dir = getFilesDir();
                File f= new File (dir,"fich_interno.txt");

                if(f.delete())
                    Toast.makeText(Ejercicio1.this, "Fichero borrado",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Ejercicio1.this, "Fichero NO borrado",Toast.LENGTH_SHORT).show();



            }
        });

        btnBorrarExterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File ruta_sd = Environment.getExternalStorageDirectory();
                File f= new File(ruta_sd.getAbsolutePath(), "fich_externo.txt");

                if(f.delete())
                    Toast.makeText(Ejercicio1.this, "Fichero borrado",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Ejercicio1.this, "Fichero NO borrado",Toast.LENGTH_SHORT).show();

            }
        });



    }

    private boolean comprobarPermisos (){
        //Comprobamos que tenemos permiso para realizar la operación.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else {
            return false;
        }
    }

    private boolean sdDisponible (){
        boolean sdDisponible = false;
        boolean sdAccesoEscritura= false;
        //Comprobamos el estado de la memoria externa
        String estado = Environment.getExternalStorageState();
        Log.i("Memoria", estado);
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;sdAccesoEscritura = false;
        } else {
            sdDisponible = false;sdAccesoEscritura = false;
        }if (sdDisponible)
            Toast.makeText(getApplicationContext(),"Tengo Tarjeta SD",Toast.LENGTH_SHORT).show();
        if (sdAccesoEscritura)
            Toast.makeText(getApplicationContext(),"La tarjeta SD es escribible",Toast.LENGTH_SHORT).show();
        if (sdDisponible &&sdAccesoEscritura)
            return true;
        else
            return false;
    }

    private static void solicitarPermiso (final String permiso,String justificacion,final int requestCode,final Activity actividad){
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)){
            //Informamos al usuario para qué y por qué se necesitan los permisos
            new AlertDialog.Builder(actividad).setTitle("Solicitud de permiso").setMessage(justificacion).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(actividad,new String[]{permiso},requestCode);
                }
            }).show();
        }else {
            //Muestra el cuadro de dialogo para la solicitud de permisos y registra el permiso según respuesta del usuario
            ActivityCompat.requestPermissions(actividad,new String[]{permiso}, requestCode);
        }

    }


    }
