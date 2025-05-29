package com.example.stockhive;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    TextView name, description, price, itemnameTV,itemquantityTV;
    ImageView image;
    Button btnAdd, btnDelete;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);


        name = itemView.findViewById(R.id.product_tv_name);
        image = itemView.findViewById(R.id.product_iv_image);
        description = itemView.findViewById(R.id.product_tv_description);
        price = itemView.findViewById(R.id.product_tv_price);
        btnAdd = itemView.findViewById(R.id.product_btn_add);
        btnDelete = itemView.findViewById(R.id.btn_delete);

//        itemnameTV = itemView.findViewById(R.id.textview_itemname);
//        itemquantityTV = itemView.findViewById(R.id.textview_itemquantity);
//        deleteBtn = itemView.findViewById(R.id.btn_delete);



    }
}
