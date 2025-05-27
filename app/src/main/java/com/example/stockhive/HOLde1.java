package com.example.stockhive;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HOLde1 extends RecyclerView.ViewHolder {
    TextView productView, descriptionView, priceView, itemnameTV,itemquantityTV;
    ImageView imageView;
    Button cartAdd, deleteBtn;
    public HOLde1(@NonNull View itemView) {
        super(itemView);


        productView = itemView.findViewById(R.id.productname);
        imageView = itemView.findViewById(R.id.imageview);
        descriptionView = itemView.findViewById(R.id.description);
        priceView = itemView.findViewById(R.id.price);
        cartAdd = itemView.findViewById(R.id.btn_cart_add);

        itemnameTV = itemView.findViewById(R.id.textview_itemname);
        itemquantityTV = itemView.findViewById(R.id.textview_itemquantity);
        deleteBtn = itemView.findViewById(R.id.btn_delete);



    }
}
