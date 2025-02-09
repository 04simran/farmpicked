package com.example.farmpicked.ui.profile;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farmpicked.R;
import com.example.farmpicked.models.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    CircleImageView profileImg;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    EditText name, email, number, address;
    Button update;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_profile,container,false);
    profileImg = root. findViewById(R.id.profile_img);
        name = root. findViewById(R.id.profile_name);
        email = root. findViewById(R.id.profile_email);
       number = root. findViewById(R.id.profile_number);
        address = root. findViewById(R.id.profile_address);
        update = root. findViewById(R.id.update);
  auth = FirebaseAuth.getInstance();
  database = FirebaseDatabase.getInstance();
  storage = FirebaseStorage.getInstance();
  database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                  .addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          UserModel userModel = snapshot.getValue(UserModel.class);
                          Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateuserprofile();
            }
        });

        return root;


}

    private void updateuserprofile() {



    }

    @Override

    public void onActivityResult(int requestcode, int resultcode, @Nullable Intent data){
        super.onActivityResult(requestcode, resultcode, data);
        if(data.getData() != null){
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);

            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "uploaded", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());
                            Toast.makeText(getContext(), "Profile picture Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });

        }
    }

}