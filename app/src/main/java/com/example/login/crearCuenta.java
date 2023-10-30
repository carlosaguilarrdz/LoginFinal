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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class crearCuenta extends AppCompatActivity {

    //Para bd
    EditText nombre, matricula, correo, telefono, contraseña;
    private FirebaseFirestore mfirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        Button btn2 = findViewById(R.id.btn2);
        Button back = findViewById(R.id.button);

        nombre = findViewById(R.id.nombre);
        matricula = findViewById(R.id.matricula);
        correo = findViewById(R.id.correo);
        telefono = findViewById(R.id.telefono);
        contraseña = findViewById(R.id.contraseña);

        mfirestore = FirebaseFirestore.getInstance();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrea = nombre.getText().toString().trim();
                String matriculaa = matricula.getText().toString().trim();
                String correoa = correo.getText().toString().trim();
                String telefonoa = telefono.getText().toString().trim();
                String contraseñaa= contraseña.getText().toString().trim();

                if(nombrea.isEmpty() || matriculaa.isEmpty() || correoa.isEmpty() || telefonoa.isEmpty() || contraseñaa.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar datos correctamentee", Toast.LENGTH_SHORT).show();
                } else {
                    postAlumno(nombrea, matriculaa, correoa, telefonoa, contraseñaa);
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

    private void postAlumno(String nombre, String matricula, String correo, String telefono, String contraseña) {
        mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("nombre", nombre);
                map.put("matricula", matricula);
                map.put("correo", correo);
                map.put("telefono", telefono);
                map.put("contraseña", contraseña);

                mfirestore.collection("usuarios").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
                        startActivity(intento);
                        Toast.makeText(getApplicationContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }


}