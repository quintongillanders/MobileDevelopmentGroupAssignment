package com.example.stockhive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> emails;
    private final ArrayList<String> uids;

    public UserAdapter(Context context, ArrayList<String> emails, ArrayList<String> uids) {
        super(context, R.layout.user_list_item, emails);
        this.context = context;
        this.emails = emails;
        this.uids = uids;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.user_list_item, parent, false);

        }

        TextView userEmail = view.findViewById(R.id.userEmail);
        Button btnReset = view.findViewById(R.id.btnResetPass);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        String email = emails.get(position);
        String uid = uids.get(position);

        userEmail.setText(email);

        btnReset.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Reset Password")
                    .setMessage("Send reset password email to " + email + "?")
                    .setPositiveButton("Send", (dialog, which) -> {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(context, "Failed to send reset email, please try again later", Toast.LENGTH_SHORT).show()
                                );
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        btnDelete.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete " + email + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        FirebaseFirestore.getInstance().collection("Users").document(uid)
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(context, "User successfully deleted", Toast.LENGTH_SHORT).show();
                                    emails.remove(position);
                                    uids.remove(position);
                                    notifyDataSetChanged();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(context, "Failed to delete user, please try again later", Toast.LENGTH_SHORT).show()
                                );
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
        return view;
    }
}








