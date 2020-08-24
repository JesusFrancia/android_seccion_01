package com.example.seccion_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private View btn;
    private final String GREETER = "hello from the other side!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //Forzar y cargar icono en el action bar
         getSupportActionBar().setDisplayShowHomeEnabled(true);
         getSupportActionBar().setIcon(R.mipmap.ic_myicon);

         btn = (Button) findViewById(R.id.buttonMain);
         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //Acceder al 2d0 activity y mandarle un string
                 Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                 intent.putExtra("greeter2",GREETER);
                 startActivity(intent);
             }
         });
    }
}