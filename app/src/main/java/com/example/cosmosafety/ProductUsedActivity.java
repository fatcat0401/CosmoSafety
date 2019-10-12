package com.example.cosmosafety;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosmosafety.Model.Category;
import com.example.cosmosafety.Model.CurrentInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ViewHolder.CategoryViewHolder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProductUsedActivity extends AppCompatActivity {

    private DatabaseReference categoryRef;
    private RecyclerView recyclerView;
    private TextView countItemsTv;
    private Button contBtn;
    private ImageView homeBtn;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(CurrentInfo.currentUser.getUsername());
    int countItem = 0;
    String isChecked;
    ForegroundColorSpan fcRed = new ForegroundColorSpan(Color.parseColor("#c90627"));
    ForegroundColorSpan fcBlue = new ForegroundColorSpan(Color.parseColor("#03d3f1"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_used);

        isChecked = "X";

        countItemsTv = (TextView)findViewById(R.id.item_num_pu);
        contBtn = (Button)findViewById(R.id.contBtn_pu);

        homeBtn = (ImageView) findViewById(R.id.home_pu);

        recyclerView = (RecyclerView)findViewById(R.id.listview_pu);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        categoryRef = FirebaseDatabase.getInstance().getReference().child("Categories");


        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductUsedActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductUsedActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(categoryRef, Category.class).build();

        final FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, final int i, @NonNull final Category category) {

                categoryViewHolder.categoryName.setText(category.getCategory().toString());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("UsingList").child(Long.toString(category.getId())).exists()) {
                            categoryViewHolder.isChecked = true;
                            categoryViewHolder.checkMark.setText("\u2713");
                            categoryViewHolder.checkMark.setTextColor(Color.parseColor("#03d3f1"));
                            //ss.setSpan(fcRed,1,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        else
                        {
                            categoryViewHolder.isChecked = false;
                            categoryViewHolder.checkMark.setText("X");
                            categoryViewHolder.checkMark.setTextColor(Color.parseColor("#c90627"));

                        }

                        if(dataSnapshot.child("Total").exists()) {
                            String tmp = dataSnapshot.child("Total").getValue().toString();
                            countItem = Integer.parseInt(tmp);
                            countItemsTv.setText(countItem + " Items");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (categoryViewHolder.isChecked) {
                            ref.child("UsingList").child(Long.toString(category.getId())).removeValue();
                            ref.child("HarmfulList").child(Long.toString(category.getId())).removeValue();
                            if (countItem > 0)
                                countItem--;
                            categoryViewHolder.isChecked = false;
                            categoryViewHolder.checkMark.setText("X");
                            categoryViewHolder.checkMark.setTextColor(Color.parseColor("#c90627"));
                            //ss.setSpan(fcRed,1,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            final HashMap<String, Object> catMap =  new HashMap<>();
                            final String sId = Long.toString(category.getId());

                            DatabaseReference effToCatRef = FirebaseDatabase.getInstance().getReference("EffCat").child("4");
                            effToCatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(sId).exists())
                                    {
                                        int countCat = Integer.parseInt(dataSnapshot.child(sId).getValue().toString());
                                        if(countCat > 50)
                                        {
                                            catMap.put("Icon", R.drawable.signal);
                                        }
                                        else
                                            catMap.put("Icon", R.drawable.warning);
                                        catMap.put("ID",category.getId());
                                        catMap.put("Name",category.getCategory());
                                        ref.child("HarmfulList").child(sId).setValue(catMap);

                                    }
                                    else
                                    {
                                        catMap.put("ID",category.getId());
                                        catMap.put("Name",category.getCategory());
                                        catMap.put("Icon", R.drawable.suggested);
                                    }
                                    ref.child("UsingList").child(sId).setValue(catMap);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            countItem++;

                            categoryViewHolder.isChecked = true;
                            categoryViewHolder.checkMark.setText("\u2713");
                            categoryViewHolder.checkMark.setTextColor(Color.parseColor("#03d3f1"));

                            //ss.setSpan(fcBlue,1,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }
                        ref.child("Total").setValue(countItem);
                        countItemsTv.setText(countItem + " Items");

                    }
                });

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout,parent,false);
                CategoryViewHolder holder = new CategoryViewHolder(view);

                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}
