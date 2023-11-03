package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Informacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        Button back = findViewById(R.id.button);

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

    }
}