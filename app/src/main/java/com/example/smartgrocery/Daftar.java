package com.example.smartgrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Daftar extends AppCompatActivity {

    EditText nama, email, password;
    TextView masuk;
    String textNama, textEmail, textPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);

        mAuth = FirebaseAuth.getInstance();
        nama = findViewById(R.id.daftar_nama);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        masuk = findViewById(R.id.masuk);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

    }

    public void cekDaftar(View v) {
        textNama = nama.getText().toString();
        textEmail = email.getText().toString();
        textPass = password.getText().toString();

//        if (TextUtils.isEmpty(textNama)) {
//            Toast.makeText(getApplicationContext(), "Masukkan nama", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(textEmail)) {
//            Toast.makeText(getApplicationContext(), "Masukkan email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(textPass)) {
//            Toast.makeText(getApplicationContext(), "Masukkan Password", Toast.LENGTH_SHORT).show();
//            return;
//        }

        mAuth.createUserWithEmailAndPassword(textEmail, textPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Daftar.this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Daftar.this, "Gagal buat akun!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}