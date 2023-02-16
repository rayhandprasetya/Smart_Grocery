package com.example.smartgrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahBarang extends AppCompatActivity {

    EditText nama_barang, harga_barang;

    Button simpan_barang;
    DatabaseReference database = FirebaseDatabase.getInstance("https://belapin-31554-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        nama_barang = findViewById(R.id.nama_barang);
        harga_barang = findViewById(R.id.harga_barang);
        simpan_barang = findViewById(R.id.simpan);

        simpan_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNamaBarang, getHargaBarang;
                getNamaBarang = nama_barang.getText().toString();
                getHargaBarang = harga_barang.getText().toString();
                if (getNamaBarang.isEmpty()) {
                    nama_barang.setError("Input nama barang!");
                }
                else if (getHargaBarang.isEmpty()) {
                    harga_barang.setError(("Input harga barang"));
                }
                else {
                    database.child("Barang").push().setValue(new Barang(getNamaBarang, getHargaBarang)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(TambahBarang.this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TambahBarang.this, mainpage.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TambahBarang.this, "Gagal menambahkan barang", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}