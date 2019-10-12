package com.example.cosmosafety;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cosmosafety.Model.Category;
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

public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private Button searchBtn, homeBtn;
    private RecyclerView searchProduct;
    String searchInput = "";
    float avgRating = 0;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        homeBtn = (Button)findViewById(R.id.toHome_btn_sa);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        searchText = (EditText)findViewById(R.id.search_text);
        searchBtn = (Button)findViewById(R.id.btn_searchPname);
        searchProduct = (RecyclerView) findViewById(R.id.search_list);
        searchProduct.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = searchText.getText().toString();
                onStart();
            }
        });
    }

    public boolean isBelonged(String pID, String pLst)
    {
        String[] tmp = pLst.split(",");
        //String pID = Long.toString(ID);
        for(int i = 0; i<tmp.length; i++)
        {
            tmp[i]=tmp[i].replaceAll("\\s","");
            int a = Integer.parseInt(tmp[i]);
            int b = Integer.parseInt(pID);
            //if(tmp[i].equals(pID))
            if(a==b)
                return true;
        }


        return false;

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        final DatabaseReference filterRef = FirebaseDatabase.getInstance().getReference().child("CurrentEffects");

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productRef.orderByChild("product_name").startAt(searchInput).endAt(searchInput + "\uf8ff"), Product.class).build();

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

                            //productViewHolder.rating.setText(Float.toString(avgRating));
                            productViewHolder.rating.setText(String.format("%.2f", avgRating));
                            if(avgRating > 2.5)
                            {
                                productViewHolder.rating.setTextColor(Color.parseColor("#03d3f1"));
                            }
                            else
                            {
                                productViewHolder.rating.setTextColor(Color.parseColor("#b31307"));
                            }
                        }
                        else
                        {
                            productViewHolder.rating.setText("N/A");
                            productViewHolder.rating.setTextColor(Color.parseColor("#03d3f1"));

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                productViewHolder.productName.setText(product.getProduct_name());
                productViewHolder.company.setText(product.getBrands());
                //productViewHolder.tvCategory.setText(product.getCategories());

                final String[] tmp = product.getCategories().split(",");

                for(int j = 0; j<tmp.length; j++)
                {
                    tmp[j]=tmp[j].replaceAll("\\s","");
                }
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Categories");

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String catInName = "";
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            Category cat = ds.getValue(Category.class);
                            for(String c: tmp)
                            {
                                if(Long.parseLong(c)==cat.getId())
                                {
                                    if(catInName=="") {
                                        catInName += cat.getCategory();
                                    }
                                    else
                                    {
                                        catInName += ", " + cat.getCategory();
                                    }
                                }
                            }
                        }
                        productViewHolder.tvCategory.setText(catInName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this,ProductRatingActivity.class);
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
    searchProduct.setAdapter(adapter);
    adapter.startListening();
    }
}
