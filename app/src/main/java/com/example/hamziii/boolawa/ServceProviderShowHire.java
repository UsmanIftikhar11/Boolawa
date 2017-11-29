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
import android.widget.Toast;

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

                if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).exists()) {

                    if (dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("HiredBy").exists()) {


                        photoHiredBy = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("HiredBy").getValue().toString();

                        mDatabaseUsers.child(photoHiredBy).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String hiredName1 = dataSnapshot.child("UserName").getValue().toString();

                                txt_hiredByName.setText(hiredName1);
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

                else {

                    mDatabsaeCat.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).exists()){

                                catHiredBy = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("HiredBy").getValue().toString();

                                mDatabaseUsers.child(catHiredBy).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String hiredName = dataSnapshot.child("UserName").getValue().toString() ;

                                        txt_hiredByName.setText(hiredName);
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
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



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
