package com.example.cosmosafety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class FindActivity extends AppCompatActivity {

    private RelativeLayout catFilter,chemFilter;
    private Button homeBtn;
    private LinearLayout searchProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        catFilter = (RelativeLayout)findViewById(R.id.product_categories_fa);
        chemFilter = (RelativeLayout)findViewById(R.id.chemical_filter_fa);
        homeBtn = (Button)findViewById(R.id.toHome_btn_fa);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        searchProduct = (LinearLayout)findViewById(R.id.ll_fa);

        searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        catFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this, AdminCategoryActivity.class);
                startActivity(intent);
            }
        });

        chemFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this, ChemicalFilterActivity.class);
                startActivity(intent);
            }
        });
    }
}
