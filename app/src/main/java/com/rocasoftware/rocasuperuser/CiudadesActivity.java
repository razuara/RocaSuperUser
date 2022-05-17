package com.rocasoftware.rocasuperuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CiudadesActivity extends AppCompatActivity {

    Spinner estadosSpinner;

    String estadosSeleccionado;

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudades);

        estadosSpinner = findViewById(R.id.estadosSpinner);

        mAuth= FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cargarEstados();
    }

    private void cargarEstados()
    {
        final List<EstadoModel> estados = new ArrayList<>();

        mDatabase.child("estados").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot ds: snapshot.getChildren())
                    {
                        String id = ds.getKey();
                        String nombre = ds.child("nombre").getValue().toString();


                        estados.add(new EstadoModel(id,nombre));
                    }

                    ArrayAdapter<EstadoModel> arrayAdapter = new ArrayAdapter<>(CiudadesActivity.this, android.R.layout.simple_dropdown_item_1line,estados);
                    estadosSpinner.setAdapter(arrayAdapter);
                    estadosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            estadosSeleccionado = adapterView.getItemAtPosition(position).toString();
                            //textViewData.setText(vehiculoSeleccionado);
                            String idEstado = estados.get(position).getId();
                            //aqui va la parte cuando selecciona el estado    textViewData.setText("El id es : "+ idVehiculo);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CiudadesActivity.this,CatalogosActivity.class);
        startActivity(intent);
        finish();
    }
}