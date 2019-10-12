package com.example.cosmosafety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.cosmosafety.Model.CurrentInfo;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private RelativeLayout sp, reco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sp = (RelativeLayout)findViewById(R.id.search_products_sa);
        reco = (RelativeLayout)findViewById(R.id.recommendation_sa);

        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,FindActivity.class);
                startActivity(intent);
            }
        });


        reco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(CurrentInfo.currentUser==null) {
                    intent = new Intent(StartActivity.this, RegisterActivity.class);
                }
                else
                {
                    intent = new Intent(StartActivity.this,ProductUsedActivity.class);
                }
                startActivity(intent);
            }
        });

    }
}
