package com.example.cosmosafety;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cosmosafety.Model.Category;
import com.example.cosmosafety.Model.Chemicals;
import com.example.cosmosafety.Model.CurrentInfo;
import com.example.cosmosafety.Model.HarmfulCategory;
import com.example.cosmosafety.Model.Product;
import com.example.cosmosafety.Model.Rating;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ViewHolder.ProductViewHolder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView catLV, chemLV;
    private DatabaseReference productRef;
    private RecyclerView recyclerView;
    private RelativeLayout warningLayout;
    private TextView warningTv, productTv;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference chemicalRef;

    List<String> catList = new ArrayList<>();
    List<Integer> catImgs = new ArrayList<>();

    float avgRating = 0;
    int count = 0;
    private ArrayList<String> chemicalNames = new ArrayList<>();
    int pos;
    private ArrayList<Rating> ratingList = new ArrayList<>();
    Chemicals chem;
    Product p;
    String tmp;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        catLV = (ListView)findViewById(R.id.cat_list_HA);
        chemLV = (ListView)findViewById(R.id.chem_list_HA);




        warningTv = (TextView)findViewById(R.id.warning_ha);
        productTv = (TextView)findViewById(R.id.products_ha);


        chemicalRef = FirebaseDatabase.getInstance().getReference().child("Chemicals");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTV = headerView.findViewById(R.id.profile_name);
        CircleImageView profileImg = headerView.findViewById(R.id.profile_image);

        Picasso.get().load(CurrentInfo.currentUser.getImage()).into(profileImg);

        userNameTV.setText(CurrentInfo.currentUser.getUsername());

        recyclerView = findViewById(R.id.recyclerMenu);
        recyclerView.setHasFixedSize(true);

        productTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                warningLayout.setVisibility(View.INVISIBLE);
                warningLayout.setEnabled(false);
                recyclerView.setEnabled(true);
            }
        });




        warningTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.INVISIBLE);
                recyclerView.setEnabled(false);
                warningLayout.setVisibility(View.VISIBLE);
                warningLayout.setEnabled(true);
            }
        });


        warningLayout = (RelativeLayout) findViewById(R.id.warning_layout);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(CurrentInfo.currentUser.getUsername()).child("HarmfulList");
        final DatabaseReference catRef = FirebaseDatabase.getInstance().getReference("Categories");
        final DatabaseReference effToChemRef = FirebaseDatabase.getInstance().getReference("EffRelation").child("4").child("Chemicals");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {
                    catList.clear();
                    catImgs.clear();
                    int count = 0;

                    for(DataSnapshot cat: dataSnapshot.getChildren())
                    {
                        if(count<3) {
                            HarmfulCategory hc = cat.getValue(HarmfulCategory.class);
                            catList.add(hc.getName());
                            catImgs.add(hc.getIcon());
                            count++;
                        }
//                        Long lcatID = cat.child("ID").getValue(Long.class);
//                        String catID = Long.toString(lcatID);

                    }

                    //Toast.makeText(HomeActivity.this, catList.get(0)+" has been added", Toast.LENGTH_SHORT).show();

                    MyAdapter adapter = new MyAdapter(HomeActivity.this, catList, catImgs);
                    catLV.setAdapter(adapter);
                   // chemLV.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        effToChemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                {

                    List<String> chemName = new ArrayList<>();
                    List<Integer> chemImg = new ArrayList<>();
                    int count = 0;

                    for(DataSnapshot cat: dataSnapshot.getChildren())
                    {
                        if(count < 3)
                        {
                            Chemicals chem = cat.getValue(Chemicals.class);
                            chemName.add(chem.getChemical());
                            chemImg.add(R.drawable.dangerous_cat);
                            count++;
                        }

                    }

                    //Toast.makeText(HomeActivity.this, catList.get(0)+" has been added", Toast.LENGTH_SHORT).show();

                    MyAdapter adapter = new MyAdapter(HomeActivity.this, chemName, chemImg);
                    //catLV.setAdapter(adapter);
                    chemLV.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productRef, Product.class).build();

        final FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
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


                /*String[] tmp =getChemicalNames(product.getIngredients());
                for(String tmp1: chemicalNames) {
                    for (String tmp2 : tmp)
                    {
                        if(tmp1.compareTo(tmp2) ==0)
                        {
                            productViewHolder.itemView.setVisibility(View.GONE);
                        }
                    }
                }*/

                //productViewHolder.productName.setText(chemicalNames.get(0));
                productViewHolder.productName.setText(product.getProduct_name());
                productViewHolder.company.setText(product.getBrands());
                //productViewHolder.tvCategory.setText(product.getCatName());
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
                        Intent intent = new Intent(HomeActivity.this,ProductRatingActivity.class);
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

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories) {
            Intent intent = new Intent(HomeActivity.this,AdminCategoryActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_search)
        {
            Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_conditions)
        {
            Intent intent = new Intent(HomeActivity.this,StartActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_setting)
        {
            Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String[] getChemicalNames(String ingredientLst)
    {
        String[] lst = ingredientLst.split(",");
        return lst;
    }
}
