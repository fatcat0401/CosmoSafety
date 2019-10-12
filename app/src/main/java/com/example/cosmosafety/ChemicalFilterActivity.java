package com.example.cosmosafety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.cosmosafety.Model.Effect;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ChemicalFilterActivity extends AppCompatActivity {

    private CheckBox c1,c2,c3,c4,c5,c6;

    private Button showLstBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_filter);

        final DatabaseReference effRef = FirebaseDatabase.getInstance().getReference("CurrentEffects");
        final DatabaseReference oRef = FirebaseDatabase.getInstance().getReference("Effects");
        showLstBtn = (Button)findViewById(R.id.toProduct_btn_cfa);
        showLstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChemicalFilterActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        c1 = (CheckBox) findViewById(R.id.ckbox1_cfa);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(c1.isChecked())
            {
                oRef.child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Effect c = dataSnapshot.getValue(Effect.class);
                        effRef.child("1").setValue(c.getProduct_ID());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            else
            {
                effRef.child("1").removeValue();
            }

            }
        });

        c2 = (CheckBox) findViewById(R.id.ckbox2_cfa);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c2.isChecked())
                {
                    oRef.child("2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Effect c = dataSnapshot.getValue(Effect.class);
                            effRef.child("2").setValue(c.getProduct_ID());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    effRef.child("2").removeValue();
                }

            }
        });

        c3 = (CheckBox) findViewById(R.id.ckbox3_cfa);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c3.isChecked())
                {
                    oRef.child("3").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Effect c = dataSnapshot.getValue(Effect.class);
                            effRef.child("3").setValue(c.getProduct_ID());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    effRef.child("3").removeValue();
                }


            }
        });
        c4 = (CheckBox) findViewById(R.id.ckbox4_cfa);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c4.isChecked())
                {
                    oRef.child("4").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Effect c = dataSnapshot.getValue(Effect.class);
                            effRef.child("4").setValue(c.getProduct_ID());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    effRef.child("4").removeValue();
                }


            }
        });
        c5 = (CheckBox) findViewById(R.id.ckbox5_cfa);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c5.isChecked())
                {
                    oRef.child("5").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Effect c = dataSnapshot.getValue(Effect.class);
                            effRef.child("5").setValue(c.getProduct_ID());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    effRef.child("5").removeValue();
                }


            }
        });
        c6 = (CheckBox) findViewById(R.id.ckbox6_cfa);
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c6.isChecked())
                {
                    oRef.child("6").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Effect c = dataSnapshot.getValue(Effect.class);
                            effRef.child("6").setValue(c.getProduct_ID());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    effRef.child("6").removeValue();
                }


            }
        });
    }
}
