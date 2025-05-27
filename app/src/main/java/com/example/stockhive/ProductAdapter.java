package com.example.stockhive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<Product> products;
    private ArrayList<Product> productsBackup;
    private final Context context;
    private Product product;



    public ProductAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
        this.productsBackup = products;


    }



    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        product = products.get(position);
        holder.name.setText("Product Name:" + product.getName());
        holder.description.setText("Model: " + product.getDescription());
        holder.image.setImageResource(product.getImage().src);
        holder.price.setText("Price: $" + String.format("%.2f", product.getPrice()));



        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    product = products.get(currentPosition);
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("imageResource", product.getImage().src);
                    intent.putExtra("productName", product.getName());
                    intent.putExtra("Description", product.getDescription());
                    intent.putExtra("price", product.getPrice());
                    context.startActivity(intent);


                }
            }
        });



        CartItem cartItem = new CartItem(product);
        holder.btnAdd.setOnClickListener(v -> {
            ((Customer) context).addToCart(cartItem);
        });



    }



    @Override
    public int getItemCount() {

        return products.size();

    }

    public void filter(String text) {

        if (text == null || text.trim().isEmpty()) {
            resetData();
            return;
        }

        final String query = text.toLowerCase();

        new Thread(() -> {
            try {

                int count = productsBackup.size();

                ArrayList<Product> filteredProducts = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    if (productsBackup.get(i).getName().toLowerCase().contains(query)) {
                        filteredProducts.add(productsBackup.get(i));
                    }
                }
                for (int i = 0; i < count; i++) {
                    if (!filteredProducts.contains(productsBackup.get(i))) {
                        if (productsBackup.get(i).getDescription().toLowerCase().contains(query)) {
                            filteredProducts.add(productsBackup.get(i));
                        }
                        }
                    }



                if (context instanceof Activity) {
                    ((Activity) context).runOnUiThread(() -> {
                        products = new ArrayList<Product>(filteredProducts.size());

                        products.addAll(filteredProducts);

                        notifyDataSetChanged();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void resetData() {

        products = productsBackup;

        notifyDataSetChanged();
    }
}
