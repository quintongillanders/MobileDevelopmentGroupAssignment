package com.example.stockhive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<String> emails;
    ArrayList<String> uids;

    public UserAdapter(Context context, ArrayList<String> emails, ArrayList<String> uids) {
        this.context = context;
        this.emails = emails;
        this.uids = uids;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String email = emails.get(position);
        String uid = uids.get(position);

        holder.userEmail.setText(email);

        holder.btnEdit.setOnClickListener(v ->
                Toast.makeText(context, "Edit clicked for: " + email, Toast.LENGTH_SHORT).show()
        );

        holder.btnDelete.setOnClickListener(v -> {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            userRef.removeValue()
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "User Deleted", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                    );
        });

        holder.itemView.setOnClickListener(v -> {
            DatabaseReference enabledRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("enabled");
            enabledRef.get().addOnSuccessListener(snapshot -> {
                Boolean current = snapshot.getValue(Boolean.class);
                if (current != null) {
                    boolean newState = !current;
                    enabledRef.setValue(newState);
                    Toast.makeText(context, "User enabled set to: " + newState, Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.itemView.setOnLongClickListener(v -> {
            FirebaseDatabase.getInstance().getReference("Users").child(uid).child("email").get()
                    .addOnSuccessListener(snap -> {
                        String userEmail = snap.getValue(String.class);
                        if (userEmail != null) {
                            FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "Reset password email sent to: " + userEmail, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Failed to send reset password email", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userEmail;
        Button btnEdit, btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.userEmail);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
