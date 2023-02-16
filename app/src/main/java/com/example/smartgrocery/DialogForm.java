package com.example.smartgrocery;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment {
    String namaBrg, hargaBrg, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance("https://belapin-31554-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    public DialogForm(String namaBrg, String hargaBrg, String key, String pilih) {
        this.namaBrg = namaBrg;
        this.hargaBrg = hargaBrg;
        this.key = key;
        this.pilih = pilih;
    }

    TextView brgnama, brgharga;
    Button smpn_tmbl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tambah, container, false);
        brgnama = view.findViewById(R.id.nama_barang);
        brgharga = view.findViewById(R.id.harga_barang);
        smpn_tmbl = view.findViewById(R.id.simpan);

        brgnama.setText(namaBrg);
        brgharga.setText(hargaBrg);

        smpn_tmbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ngambil data di form
                String barangnama = brgnama.getText().toString();
                String barangharga = brgharga.getText().toString();

                if(pilih.equals("Ubah")) {
                    // Ketika pilih "ubah", data di database akan di update
                    // Database akan di update berdasarkan key atau card yang kita pilih
                    database.child("Barang").child(key).setValue(new Barang(barangnama, barangharga)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Barang berhasil diedit", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Barang gagal edit!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
