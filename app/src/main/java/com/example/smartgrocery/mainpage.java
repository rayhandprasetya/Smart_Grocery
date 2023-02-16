package com.example.smartgrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mainpage extends AppCompatActivity {

    FloatingActionButton tombol_add;
    AdapterBarang adapterBarang;
    DatabaseReference database = FirebaseDatabase.getInstance("https://belapin-31554-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    ArrayList<Barang> listBarang;
    RecyclerView tampilan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        tombol_add = findViewById(R.id.tambah);

        tombol_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpage.this, TambahBarang.class);
                startActivity(intent);
            }
        });

        tampilan = findViewById(R.id.tampilan);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        tampilan.setLayoutManager(mLayout);
        tampilan.setItemAnimator(new DefaultItemAnimator());

        tampilData();
    }

    private void tampilData() {
        database.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBarang = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Barang brg = item.getValue(Barang.class);
                    brg.setKey(item.getKey());
                    listBarang.add(brg);
                }

                adapterBarang = new AdapterBarang(listBarang, mainpage.this);
                tampilan.setAdapter(adapterBarang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}