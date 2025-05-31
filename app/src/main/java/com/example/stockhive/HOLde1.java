package com.example.stockhive;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class HOLde1 extends RecyclerView.ViewHolder {


    TextView itemnameTV, itemquantityTV;
    Button deleteBtn;

    public HOLde1(@NonNull View itemView) {
        super(itemView);

        itemnameTV = itemView.findViewById(R.id.textview_itemname);
        itemquantityTV = itemView.findViewById(R.id.textview_itemquantity);
        deleteBtn = itemView.findViewById(R.id.btn_delete);
    }
}
