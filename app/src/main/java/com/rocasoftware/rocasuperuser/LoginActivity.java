package com.rocasoftware.rocasuperuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText,passwordEditText;
    Button accesoButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        accesoButton = findViewById(R.id.accesoButton);

        accesoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

            }
        });
    }

    private void validate() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Correo Invalido");
            return;
        }
        else
        {
            emailEditText.setError(null);
        }
        if (password.isEmpty() || password.length() < 8)
        {
            passwordEditText.setError("Se necesitan mas de 8 caracteres");
            return;
        }
        else if(!Pattern.compile("[0-9]").matcher(password).find())
        {
            passwordEditText.setError("Al menos un numero");
            return;
        }
        else
        {
            passwordEditText.setError(null);
        }
        iniciarSesion(email,password);
    }

    private void iniciarSesion(String email, String password)
    {
        accesoButton.setClickable(false);
        accesoButton.setText("Accesando...");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String idUser= user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference("admin");

                            mDatabase.child(idUser).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists())
                                    {
                                        String usuarioAdmin = snapshot.child("usuario").getValue().toString();

                                        if (usuarioAdmin=="true")
                                        {
                                            accesoButton.setClickable(true);
                                            accesoButton.setText("Acceso");
                                            Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
                                            intent.putExtra("idUser",idUser);
                                            intent.putExtra("usuarioAdmin",usuarioAdmin);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else
                                    {
                                        accesoButton.setClickable(true);
                                        accesoButton.setText("Acceso");
                                        Toast.makeText(LoginActivity.this,"Usuario sin permisos",Toast.LENGTH_LONG).show();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                        else
                        {
                            accesoButton.setClickable(true);
                            accesoButton.setText("Acceso");
                            Toast.makeText(LoginActivity.this,"Credenciales incorrectas",Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }

    public static String getTimeDate() { // without parameter argument
        try{
            Date netDate = new Date(); // current time from here
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            return sfd.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }

}