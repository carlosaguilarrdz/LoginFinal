package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

public class InicioSesion extends AppCompatActivity {


    EditText correo, contraseña;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        Button btn2 = findViewById(R.id.btn2);
        correo = findViewById(R.id.correo_login);
        contraseña = findViewById(R.id.contraseña_login);
        mAuth = FirebaseAuth.getInstance();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo_login = correo.getText().toString().trim();
                String contraseña_login = contraseña.getText().toString().trim();


                if(correo_login.isEmpty() && contraseña_login.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar datos correctamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(correo_login, contraseña_login);
                }
                Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
                startActivity(intento);
            }
        });
    }

    private void loginUser(String correoLogin, String contraseñaLogin) {
        mAuth.signInWithEmailAndPassword(correoLogin, contraseñaLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
                    startActivity(intento);
                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}