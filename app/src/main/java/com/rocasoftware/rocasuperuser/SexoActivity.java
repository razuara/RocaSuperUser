package com.rocasoftware.rocasuperuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SexoActivity extends AppCompatActivity {


    private Button registroButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexo);

        registroButton = findViewById(R.id.registroButton);


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