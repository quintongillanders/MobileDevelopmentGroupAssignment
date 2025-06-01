package com.example.stockhive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;
    private OnRemoveClickListener removeClickListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(int position);
    }

    public CartAdapter(ArrayList<CartItem> cartItems, OnRemoveClickListener listener) {
        this.cartItems = cartItems;
        this.removeClickListener = listener;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView ivProductImage;
        Button btnRemove;

        public CartViewHolder(View itemView, final OnRemoveClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.product_tv_name);
            tvPrice = itemView.findViewById(R.id.product_tv_price);
            ivProductImage = itemView.findViewById(R.id.product_iv_image);
            btnRemove = itemView.findViewById(R.id.product_btn_remove);

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onRemoveClick(pos);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, parent, false);
        return new CartViewHolder(view, removeClickListener);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText("Price: $" + String.format("%.2f", item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
