package com.example.farmpicked.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmpicked.R;
import com.example.farmpicked.models.NavCategoryDetailedModel;

import java.util.List;

public class NavCategoryDetailedAdaptor extends RecyclerView.Adapter<NavCategoryDetailedAdaptor.ViewHolder> {
    Context context;
    List<NavCategoryDetailedModel> list;

    public NavCategoryDetailedAdaptor(Context context, List<NavCategoryDetailedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NavCategoryDetailedAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detailed_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavCategoryDetailedAdaptor.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.nav_cat_detail_img);
            name = itemView.findViewById(R.id.nav_cat_detail_name);
            price = itemView.findViewById(R.id.price);

        }
    }
}
