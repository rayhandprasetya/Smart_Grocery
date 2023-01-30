package com.example.smartgrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText email, password;
    Button loginbut;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginbut = findViewById(R.id.login_button);
        register = findViewById(R.id.daftar);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), daftar.class);
                startActivity(intent);
            }
        });

        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();
                if(email.getText().toString().equals("admin") && password.getText().toString().equals("fuyuk")) {
                    Intent intent = new Intent(getApplicationContext(), mainpage.class);
                    startActivity(intent);
                }
                else if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email atau Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Email atau Password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}