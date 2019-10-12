package com.example.cosmosafety;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

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

public class AdminCategoryActivity extends AppCompatActivity {

    private TextView titleTv;

    private TextView faceMask,faceScrub, faceCream, faceEye;
    private TextView hairSuncare, hairMask, hairShampoo, hairDyeing, hairSpray;
    private TextView hygieneBody, hygieneFace, hygieneShaving, hygieneHousehold, hygieneMakeup, hygieneShower;
    private TextView bodyLotion, bodySpray, bodyScrub, bodySuncare;

    private RecyclerView prodLst;
    private ScrollView scrollView;
    private Button backBtn,homeBtn;

    float avgRating = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        homeBtn = (Button)findViewById(R.id.toHome_btn_aca);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        titleTv = (TextView)findViewById(R.id.tv_aca);
        titleTv.setVisibility(View.INVISIBLE);
        titleTv.setEnabled(false);

        backBtn = (Button)findViewById(R.id.toCatLst_btn_aca);
        backBtn.setVisibility(View.INVISIBLE);
        backBtn.setEnabled(false);

        prodLst = (RecyclerView)findViewById(R.id.prod_lst_aca);
        prodLst.setLayoutManager(new LinearLayoutManager(AdminCategoryActivity.this));
        prodLst.setEnabled(false);
        prodLst.setVisibility(View.INVISIBLE);

        scrollView = (ScrollView)findViewById(R.id.scrview_aca);

        faceMask = (TextView)findViewById(R.id.cat14_acf);
        faceMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("1, 14");
                titleTv.setText("Face -> Mask");
            }
        });

        faceScrub = (TextView)findViewById(R.id.cat20_acf);
        faceScrub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("1, 20");
                titleTv.setText("Face -> Scrub");
            }
        });

        faceCream = (TextView)findViewById(R.id.cat9_acf);
        faceCream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("1, 9");
                titleTv.setText("Face -> Cream");
            }
        });

        faceEye = (TextView)findViewById(R.id.cat27_acf);
        faceEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("1, 27");
                titleTv.setText("Face -> Eye");
            }
        });

        hairSuncare = (TextView)findViewById(R.id.cat19_acf);
        hairSuncare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("2, 19");
                titleTv.setText("Hair -> Suncare");
            }
        });

        hairMask = (TextView)findViewById(R.id.cat142_acf);
        hairMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("2, 14");
                titleTv.setText("Hair -> Mask");
            }
        });

        hairShampoo = (TextView)findViewById(R.id.cat10_acf);
        hairShampoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("2, 10");
                titleTv.setText("Hair -> Shampoo");
            }
        });

        hairDyeing = (TextView)findViewById(R.id.cat11_acf);
        hairDyeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("2, 11");
                titleTv.setText("Hair -> Dyeing");
            }
        });

        hairSpray = (TextView)findViewById(R.id.cat21_acf);
        hairSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("2, 21");
                titleTv.setText("Hair -> Spray");
            }
        });

        hygieneBody = (TextView)findViewById(R.id.cat19_acf);
        hygieneBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("3, 19");
                titleTv.setText("Hygiene -> Body");
            }
        });

        hygieneBody = (TextView)findViewById(R.id.cat19_acf);
        hygieneBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("3, 19");
                titleTv.setText("Hygiene -> Body");
            }
        });

        hygieneBody = (TextView)findViewById(R.id.cat19_acf);
        hygieneBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("3, 19");
                titleTv.setText("Hygiene -> Body");
            }
        });

        hygieneBody = (TextView)findViewById(R.id.cat19_acf);
        hygieneBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("3, 19");
                titleTv.setText("Hygiene -> Body");
            }
        });

        hygieneBody = (TextView)findViewById(R.id.cat19_acf);
        hygieneBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("3, 19");
                titleTv.setText("Hygiene -> Body");
            }
        });

        hygieneBody = (TextView)findViewById(R.id.cat19_acf);
        hygieneBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("3, 19");
                titleTv.setText("Hygiene -> Body");
            }
        });

        hygieneBody = (TextView)findViewById(R.id.cat19_acf);
        hygieneBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForOnClick();
                createList("3, 19");
                titleTv.setText("Hygiene -> Body");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateForCat();
            }
        });

        final Intent intent = new Intent(AdminCategoryActivity.this,CategoryDetailsActivity.class);
    }

    public void setStateForCat()
    {
        scrollView.setVisibility(View.VISIBLE);
        scrollView.setEnabled(true);

        titleTv.setVisibility(View.INVISIBLE);
        titleTv.setEnabled(false);

        backBtn.setVisibility(View.INVISIBLE);
        backBtn.setEnabled(false);

        prodLst.setVisibility(View.INVISIBLE);
        prodLst.setEnabled(false);
    }

    public void setStateForOnClick()
    {
        scrollView.setVisibility(View.INVISIBLE);
        scrollView.setEnabled(false);

        titleTv.setVisibility(View.VISIBLE);
        titleTv.setEnabled(true);

        backBtn.setVisibility(View.VISIBLE);
        backBtn.setEnabled(true);

        prodLst.setVisibility(View.VISIBLE);
        prodLst.setEnabled(true);
    }

    public void createList(String catTree)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Products");
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productRef.orderByChild("categories").startAt(catTree).endAt(catTree + "\uf8ff"), Product.class).build();

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
                        Intent intent = new Intent(AdminCategoryActivity.this,ProductRatingActivity.class);
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
        prodLst.setAdapter(adapter);
        adapter.startListening();
    }


}
