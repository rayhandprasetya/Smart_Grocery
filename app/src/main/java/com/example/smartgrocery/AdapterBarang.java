package com.example.smartgrocery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.MyViewHolder> {

    public List<Barang> myList;
    public Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance("https://belapin-31554-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    public AdapterBarang(List<Barang>myList, Activity activity) {
        this.myList = myList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewBarang = inflater.inflate(R.layout.items_add, parent, false);
        return new MyViewHolder(viewBarang);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Barang data = myList.get(position);
        holder.namabarang.setText("Barang: " + data.getNamaBarang());
        holder.hargabarang.setText("Harga: " + data.getHargaBarang());

        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Membuat alert untuk menampilkan pesan
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                // Pesan ketika user ingin hapus data
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.child("Barang").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Data gagal dihapus!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    // Pesan ketika user tidak ingin hapus data
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Tidak jadi menghapus
                        dialogInterface.dismiss();
                    }

                    //Set pesan
                }).setMessage("Hapus barang?" + data.getNamaBarang());
                // Menampilkan pesan
                builder.show();
            }
        });

        // Membuat card bisa dipencet lama agar memproses data
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // Menjalankan fragment manager
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();

                // Mengirim data ke Dialog Form
                DialogForm dialog = new DialogForm(
                        data.getNamaBarang(),
                        data.getHargaBarang(),
                        data.getKey(),
                        "Ubah"
                );

                // Menampilkan dialog
                dialog.show(manager, "form");

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namabarang, hargabarang;
        ImageView hapus;
        CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namabarang = itemView.findViewById(R.id.namabarang);
            hargabarang = itemView.findViewById(R.id.hargabarang);
            card = itemView.findViewById(R.id.card);
            hapus = itemView.findViewById(R.id.hapus);
        }
    }
}
