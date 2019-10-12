package com.example.cosmosafety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ConditionActivity extends AppCompatActivity {

    String selectedAge = null;
    String selectedCond = null;
    String effect = "476, 479, 498, 503, 601, 624, 647, 648";
    private Button button;
    DatabaseReference effectRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);


        List<String> ageLst = new ArrayList<String>();
        ageLst.add("16-20");
        ageLst.add("21-40");
        ageLst.add("41-60");

        List<String> condLst = new ArrayList<String>();
        condLst.add("Dermal");
        condLst.add("Cardiovascular");
        condLst.add("Endocrine");

        final Spinner sAge = (Spinner) findViewById(R.id.AgeRangeSpn);
        final Spinner sConditions = (Spinner) findViewById(R.id.ConditionSpn);

        final ArrayAdapter<String> aspAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, ageLst);
        aspAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAge.setAdapter(aspAdapter);

        final ArrayAdapter<String> cspAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, condLst);
        cspAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sConditions.setAdapter(cspAdapter);


        sAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedAge = parent.getItemAtPosition(position).toString();
                if(selectedAge!=null)
                {
                    //Toast.makeText(parent.getContext(),"Selected age is: "+selectedAge,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sConditions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCond = parent.getItemAtPosition(position).toString();
                if(selectedCond!=null)
                {
                    //Toast.makeText(parent.getContext(),"Selected age is: "+selectedAge,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button = (Button)findViewById(R.id.toCategoryBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConditionActivity.this, ProductUsedActivity.class);
                intent.putExtra("effect",effect);
                startActivity(intent);
            }
        });
    }
}
