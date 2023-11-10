package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ConfigPerfil extends AppCompatActivity {

    private FirebaseFirestore mfirestore;
    private FirebaseAuth mAuth;
    EditText nombre, matricula, telefono;
    String nombrea, matriculaa, telefonoa, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_perfil);

        Button guardar = findViewById(R.id.saveBtn);
        Button borrar = findViewById(R.id.deleteBtn);

        nombre = findViewById(R.id.nombre);
        matricula = findViewById(R.id.matricula);
        telefono = findViewById(R.id.telefono);

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

                        nombre.setText(nombrea);
                        matricula.setText(matriculaa);
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

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrea = nombre.getText().toString().trim();
                String matriculaa = matricula.getText().toString().trim();
                String telefonoa = telefono.getText().toString().trim();

                modificarUsuario(nombrea, matriculaa,telefonoa);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Muestra un mensaje de advertencia
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfigPerfil.this);
                builder.setTitle("Eliminar cuenta");
                builder.setMessage("¿Estás seguro de que deseas eliminar tu cuenta?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
                        startActivity(intento);
                    }
                });

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mfirestore.collection("usuarios").document(uid).delete();

                        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    // Muestra un mensaje de confirmación
                                    Toast.makeText(getApplicationContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();

                                    // Regresa a la actividad de inicio de sesión
                                    Intent intento = new Intent(getApplicationContext(), LoginLayout.class);
                                    startActivity(intento);
                                }
                            }
                        });


                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void modificarUsuario(String nombrea, String matriculaa, String telefonoa) {
        DocumentReference docRef = mfirestore.collection("usuarios").document(uid);
        // Actualiza el documento del usuario
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrea);
        map.put("matricula", matriculaa);
        map.put("telefono", telefonoa);

        docRef.update(map);

        Toast.makeText(getApplicationContext(), "Usuario modificado", Toast.LENGTH_SHORT).show();
        Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
        startActivity(intento);
    }


}