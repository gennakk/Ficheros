package com.example.ficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEjercicio1 = findViewById(R.id.btnEjercicio1);
        btnEjercicio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ejercicio1.class);

                startActivity(intent);

            }
        });


        Button btnEjercicio2 = findViewById(R.id.btnEjercicio2);
        btnEjercicio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ejercicio2.class);

                startActivity(intent);

            }
        });


        Button btnEjercicio3 = findViewById(R.id.btnEjercicio3);
        btnEjercicio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ejercicio3.class);

                startActivity(intent);

            }
        });
    }
}
