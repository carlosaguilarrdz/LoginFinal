package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class crearCuenta extends AppCompatActivity {

    //Para bd
    EditText nombre, matricula, correo, telefono, contraseña;
    private FirebaseFirestore mfirestore;

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

    private void postAlumno(String nombrea, String matriculaa, String correoa, String telefonoa, String contraseñaa) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrea);
        map.put("matricula", matriculaa);
        map.put("correo", correoa);
        map.put("telefono", telefonoa);
        map.put("contraseña", contraseñaa);

        mfirestore.collection("usuarios").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
                startActivity(intento);
                Toast.makeText(getApplicationContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}