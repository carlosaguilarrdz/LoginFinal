package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Perfil extends AppCompatActivity {

    private FirebaseFirestore mfirestore;
    private FirebaseAuth mAuth;
    TextView nombre, matricula, telefono, correo, contraseña;
    String nombrea, matriculaa, telefonoa, correoa, contraseñaa,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button config = findViewById(R.id.configBtn);
        Button cerrar = findViewById(R.id.exitBtn);

        nombre = findViewById(R.id.nombre);
        matricula = findViewById(R.id.matricula);
        correo = findViewById(R.id.correo);
        telefono = findViewById(R.id.telefono);
        contraseña = findViewById(R.id.contraseña);

        mfirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getCurrentUser().getUid();
        }
        else {
            Toast.makeText(getApplicationContext(), "Usuario no logeado", Toast.LENGTH_SHORT).show();
        }

        mfirestore.collection("usuarios").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot != null && documentSnapshot.exists()) {
                        nombrea = documentSnapshot.getString("nombre");
                        matriculaa = documentSnapshot.getString("matricula");
                        telefonoa = documentSnapshot.getString("telefono");
                        correoa = documentSnapshot.getString("correo");
                        contraseñaa = documentSnapshot.getString("contraseña");

                        nombre.setText(nombrea);
                        matricula.setText(matriculaa);
                        correo.setText(correoa);
                        telefono.setText(telefonoa);
                        contraseña.setText(contraseñaa);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), ConfigPerfil.class);
                startActivity(intento);
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intento = new Intent(getApplicationContext(), LoginLayout.class);
                startActivity(intento);
            }
        });
    }
}