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
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrea = String.valueOf(nombre.getText());
                String matriculaa = String.valueOf(matricula.getText());
                String correoa = String.valueOf(correo.getText());
                String telefonoa = String.valueOf(telefono.getText());
                String contraseñaa= String.valueOf(contraseña.getText());

                if(nombrea.isEmpty() || matriculaa.isEmpty() || correoa.isEmpty() || telefonoa.isEmpty() || contraseñaa.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar datos correctamente", Toast.LENGTH_SHORT).show();
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
        mAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent intento = new Intent(getApplicationContext(), InicioSesion.class);
                            startActivity(intento);

                            String uid = mAuth.getCurrentUser().getUid();
                            DocumentReference docRef = mfirestore.collection("usuarios").document(uid);
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", uid);
                            map.put("nombre", nombre);
                            map.put("matricula", matricula);
                            map.put("correo", correo);
                            map.put("telefono", telefono);
                            map.put("contraseña", contraseña);

                            docRef.set(map);
                            Toast.makeText(getApplicationContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


}