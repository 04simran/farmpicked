package com.example.farmpicked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.farmpicked.adaptors.ViewallAdapter;
import com.example.farmpicked.models.ViewallModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity2 extends AppCompatActivity {
    FirebaseFirestore firestore;
   List<ViewallModel> viewallModelList;
   ViewallAdapter viewallAdapter;
   RecyclerView recyclerView;
   ProgressBar progressbar;
   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all2);
        firestore = FirebaseFirestore.getInstance();
        toolbar = findViewById( R.id.toolbar);
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewallModelList = new ArrayList<>();
        viewallAdapter = new ViewallAdapter(this, viewallModelList);
        recyclerView.setAdapter(viewallAdapter);

        //getting Fruits
        if(type != null && type.equalsIgnoreCase("fruit")) {
            firestore.collection("AllProducts").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        ViewallModel viewallModel = documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                        progressbar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                }
            });
        }
            //getting vegetable
            if(type != null && type.equalsIgnoreCase("vegetable")){
                firestore.collection("AllProducts").whereEqualTo("type", "vegetable").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                            ViewallModel viewallModel = documentSnapshot.toObject(ViewallModel.class);
                            viewallModelList.add(viewallModel);
                            viewallAdapter.notifyDataSetChanged();
                            progressbar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                        }
                    }
                });
            }
        if(type != null && type.equalsIgnoreCase("fish")){
            firestore.collection("AllProducts").whereEqualTo("type", "fish").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewallModel viewallModel = documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                        progressbar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                }
            });
        }
        if(type != null && type.equalsIgnoreCase("milk")){
            firestore.collection("AllProducts").whereEqualTo("type", "milk").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewallModel viewallModel = documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                        progressbar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                }
            });
        }
        if(type != null && type.equalsIgnoreCase("egg")){
            firestore.collection("AllProducts").whereEqualTo("type", "egg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewallModel viewallModel = documentSnapshot.toObject(ViewallModel.class);
                        viewallModelList.add(viewallModel);
                        viewallAdapter.notifyDataSetChanged();
                        progressbar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                }
            });
        }

        }
}

