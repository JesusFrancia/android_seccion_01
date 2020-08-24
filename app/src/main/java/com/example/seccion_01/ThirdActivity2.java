package com.example.seccion_01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity2 extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imgBtnPhone;
    private ImageButton imgBtnWeb;
    private ImageButton imgBtnCamara;

    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //Activar flecha ir atras
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imgBtnPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imgBtnWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imgBtnCamara = (ImageButton) findViewById(R.id.imageButtonCamara);

        //boton para la llamada
        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editTextPhone.getText().toString();
                if(phoneNumber != null && !phoneNumber.isEmpty()){
                    //comprobar version actual de android que estamos corriendo
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        //comprobar si se ha aceptado, no se ha aceptado o nunca se ha preguntado
                        if(CheckPermission(Manifest.permission.CALL_PHONE)){
                            //ha aceptado
                            Intent i = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phoneNumber));
                            if(ActivityCompat.checkSelfPermission(ThirdActivity2.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                            startActivity(i);
                        }else{
                            //ha denegado o es primera vez que se pregunta
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                //no se le ha preguntado
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_CODE);
                            }else{
                                //ha denegado
                                Toast.makeText(ThirdActivity2.this, "please enable the request permission", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }

                        //requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_CODE);
                    }else{
                        OlderVersions(phoneNumber);
                    }
                }else{
                    Toast.makeText(ThirdActivity2.this,"you declined the access",Toast.LENGTH_LONG).show();
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumber));
                if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity2.this, "uou declined the access", Toast.LENGTH_LONG).show();
                }
            }

        });

        //boton para la direccion web
        imgBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextWeb.getText().toString();
                String email = "jesusantfc.24@gmail.com";
                if(url != null && !url.isEmpty()){
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://" + url));

                    //Contactos
                    Intent intentContacts =  new Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people"));
                    //Email rapido
                    Intent intentEmailTo =  new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:" + email));
                    //Email completo
                    //Intent intentEmail =  new Intent(Intent.ACTION_VIEW,Uri.parse(email));
                    Intent intentEmail =  new Intent(Intent.ACTION_SEND,Uri.parse(email));
                    //intentEmail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                    //intentEmail.setType("plain/text");
                    intentEmail.setType("message/rfc822");
                    intentEmail.putExtra(Intent.EXTRA_SUBJECT,"Mail´s title");
                    intentEmail.putExtra(Intent.EXTRA_TEXT, "Hi my friend");
                    intentEmail.putExtra(Intent.EXTRA_EMAIL,new String[]{"joelFrancia@gmail.com","brandonFrancia@gmail.com"});
                    //startActivity(Intent.createChooser(intentEmail,"elige cliente de correo"));

                    //Telefono 2
                    Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:666111222"));


                    //startActivity(intentEmail);
                }
            }
        });

        imgBtnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir Camara
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera, PICTURE_FROM_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){
            case PICTURE_FROM_CAMERA:
                if(resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(this,"Resultado ok " + result,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Resultado error",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //estamos en el caso del telefono
        switch (requestCode){
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result = grantResults[0];
                if(permission.equals(Manifest.permission.CALL_PHONE)){
                    //Comprobar si ha sido aceptado o denegado la peticion de permiso
                    if(result == PackageManager.PERMISSION_GRANTED){
                        //concedio su permiso
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phoneNumber));
                        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                        startActivity(intentCall);
                    }else{
                        //no concedio permiso
                        Toast.makeText(ThirdActivity2.this,"you devlined the access",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean CheckPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}