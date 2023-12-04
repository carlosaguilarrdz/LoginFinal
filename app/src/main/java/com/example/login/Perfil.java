package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    TextView nombre, matricula, telefono, correo;
    String nombrea, matriculaa, telefonoa, correoa,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button config = findViewById(R.id.configBtn);
        Button cerrar = findViewById(R.id.exitBtn);
        Button back = findViewById(R.id.returnbtn);

        ImageButton casa = findViewById(R.id.btncasa);
        ImageButton perfil = findViewById(R.id.btnperfil);

        nombre = findViewById(R.id.nombre);
        matricula = findViewById(R.id.matricula);
        correo = findViewById(R.id.correo);
        telefono = findViewById(R.id.telefono);


        mfirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getCurrentUser().getUid();
        }
        else {
            Toast.makeText(getApplicationContext(), "Usuario no logeado", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
                startActivity(intento);
            }
        });

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

                        nombre.setText(nombrea);
                        matricula.setText(matriculaa);
                        correo.setText(correoa);
                        telefono.setText(telefonoa);
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

        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento1 = new Intent(getApplicationContext(), MenuDashboard.class);
                startActivity(intento1);
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifica que el usuario esté autenticado
                if (mAuth.getCurrentUser() != null) {
                    // Crea un intent a la actividad perfil
                    Intent intento3 = new Intent(getApplicationContext(), Perfil.class);

                    // Inicia la actividad perfil
                    startActivity(intento3);
                } else {
                    // El usuario no está autenticado
                    // Muestra un mensaje de error
                    Toast.makeText(getApplicationContext(), "Debes iniciar sesión para acceder a este contenido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}