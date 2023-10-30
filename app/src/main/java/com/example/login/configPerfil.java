package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class configPerfil extends AppCompatActivity {

    private FirebaseFirestore mfirestore;
    private FirebaseAuth auth;
    EditText nombre, matricula, contraseña, telefono, correo;

    DocumentReference userRef = mfirestore.collection("usuarios").document(auth.getCurrentUser().getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_perfil);

        Button guardar = findViewById(R.id.saveBtn);

        nombre = findViewById(R.id.nombre);
        matricula = findViewById(R.id.matricula);
        correo = findViewById(R.id.correo);
        telefono = findViewById(R.id.telefono);
        contraseña = findViewById(R.id.contraseña);

        mfirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Obtén el nombre del usuario
        DocumentSnapshot docSnapshot = userRef.get().getResult();
        // Obtén el nombre del usuario
        String nombrea = docSnapshot.getData().get("nombre").toString();
        String matriculaa = docSnapshot.getData().get("matricula").toString();
        String correoa = docSnapshot.getData().get("correo").toString();
        String telefonoa = docSnapshot.getData().get("telefono").toString();
        String contraseñaa = docSnapshot.getData().get("contraseña").toString();

        nombre.setText(nombrea);
        matricula.setText(matriculaa);
        correo.setText(correoa);
        telefono.setText(telefonoa);
        contraseña.setText(contraseñaa);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrea = nombre.getText().toString().trim();
                String matriculaa = matricula.getText().toString().trim();
                String correoa = correo.getText().toString().trim();
                String telefonoa = telefono.getText().toString().trim();
                String contraseñaa= contraseña.getText().toString().trim();

                modificarUsuario(nombrea, matriculaa, correoa, telefonoa, contraseñaa);
            }
        });
    }

    private void modificarUsuario(String nombrea, String matriculaa, String correoa, String telefonoa, String contraseñaa) {
        // Actualiza el documento del usuario
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrea);
        map.put("matricula", matriculaa);
        map.put("correo", correoa);
        map.put("telefono", telefonoa);
        map.put("contraseña", contraseñaa);

        userRef.update(map);

        Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
        startActivity(intento);

    }

}