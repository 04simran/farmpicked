package com.example.farmpicked.ui.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.farmpicked.R;
import com.example.farmpicked.adaptors.NavcategoryAdapter;
import com.example.farmpicked.adaptors.PopularAdapters;
import com.example.farmpicked.models.NavCategoryModel;
import com.example.farmpicked.models.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
  FirebaseFirestore db;
    List<NavCategoryModel> categoryModelList;
    RecyclerView recyclerView;
    NavcategoryAdapter navcategoryAdapter;
    ProgressBar progressbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         db = FirebaseFirestore.getInstance();

        View root = inflater.inflate(R.layout.fragment_category,container,false);
        //nav Category
        recyclerView = root.findViewById(R.id.cat_rec);
        recyclerView.setVisibility(View.GONE);
        progressbar = root.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false));
        categoryModelList =new ArrayList<>();
        navcategoryAdapter = new NavcategoryAdapter(getActivity(),categoryModelList);
        recyclerView.setAdapter(navcategoryAdapter);
        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCategoryModel navCategoryModel = document.toObject(NavCategoryModel.class);
                                categoryModelList.add(navCategoryModel);
                                navcategoryAdapter.notifyDataSetChanged();
                                progressbar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;


    }
}