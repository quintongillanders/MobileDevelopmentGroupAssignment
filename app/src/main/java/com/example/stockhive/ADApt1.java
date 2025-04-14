package com.example.stockhive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ADApt1 extends RecyclerView.Adapter<HOLde1>{

    private int[] imageData;
    private String[] data;

    public ADApt1(int[] imageData, String[] data) {
        this.imageData = imageData;
        this.data = data;
    }

    @NonNull
    @Override
    public HOLde1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view,parent,false);
        return new HOLde1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HOLde1 holder, int position) {

        holder.tv.setText(data[position]);
        holder.imageView.setImageResource(imageData[position]);

    }

    @Override
    public int getItemCount() {
        return imageData.length;
    }
}
