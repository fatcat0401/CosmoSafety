package com.example.cosmosafety;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccBtn;
    private EditText InputName;
    private Dialog dialog;
    private TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccBtn = (Button)findViewById(R.id.createAcc_btn);
        InputName = (EditText) findViewById(R.id.register_input);
        toLogin = (TextView)findViewById(R.id.toLogin_R);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_bar);
        dialog = builder.create();

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();

            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    void createAccount()
    {
        String username = InputName.getText().toString();
        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this,"Please input user name...",Toast.LENGTH_LONG).show();
        }
        else
        {
            setLoadingDialog(true);
            validateUserName(username);
        }
    }

    void validateUserName(final String username)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(username).exists())
                {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("username",username);
                    userDataMap.put("password","a");
                    ref.child(username).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Your account has been created!", Toast.LENGTH_SHORT).show();
                                setLoadingDialog(false);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                setLoadingDialog(false);
                                Toast.makeText(RegisterActivity.this, "Network error! Please try again...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(RegisterActivity.this, "This "+username+" is already existed!", Toast.LENGTH_SHORT).show();
                    setLoadingDialog(false);
                    Toast.makeText(RegisterActivity.this, "Please try again use another username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void setLoadingDialog(boolean show)
    {

        if(show)
            dialog.show();
        else
            dialog.dismiss();
    }
}
