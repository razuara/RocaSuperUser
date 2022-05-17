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

public class SexoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SexoAdapter sexoAdapter;

    private Button registroButton;

    @Override
    protected void onStart() {
        super.onStart();
        sexoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sexoAdapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexo);

        registroButton = findViewById(R.id.registroButton);
        recyclerView = findViewById(R.id.sexoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<SexoModel> options =
                new FirebaseRecyclerOptions.Builder<SexoModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("sexo")
                                , SexoModel.class)
                        .build();
        sexoAdapter = new SexoAdapter(options);
        recyclerView.setAdapter(sexoAdapter);





        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SexoActivity.this,SexoRegistroActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SexoActivity.this,CatalogosActivity.class);
        startActivity(intent);
        finish();
    }
}