package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Informacion extends AppCompatActivity {

    private FirebaseFirestore mfirestore;
    private FirebaseAuth mAuth;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        Button back = findViewById(R.id.returnbtn);

        ImageButton casa = findViewById(R.id.btncasa);
        ImageButton perfil = findViewById(R.id.btnperfil);

        String nombre = getIntent().getStringExtra("nombre");
        String carbohidratos = getIntent().getStringExtra("carbohidratos");
        String grasas = getIntent().getStringExtra("grasas");
        String proteinas = getIntent().getStringExtra("proteinas");
        String calorias = getIntent().getStringExtra("calorias");

        TextView nombrep = findViewById(R.id.nombre);
        TextView carbohidratosp = findViewById(R.id.carbohidratos);
        TextView grasasp = findViewById(R.id.grasas);
        TextView proteinasp = findViewById(R.id.proteinas);
        TextView caloriasp = findViewById(R.id.calorias);

        nombrep.setText(nombre);
        carbohidratosp.setText("Carbohidratos: " + String.valueOf(carbohidratos));
        grasasp.setText("Grasas: " + String.valueOf(grasas));
        proteinasp.setText("Proteinas: " + String.valueOf(proteinas));
        caloriasp.setText("Calorias: " + String.valueOf(calorias));

        mfirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getCurrentUser().getUid();
        }
        else {
            Toast.makeText(getApplicationContext(), "Usuario no logeado", Toast.LENGTH_SHORT).show();
        }

        String img = getIntent().getStringExtra("img");
        if (img != null && img.length() > 0) {
            ImageView imgView = findViewById(R.id.imagen_platillo);
            Picasso.get().load(img).into(imgView);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), MenuDashboard.class);
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