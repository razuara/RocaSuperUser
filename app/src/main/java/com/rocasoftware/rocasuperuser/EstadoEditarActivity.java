package com.rocasoftware.rocasuperuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EstadoEditarActivity extends AppCompatActivity {

    private TextView nombreEditText;
    private Button actualizarButton,borrarButton;

    DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_editar);

        nombreEditText = findViewById(R.id.nombreEditText);
        actualizarButton = findViewById(R.id.actualizarButton);
        borrarButton = findViewById(R.id.borrarButton);

        String idEstado = getIntent().getStringExtra("idEstado");

        mDatabase = FirebaseDatabase.getInstance().getReference("estados");

        mDatabase.child(idEstado).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    nombreEditText.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                validar();
            }
        });

        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateAlertDialog();
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
            mDatabase = FirebaseDatabase.getInstance().getReference();
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
                            actualizar(nombre);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void actualizar(String nombre)
    {
        String idEstado = getIntent().getStringExtra("idEstado");
        mDatabase = FirebaseDatabase.getInstance().getReference("estados");

        mDatabase.child(idEstado).child("nombre").setValue(nombre);

        Intent intent = new Intent(EstadoEditarActivity.this,EstadosActivity.class);
        startActivity(intent);
        finish();
    }

    private void CreateAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Seguro que quieres borrar este conductor?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String idEstado = getIntent().getStringExtra("idEstado");
                mDatabase = FirebaseDatabase.getInstance().getReference("estados").child(idEstado);
                mDatabase.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(EstadoEditarActivity.this,EstadosActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(EstadoEditarActivity.this,"Proceso Cancelado",Toast.LENGTH_LONG).show();
            }
        });
        dialog.create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EstadoEditarActivity.this,EstadosActivity.class);
        startActivity(intent);
        finish();
    }
}