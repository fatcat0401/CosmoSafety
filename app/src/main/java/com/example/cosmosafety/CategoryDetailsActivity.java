package com.example.cosmosafety;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cosmosafety.Model.Product;
import com.example.cosmosafety.Model.Rating;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ViewHolder.ProductViewHolder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryDetailsActivity extends AppCompatActivity {

    String catName;
    float avgRating = 0;
    int count = 0;
    private TextView tvCatName;
    private RecyclerView list;
    private DatabaseReference productRef;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        tvCatName = (TextView)findViewById(R.id.category_name);
        list = (RecyclerView) findViewById(R.id.productLst1);
        catName = getIntent().getStringExtra("categoryName");
        tvCatName.setText(catName);
        list.setLayoutManager(new LinearLayoutManager(CategoryDetailsActivity.this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productRef.orderByChild("categories").startAt(catName), Product.class).build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i, @NonNull final Product product) {
                count = 0;
                avgRating = 0;

                DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference().child("Ratings").child(Long.toString(product.getID()));
                ratingRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot rating: dataSnapshot.getChildren())
                            {
                                Rating tmp = rating.getValue(Rating.class);
                                avgRating += tmp.getRating();
                                count++;
                            }
                            avgRating/=count;
                            productViewHolder.rating.setText(Float.toString(avgRating));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                productViewHolder.productName.setText(product.getProduct_name());
                productViewHolder.company.setText(product.getBrands());
                productViewHolder.tvCategory.setText(product.getCategories());



                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategoryDetailsActivity.this,ProductRatingActivity.class);
                        intent.putExtra("pid",product.getID());
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);

                return holder;
            }
        };

        list.setAdapter(adapter);
        adapter.startListening();

    }
}
