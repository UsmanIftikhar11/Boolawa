package com.example.hamziii.boolawa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity
{

    private Button mLogOutBtn;
    private FirebaseAuth maAuth;
    private FirebaseAuth.AuthStateListener mauthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        maAuth = FirebaseAuth.getInstance();
        mauthListner = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if (firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(Account.this, LoginActivity.class));
                }
            }
        };
        mLogOutBtn = (Button)findViewById(R.id.logOutBtn);

        mLogOutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                maAuth.signOut();

                Intent intent_accountActivity = new Intent(Account.this,LoginActivity.class);
                startActivity(intent_accountActivity);
            }

        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        maAuth.addAuthStateListener(mauthListner);
    }
}
