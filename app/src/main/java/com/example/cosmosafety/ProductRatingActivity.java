package com.example.cosmosafety;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cosmosafety.Model.Product;
import com.example.cosmosafety.Model.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProductRatingActivity extends AppCompatActivity {

    private TextView pName,pCompany,pCategory,rValue;
    private RatingBar pRating;
    private Button finish;

    DatabaseReference ratingRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_rating);

        pName = (TextView)findViewById(R.id.productName_rating);
        pCompany = (TextView)findViewById(R.id.productCompany_rating);
        pCategory = (TextView)findViewById(R.id.category_rating);
        pRating = (RatingBar) findViewById(R.id.rating_stars);
        rValue = (TextView)findViewById(R.id.value_rating);
        finish = (Button)findViewById(R.id.btn_finish_rating);
        Long pid = getIntent().getLongExtra("pid",1);

        getProductDetails(Long.toString(pid));
        ratingRef = FirebaseDatabase.getInstance().getReference("Ratings").child(Long.toString(pid));

        pRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rValue.setText("This product rating is "+ rating);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRating();
                onBackPressed();
            }


        });

    }
    private void saveRating()
    {
        float score = pRating.getRating();
        String id = ratingRef.push().getKey();

        Rating rating = new Rating(id,score);
        ratingRef.child(id).setValue(rating);

    }

    private void getProductDetails(String pID)
    {
        DatabaseReference pRef = FirebaseDatabase.getInstance().getReference().child("Products");
        pRef.child(pID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Product product = dataSnapshot.getValue(Product.class);
                    pName.setText(product.getProduct_name());
                    pCompany.setText(product.getBrands());
                    pCategory.setText(product.getCategories());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
