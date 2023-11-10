package com.example.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MenuDashboard extends AppCompatActivity implements RecyclerViewInterface{
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    private FirebaseFirestore mfirestore;
    Adapter adapter;
    ArrayList<Platillos> list;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dashboard);

        ImageButton casa = findViewById(R.id.imageButton1);
        ImageButton lupa = findViewById(R.id.imageButton3);
        ImageButton perfil = findViewById(R.id.imageButton2);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.platillos_lista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mfirestore = FirebaseFirestore.getInstance();
        list = new ArrayList<Platillos>();
        adapter = new Adapter(MenuDashboard.this, list, this);
        recyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            uid = mAuth.getCurrentUser().getUid();
        }
        else {
            Toast.makeText(getApplicationContext(), "Usuario no logeado", Toast.LENGTH_SHORT).show();
        }

        EventChangeListener();

        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento1 = new Intent(getApplicationContext(), MenuDashboard.class);
                startActivity(intento1);
            }
        });

        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento2 = new Intent(getApplicationContext(), Buscar_platillos.class);
                startActivity(intento2);
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

    private void EventChangeListener() {
        mfirestore.collection("Platillos").orderBy("nombre", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Log.e("Error", error.getMessage());
                    return;
                }

                for(DocumentChange dc : value.getDocumentChanges()) {
                    if(dc.getType() == DocumentChange.Type.ADDED) {
                        list.add(dc.getDocument().toObject(Platillos.class));
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void OnItemClick(int position) {
        Intent intento = new Intent(MenuDashboard.this, Informacion.class);

        intento.putExtra("nombre", list.get(position).getNombre());

        long carbohidratos = list.get(position).getCarbohidratos();
        String carbohidratosCadena = String.valueOf(carbohidratos);
        intento.putExtra("carbohidratos", carbohidratosCadena);

        long calorías = list.get(position).getCalorias();
        String caloríasCadena = String.valueOf(calorías);
        intento.putExtra("calorias", caloríasCadena);

        long grasas = list.get(position).getGrasas();
        String grasasCadena = String.valueOf(grasas);
        intento.putExtra("grasas", grasasCadena);

        long proteínas = list.get(position).getProteinas();
        String proteínasCadena = String.valueOf(proteínas);
        intento.putExtra("proteinas", proteínasCadena);

        intento.putExtra("img", list.get(position).getImg());

        startActivity(intento);
    }
}