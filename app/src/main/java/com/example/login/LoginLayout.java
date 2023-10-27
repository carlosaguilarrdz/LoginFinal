package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        Button btnRegistrar = findViewById(R.id.btn1);
        Button btnIniciarSes = findViewById(R.id.btn2);

        btnIniciarSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentoSesion = new Intent(getApplicationContext(), InicioSesion.class);
                startActivity(intentoSesion);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentoRegistro = new Intent(getApplicationContext(), crearCuenta.class);
                startActivity(intentoRegistro);
            }
        });
    }
}