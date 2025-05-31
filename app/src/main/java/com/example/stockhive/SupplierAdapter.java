package com.example.stockhive;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.FirebaseDatabase;


public class SupplierAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<Item> itemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SupplierAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public void setData(ArrayList<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supplier_items, parent, false));
    }

    @Override

    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemnameTV.setText(item.getItemname());
        holder.itemquantityTV.setText("Quantity:" + Integer.toString(item.getItemquantity()));


        holder.btnDelete.setOnClickListener(v -> {

            new androidx.appcompat.app.AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Product Confirmation")
                    .setMessage("Are you sure you want to delete this product?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseDatabase.getInstance().getReference()
                                .child("items")
                                .child(item.getId())
                                .removeValue()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(v.getContext(), "Product deleted", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(v.getContext(), "Failed to delete product", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
                Toast.makeText(v.getContext(), "Item clicked: " + item.getItemname() + "!", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

