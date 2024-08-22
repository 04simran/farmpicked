package com.example.farmpicked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.farmpicked.models.ViewallModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg, addItem,removeItem;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextView price, rating, description,quantity;
    int totalQuantity = 1;
    int totalPrice = 0;
    Button addtocart;
    Toolbar toolbar;
    ViewallModel viewallModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        toolbar = findViewById( R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ViewallModel){
            viewallModel = (ViewallModel) object;
        }

        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_dec);
        addtocart = findViewById(R.id.add_to_cart);
        quantity = findViewById(R.id.quantity);

        if(viewallModel != null){
            Glide.with(getApplicationContext()).load(viewallModel.getImg_url()).into(detailedImg);
            rating.setText(viewallModel.getRating());
            price.setText("Price :Rs"+viewallModel.getPrice()+"/kg");
            description.setText(viewallModel.getDescription());
            totalPrice = viewallModel.getPrice() * totalQuantity;
        }
        if(viewallModel.getType().equals("egg")){
            price.setText("Price :Rs"+viewallModel.getPrice()+"/dozen");
            totalPrice = viewallModel.getPrice() * totalQuantity;
        }

        if(viewallModel.getType().equals("milk")){
            price.setText("Price :Rs"+viewallModel.getPrice()+"/litre");
            totalPrice = viewallModel.getPrice() * totalQuantity;
        }

    addItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(totalQuantity < 10){
                totalQuantity++;
                quantity.setText(String.valueOf(totalQuantity));
                totalPrice = viewallModel.getPrice() * totalQuantity;
            }
            
        }
    });
        
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewallModel.getPrice() * totalQuantity;
                }


            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addedtocart();
            }
        });
    }

    private void addedtocart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", viewallModel.getName());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime ",saveCurrentTime);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);


        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
    }


}