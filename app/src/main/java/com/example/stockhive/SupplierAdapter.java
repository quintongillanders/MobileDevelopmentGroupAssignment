package com.example.stockhive;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.FirebaseDatabase;

public class SupplierAdapter extends RecyclerView.Adapter<HOLde1> {

    private ArrayList<Item> itemList;

    public SupplierAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public void setData(ArrayList<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HOLde1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLde1(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supplier_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HOLde1 holder, int position) {
        Item item = itemList.get(position);
        holder.itemnameTV.setText(item.getItemname());
        holder.itemquantityTV.setText("Quantity:" +Integer.toString(item.getItemquantity()));


        holder.deleteBtn.setOnClickListener(v -> {

            FirebaseDatabase.getInstance().getReference()
                    .child("items")
                    .child(item.getId())
                    .removeValue();
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }




}
