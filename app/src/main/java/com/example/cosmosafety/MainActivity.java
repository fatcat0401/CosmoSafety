package com.example.cosmosafety;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cosmosafety.Model.ChemEffect;
import com.example.cosmosafety.Model.Chemicals;
import com.example.cosmosafety.Model.Effect;
import com.example.cosmosafety.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private ImageView img;

    List<Effect> effectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        effectList = new ArrayList<>();

        DatabaseReference nDB = FirebaseDatabase.getInstance().getReference("Products");
        DatabaseReference delCurEff = FirebaseDatabase.getInstance().getReference("CurrentEffects");
        delCurEff.removeValue();
        //fixDB(nDB);
        //createEffectDB(nDB);
        //addToEffect(nDB);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        img = (ImageView) findViewById(R.id.fade_app_logo);


        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        img.setAnimation(myAnim);
        final Intent intent = new Intent(MainActivity.this, IntroActivity1.class);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }finally {
                    startActivity(intent);

                    finish();
                }
            }
        };
        thread.start();

    }

    void addToEffect(final DatabaseReference ref)
    {
        DatabaseReference old = FirebaseDatabase.getInstance().getReference().child("EffChem");
        List<ChemEffect>effectList = new ArrayList<>();
        final DatabaseReference chemDB = FirebaseDatabase.getInstance().getReference().child("Chemicals");
        old.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot tmp: dataSnapshot.getChildren())
                {
                    final ChemEffect c = tmp.getValue(ChemEffect.class);
                    String[] chemLst = c.chemLst();
                    for(final String chemID: chemLst)
                    {
                        Toast.makeText(MainActivity.this, chemID+" has been created!", Toast.LENGTH_SHORT).show();
                        chemDB.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(chemID).exists())
                                {
                                    Chemicals tmp = dataSnapshot.child(chemID).getValue(Chemicals.class);
                                    ref.child(Long.toString(c.getID())).child("Chemicals").child(Long.toString(tmp.getID())).setValue(tmp);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    //String[] pLst = c.getProductList();

                    //ref.child(Long.toString(c.getID())).setValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void fixDB(final DatabaseReference ref)
    {
        DatabaseReference old = FirebaseDatabase.getInstance().getReference().child("tmpProduct");
        old.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot tmp: dataSnapshot.getChildren())
                {
                    Product p = tmp.getValue(Product.class);
                    ref.child(Long.toString(p.getID())).setValue(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void createEffectDB(final DatabaseReference ref)
    {
        final DatabaseReference p = FirebaseDatabase.getInstance().getReference().child("Products");
        final DatabaseReference c = FirebaseDatabase.getInstance().getReference().child("Chemicals");
        DatabaseReference e = FirebaseDatabase.getInstance().getReference().child("nEffects");
        DatabaseReference cat = FirebaseDatabase.getInstance().getReference().child("Categories");

        e.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot tmp: dataSnapshot.getChildren())
                {
                    final Effect c = tmp.getValue(Effect.class);
//                    String[] a = c.getProductLst();
//                    String b = a[0]+a[a.length-1];
//                    Toast.makeText(MainActivity.this, b, Toast.LENGTH_SHORT).show();
                    effectList.add(c);
//                    HashMap<String, Object> effectMap = new HashMap<>();
//                    effectMap.put("Effects",c.getEffects().toString());
//                    effectMap.put("Product_ID",c.getProduct_ID().toString());
//                    ref.child(Long.toString(c.getID())).setValue(effectMap);


                }

                //for(Effect e: effectList) {


                    for(Effect e: effectList) {
                        final Effect c = e;
                        final HashMap<String, Integer> catList = new HashMap<>();
                        //  catList.clear();
                        catList.put("Total", 0);
                        //List<String> catLst = new ArrayList<>();
                        final int pos = 0;

                        p.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot tmp : dataSnapshot.getChildren()) {
                                    Product p = tmp.getValue(Product.class);

                                    if (c.isBelonged(Long.toString(p.getID()))) {
                                        String catID = p.getCategoriesList();
                                        if (catList.containsKey(catID)) {
                                            catList.put(catID, catList.get(catID) + 1);
                                        } else
                                            catList.put(catID, 1);
                                        catList.put("Total", catList.get("Total") + 1);
                                        ref.child(Long.toString(c.getID())).child("Categories").child(catID).setValue(Integer.toString(catList.get(catID)));
                                        ref.child(Long.toString(c.getID())).child("Categories").child("Total").setValue(Integer.toString(catList.get("Total")));
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                //}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
