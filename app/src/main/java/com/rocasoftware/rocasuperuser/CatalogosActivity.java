package com.rocasoftware.rocasuperuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class CatalogosActivity extends AppCompatActivity {
    CardView sexoCardView,estadosCardView,ciudadesCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogos);

        sexoCardView = findViewById(R.id.sexoCardView);
        estadosCardView = findViewById(R.id.estadosCardView);



        sexoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogosActivity.this,SexoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        estadosCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogosActivity.this,EstadosActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CatalogosActivity.this,PrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}