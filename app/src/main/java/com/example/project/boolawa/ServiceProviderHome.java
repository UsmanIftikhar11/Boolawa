package com.example.project.boolawa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ServiceProviderHome extends AppCompatActivity {

    MaterialSpinner spinnerUpdate ;
    private boolean available = false ;
    private boolean not_available = false ;

    private DatabaseReference mDatabseCat , mDatabasePhoto ;
    private FirebaseAuth mAuth ;

    EditText updateServiceName , updateServiceContact , updateServiceCharges ;
    private Button btn_update ;

    private String serviceType ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabseCat = FirebaseDatabase.getInstance().getReference().child("Hire").child("Caterer");
        mDatabasePhoto = FirebaseDatabase.getInstance().getReference().child("Hire").child("Photographer");


        updateServiceName = (EditText)findViewById(R.id.update_servicename);
        updateServiceCharges = (EditText)findViewById(R.id.update_servicecharges);
        updateServiceContact = (EditText)findViewById(R.id.update_servicecontact);
        btn_update = (Button)findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdate();
            }
        });

        spinnerUpdate = (MaterialSpinner) findViewById(R.id.spinner_updateAvailability);
        spinnerUpdate.setItems("Availability" , "Available", "Not Available");

        spinnerUpdate.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                if(item.toString()=="Available")
                {
                    available = true ;
                    not_available = false ;
                }
                else if (item.toString()=="Not Available")
                {
                    available = false ;
                    not_available = true ;
                }
                else {
                    available = false ;
                    not_available = true ;
                }
            }
        });

        mDatabseCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).hasChild("name"))
                {
                    updateServiceName.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("name").getValue().toString());
                    updateServiceContact.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("phone").getValue().toString());
                    updateServiceCharges.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("charges").getValue().toString());

                    serviceType = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("Service").getValue().toString();

                }

                else {

                    mDatabasePhoto.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            updateServiceName.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("name").getValue().toString());
                            updateServiceContact.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("phone").getValue().toString());
                            updateServiceCharges.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("charges").getValue().toString());

                            serviceType = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("Service").getValue().toString();

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

    private void startUpdate() {

        String updateName = updateServiceName.getText().toString().trim();
        String updateCntact = updateServiceContact.getText().toString().trim();
        String updateCharges = updateServiceCharges.getText().toString().trim();

        if(!TextUtils.isEmpty(updateName) && !TextUtils.isEmpty(updateCharges)  &&  !TextUtils.isEmpty(updateCntact)){


            if(available == true){

                if(serviceType.equals("caterer")) {

                    DatabaseReference caterer_user_db = mDatabseCat.child(mAuth.getCurrentUser().getUid());
                    caterer_user_db.child("name").setValue(updateName);
                    caterer_user_db.child("phone").setValue(updateCntact);
                    caterer_user_db.child("charges").setValue(updateCharges);
                    caterer_user_db.child("Availability").setValue("Available");

                    caterer_user_db.child("HiredBy").removeValue();

                }

                else {

                    DatabaseReference caterer_user_db = mDatabasePhoto.child(mAuth.getCurrentUser().getUid());
                    caterer_user_db.child("name").setValue(updateName);
                    caterer_user_db.child("phone").setValue(updateCntact);
                    caterer_user_db.child("charges").setValue(updateCharges);
                    caterer_user_db.child("Availability").setValue("Available");

                    caterer_user_db.child("HiredBy").removeValue();
                }
            }

            else {

                if(serviceType.equals("caterer")) {

                    DatabaseReference caterer_user_db = mDatabseCat.child(mAuth.getCurrentUser().getUid());
                    caterer_user_db.child("name").setValue(updateName);
                    caterer_user_db.child("phone").setValue(updateCntact);
                    caterer_user_db.child("charges").setValue(updateCharges);
                    caterer_user_db.child("Availability").setValue("Not Available");

                }

                else {

                    DatabaseReference caterer_user_db = mDatabasePhoto.child(mAuth.getCurrentUser().getUid());
                    caterer_user_db.child("name").setValue(updateName);
                    caterer_user_db.child("phone").setValue(updateCntact);
                    caterer_user_db.child("charges").setValue(updateCharges);
                    caterer_user_db.child("Availability").setValue("Not Available");
                }
            }

        }
    }
}
