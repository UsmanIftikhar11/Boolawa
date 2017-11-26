package com.example.hamziii.boolawa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ServceProviderShowHire extends AppCompatActivity {

    private DatabaseReference mDatabase , mDatabsaeCat , mDatabasePhoto , mDatabaseUsers;
    private FirebaseAuth mAuth ;

    private Query mQueryPhoto , mQuerryCat ;

    private Button btn_serviceUpdate ;

    private String catHiredBy , photoHiredBy ;

    private TextView txt_hiredByName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servce_provider_show_hire);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Hire");
        mDatabasePhoto = mDatabase.child("Photographer");
        mDatabsaeCat = mDatabase.child("Caterer");
        mQueryPhoto = mDatabasePhoto.orderByChild("Service").equalTo("Photographer");
        mQuerryCat = mDatabsaeCat.orderByChild("Service").equalTo("caterer");

        txt_hiredByName = (TextView)findViewById(R.id.txt_hiredByName);

        btn_serviceUpdate = (Button)findViewById(R.id.btn_serviceUpdate);
        btn_serviceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServceProviderShowHire.this , ServiceProviderHome.class);
                startActivity(intent);
            }
        });

        mDatabasePhoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photoHiredBy = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("HiredBy").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabsaeCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catHiredBy = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("HiredBy").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(!TextUtils.isEmpty(photoHiredBy)){

            mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    txt_hiredByName.setText(photoHiredBy);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else if (!TextUtils.isEmpty(catHiredBy)){

            mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    txt_hiredByName.setText(photoHiredBy);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else {

            txt_hiredByName.setText("No One");
        }

    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mAuth.getInstance().signOut();

                Intent intent = new Intent(ServceProviderShowHire.this , ServiceProviderLogin.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        final AlertDialog ad = builder.create();
        ad.show();

    }
}
