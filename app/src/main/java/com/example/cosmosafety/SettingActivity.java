package com.example.cosmosafety;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmosafety.Model.CurrentInfo;
import com.example.cosmosafety.Model.HarmfulCategory;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    private TextView closeSetting, updateSetting, changeProfile, addCategory;
    private ListView catLstView;
    private EditText username;
    private CircleImageView profilePic;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicRef;
    private String checker = "";

    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        List<String> list;
        List<Integer> imgLst;
        MyAdapter(Context c, List<String> l, List<Integer> imgLst)
        {
            super(c, R.layout.cat_row, R.id.tv1_cr,l);

            this.context = c;
            list = new ArrayList<>();
            this.imgLst = new ArrayList<>();
            list.addAll(l);
            this.imgLst.addAll(imgLst);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cat_row, parent,false);

            TextView myTitle = row.findViewById(R.id.tv1_cr);
            ImageView imageView = row.findViewById(R.id.img_cr);

            imageView.setImageResource(imgLst.get(position));

            myTitle.setText(list.get(position));

            return row;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(CurrentInfo.currentUser.getUsername()).child("UsingList");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {
                    List<String> catLst = new ArrayList<>();
                    List<Integer> imgLst = new ArrayList<>();

                    for(DataSnapshot cat: dataSnapshot.getChildren())
                    {
                        HarmfulCategory hc = cat.getValue(HarmfulCategory.class);
                        catLst.add(hc.getName());
                        imgLst.add(hc.getIcon());
                    }
                    MyAdapter adapter = new MyAdapter(SettingActivity.this, catLst,imgLst);
                    catLstView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        addCategory = (TextView)findViewById(R.id.add_btn_pp);
        catLstView = (ListView)findViewById(R.id.using_product_lst_pp);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ProductUsedActivity.class);
                startActivity(intent);
            }
        });

        storageProfilePicRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        closeSetting = (TextView) findViewById(R.id.close_setting);
        updateSetting = (TextView) findViewById(R.id.update_setting);
        changeProfile = (TextView) findViewById(R.id.change_profile_setting);
        profilePic = (CircleImageView) findViewById(R.id.profile_image_setting);
        username = (EditText) findViewById(R.id.username_setting);

        displayUserInfo(profilePic, username);

        closeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked"))
                {
                    userInfoSaved();
                }
            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri).setAspectRatio(1,1).start(SettingActivity.this);
            }
        });
    }

    private void userInfoSaved()
    {
        if(TextUtils.isEmpty(username.getText().toString()))
        {
            Toast.makeText(SettingActivity.this, "You must fill User Name", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }

    private void uploadImage() {
        if(imageUri!= null)
        {
            final StorageReference fileRef = storageProfilePicRef.child(CurrentInfo.currentUser.getUsername()+".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUrl = (Uri) task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("username", username.getText().toString());
                        userMap.put("image",myUrl);
                        ref.child(CurrentInfo.currentUser.getUsername()).updateChildren(userMap);

                        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
                        finish();
                    }
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profilePic.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(SettingActivity.this, "Error! Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingActivity.this, SettingActivity.class));
            finish();
        }
    }

    private void displayUserInfo(final CircleImageView profilePic, final EditText username) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentInfo.currentUser.getUsername());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("image").exists())
                    {
                        String userName = dataSnapshot.child("username").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();

                        username.setText(userName);
                        Picasso.get().load(image).into(profilePic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
