package com.example.seccion_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private View btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Activar flecha ir atras
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textView = (TextView) findViewById(R.id.textViewMain);
        btnNext = (Button)findViewById(R.id.buttonGoSharing);
        //tomar los datos del intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.getString("greeter") != null){
            String greeter = bundle.getString("greeter");
            Toast.makeText(SecondActivity.this,greeter,Toast.LENGTH_LONG).show();
            textView.setText(greeter);
        }else{
            Toast.makeText(SecondActivity.this,"it is empty",Toast.LENGTH_LONG).show();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this,ThirdActivity2.class);
                startActivity(intent);
            }
        });
    }
}