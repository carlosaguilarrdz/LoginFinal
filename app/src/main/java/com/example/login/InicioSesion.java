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
        Button back = findViewById(R.id.button);

        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);
        mAuth = FirebaseAuth.getInstance();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String correo_login = String.valueOf(correo.getText());
                String contraseña_login =   String.valueOf(contraseña.getText());


                if(correo_login.isEmpty() || contraseña_login.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar datos correctamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(correo_login, contraseña_login);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento2 = new Intent(getApplicationContext(), LoginLayout.class);
                startActivity(intento2);
            }
        });
    }

    private void loginUser(String correoLogin, String contraseñaLogin) {
        mAuth.signInWithEmailAndPassword(correoLogin, contraseñaLogin)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Login con éxito", Toast.LENGTH_SHORT).show();
                            Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
                            startActivity(intento);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login sin éxito", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}