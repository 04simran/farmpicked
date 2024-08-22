package com.example.farmpicked;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.farmpicked.adaptors.MyCartAdapter;
import com.example.farmpicked.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {

    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    TextView overTotalamount;
    ProgressBar progressbar;
    Button buynow;


    public MyCartsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     View root = inflater.inflate(R.layout.fragment_my_carts, container,false);
     db = FirebaseFirestore.getInstance();
     auth = FirebaseAuth.getInstance();
     recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
     overTotalamount = root.findViewById(R.id.textView4);
        progressbar = root.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);
        buynow = root.findViewById(R.id.buy_now);


     recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
     cartModelList = new ArrayList<>();
     cartAdapter =  new MyCartAdapter(getActivity(), cartModelList);
     recyclerView.setAdapter(cartAdapter);
     db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
             .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if(task.isSuccessful()){
                         for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments() ){
                             String documentId = documentSnapshot.getId();
                            MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                            cartModel.setDocumentId(documentId);

                             cartModelList.add(cartModel);
                             cartAdapter.notifyDataSetChanged();
                             progressbar.setVisibility(View.GONE);
                             recyclerView.setVisibility(View.VISIBLE);
                         }
                         calculateTotalAmount(cartModelList);
                     }

                 }
             });

     buynow.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent = new Intent(getContext(),PlacedOrderActivity.class);
            intent.putExtra("itemList", (Serializable) cartModelList);
            startActivity(intent);
         }
     });
     return root;
    }

    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
        double totalAmount = 0.0;
        for(MyCartModel myCartModel : cartModelList){
            totalAmount += myCartModel.getTotalPrice();
        }
        overTotalamount.setText("Total Amount :" + totalAmount);
    }

}