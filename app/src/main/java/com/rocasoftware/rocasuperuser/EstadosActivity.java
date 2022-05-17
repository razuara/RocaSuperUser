package com.rocasoftware.rocasuperuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class EstadosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EstadoAdapter estadoAdapter;

    private Button registroButton;

    @Override
    protected void onStart() {
        super.onStart();
        estadoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        estadoAdapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados);

        registroButton = findViewById(R.id.registroButton);
        recyclerView = findViewById(R.id.estadosRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<EstadoModel> options =
                new FirebaseRecyclerOptions.Builder<EstadoModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("estados")
                                , EstadoModel.class)
                        .build();
        estadoAdapter = new EstadoAdapter(options);
        recyclerView.setAdapter(estadoAdapter);

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstadosActivity.this,EstadoRegistroActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EstadosActivity.this,CatalogosActivity.class);
        startActivity(intent);
        finish();
    }
}