package com.example.cosmosafety;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cosmosafety.Model.Chemicals;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ViewHolder.ProductViewHolder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductDetailsActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.recyclerMenu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseRef = FirebaseDatabase.getInstance().getReference().child("Chemicals");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Chemicals> options = new FirebaseRecyclerOptions.Builder<Chemicals>()
                .setQuery(databaseRef,Chemicals.class).build();

        FirebaseRecyclerOptions<Chemicals> tmp = new FirebaseRecyclerOptions.Builder<Chemicals>()
                .setQuery(databaseRef.orderByChild("Chemical"),Chemicals.class).build();

        final FirebaseRecyclerAdapter<Chemicals, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Chemicals, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Chemicals chemicals)
            {
                if(chemicals.getID()==1)
                {

                    //getRef(i).removeValue();
                    //notifyItemRemoved(i);
                    //notifyItemRangeChanged(i,getItemCount()-1);
                    //productViewHolder.itemView.setVisibility(View.GONE);
                }

                else {
                    productViewHolder.productName.setText(chemicals.getChemical());
                    long num = chemicals.getID();
                    String sID = Long.toString(num);
                    productViewHolder.company.setText(sID);
                }

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);

                return holder;
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
