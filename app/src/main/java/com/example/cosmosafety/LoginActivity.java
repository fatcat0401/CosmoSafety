package com.example.cosmosafety;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cosmosafety.Model.CurrentInfo;
import com.example.cosmosafety.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText loginInputEt;
    private Button loginBtn;
    private CheckBox rememberMeChkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginInputEt = (EditText)findViewById(R.id.login_input);
        loginBtn = (Button) findViewById(R.id.login_btn);
        rememberMeChkBox = (CheckBox) findViewById(R.id.remember_me_chkbx);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginInputEt.getText().toString();
                if(TextUtils.isEmpty(username))
                {
                    Toast.makeText(LoginActivity.this,"Please input user name...",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    validateUserName(username);
                }
            }

        });
    }

    void validateUserName(final String username)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(username).exists())
                {

                }
                else
                {
                    User userdata = dataSnapshot.child(username).getValue(User.class);
                    CurrentInfo.currentUser = userdata;
                    Intent intent = new Intent(LoginActivity.this, ProductUsedActivity.class);

                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
