package com.example.project.boolawa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FragmentHire extends Fragment {

    private RecyclerView mServiceList ;

    private DatabaseReference mDatabase , mDatabasePhoto , mDatabaseCat , mDatabaseUsers ;

    private FirebaseAuth mAuth ;
    private Query mQueryPhoto , mQueryCat ;

    private Button btn_photo , btn_caterer ;

    private TextView txt_photo , txt_caterer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hire, container, false);

        mAuth = FirebaseAuth.getInstance() ;

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        btn_caterer = (Button) rootView.findViewById(R.id.btn_caterer);
        btn_photo = (Button) rootView.findViewById(R.id.btn_photographer);

        txt_caterer = (TextView)rootView.findViewById(R.id.txt_caterer);
        txt_photo = (TextView)rootView.findViewById(R.id.txt_photogrpaher);

        mServiceList = (RecyclerView) rootView.findViewById(R.id.service_list);
        mServiceList.setHasFixedSize(true);
        mServiceList.setLayoutManager(new LinearLayoutManager(getActivity()));

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Hire");
                mDatabasePhoto = mDatabase.child("Photographer");
                mQueryPhoto = mDatabasePhoto.orderByChild("Availability").equalTo("Available");


                FirebaseRecyclerAdapter<Users , ServiceViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, ServiceViewHolder>(

                                    Users.class ,
                                    R.layout.service_row ,
                                    ServiceViewHolder.class ,
                                    mQueryPhoto

                            ) {
                                @Override
                                protected void populateViewHolder(ServiceViewHolder viewHolder, Users model, int position) {

                                    final String key = getRef(position).getKey();

                                    viewHolder.setName(model.getName());
                                    viewHolder.setPhone(model.getPhone());
                                    viewHolder.setCharges(model.getCharges());

                                    viewHolder.btn_hire.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            final DatabaseReference mDatebaseHire = mDatabasePhoto.child(key);
                                            mDatabasePhoto.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    mDatebaseHire.child("Availability").setValue("Not Available");
                                                    mDatebaseHire.child("HiredBy").setValue(mAuth.getCurrentUser().getUid());
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                            Toast.makeText(getActivity() , "Photographer hired...We will contact you further details" , Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            };

                            mServiceList.setAdapter(firebaseRecyclerAdapter);

                            txt_caterer.setVisibility(View.INVISIBLE);
                            txt_photo.setVisibility(View.VISIBLE);


            }
        });

        btn_caterer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Hire");
                mDatabaseCat = mDatabase.child("Caterer");
                mQueryCat = mDatabaseCat.orderByChild("Availability").equalTo("Available");


                            FirebaseRecyclerAdapter<Users , ServiceViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, ServiceViewHolder>(

                                    Users.class ,
                                    R.layout.service_row ,
                                    ServiceViewHolder.class ,
                                    mQueryCat

                            ) {
                                @Override
                                protected void populateViewHolder(ServiceViewHolder viewHolder, Users model, int position) {

                                    final String key1 = getRef(position).getKey();

                                    viewHolder.setName(model.getName());
                                    viewHolder.setPhone(model.getPhone());
                                    viewHolder.setCharges(model.getCharges());

                                    viewHolder.btn_hire.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(getActivity() , "Hired!!!!!!!" , Toast.LENGTH_LONG).show();
                                            final DatabaseReference mDatebaseHire = mDatabaseCat.child(key1);
                                            mDatabaseCat.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    mDatebaseHire.child("Availability").setValue("Not Available");
                                                    mDatebaseHire.child("HiredBy").setValue(mAuth.getCurrentUser().getUid());
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                            Toast.makeText(getActivity() , "Caterer hired...We will contact you further details" , Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            };

                            mServiceList.setAdapter(firebaseRecyclerAdapter);

                            txt_caterer.setVisibility(View.VISIBLE);
                            txt_photo.setVisibility(View.INVISIBLE);


            }
        });

        return rootView;
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder{

        View mView ;
        Button btn_hire ;

        public ServiceViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;
            btn_hire = (Button)mView.findViewById(R.id.btn_hire);
        }

        public void setName (String name){
            TextView txt_Name = (TextView) mView.findViewById(R.id.txt_serviceProviderName);
            txt_Name.setText(name);
        }
        public void setPhone (String phone){
            TextView txt_Email = (TextView) mView.findViewById(R.id.txt_serviceProviderEmail);
            txt_Email.setText(phone);
        }
        public void setCharges (String charges){
            TextView txt_Price = (TextView) mView.findViewById(R.id.txt_serviceProviderPrice);
            txt_Price.setText(charges);
        }
    }
}
