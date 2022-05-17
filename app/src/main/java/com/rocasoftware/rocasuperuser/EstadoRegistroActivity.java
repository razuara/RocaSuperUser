package com.rocasoftware.rocasuperuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EstadoRegistroActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private Button registrarButton;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_registro);

        nombreEditText = findViewById(R.id.nombreEditText);
        registrarButton = findViewById(R.id.registrarButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                validar();
            }
        });
    }

    private void validar()
    {
        String nombre = nombreEditText.getText().toString().trim();


        if (nombre.isEmpty())
        {
            nombreEditText.setError("No puede estar vacio");
        }
        else
        {
            mDatabase.child("estados").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        Boolean existe= false;
                        for (DataSnapshot ds: snapshot.getChildren())
                        {
                            String nombreVerificar = ds.child("nombre").getValue().toString();
                            if (nombreVerificar.equals(nombre))
                            {
                                existe = true;
                            }
                        }
                        if (existe)
                        {
                            nombreEditText.setError("Ya Existe");
                        }
                        else
                        {
                            registrar(nombre);
                        }
                    }
                    else
                    {
                        registrar(nombre);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void registrar(String nombre)
    {
        registrarButton.setClickable(false);
        registrarButton.setText("Registrando...");
        Map<String,Object> map = new HashMap<>();
        map.put("nombre",nombre);

        mDatabase
                .child("estados")
                .push()
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            registrarButton.setClickable(true);
                            registrarButton.setText("Registrar");
                            Intent intent = new Intent(EstadoRegistroActivity.this,EstadosActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            registrarButton.setClickable(true);
                            registrarButton.setText("Registrar");
                            Toast.makeText(EstadoRegistroActivity.this, "Fallo en registrarse", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}