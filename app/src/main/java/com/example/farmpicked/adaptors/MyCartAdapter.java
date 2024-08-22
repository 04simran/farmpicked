package com.example.farmpicked.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmpicked.R;
import com.example.farmpicked.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

FirebaseFirestore firestore;
FirebaseAuth auth;
    Context context;
     List<MyCartModel> cardModellist;
      int totalPrice = 0;

    public MyCartAdapter(Context context, List<MyCartModel> cardModellist) {
        this.context = context;
        this.cardModellist = cardModellist;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(cardModellist.get(position).getProductName());
       holder.price.setText((cardModellist.get(position).getProductPrice()));
        holder.time.setText(cardModellist.get(position).getCurrentTime());
        holder.date.setText(cardModellist.get(position).getCurrentDate());
        holder.quantity.setText(cardModellist.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cardModellist.get(position).getTotalPrice()));
holder.deleteItem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart")
                .document(cardModellist.get(position).getDocumentId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        cardModellist.remove(cardModellist.get(position));
                        notifyDataSetChanged();
                            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
});


    }

    @Override
    public int getItemCount() {
        return cardModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price,date, time, quantity,totalPrice;
      ImageView deleteItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity= itemView.findViewById(R.id.total_quantity);
           totalPrice = itemView.findViewById(R.id.total_price);
           deleteItem = itemView.findViewById(R.id.delete);
        }
    }
}
