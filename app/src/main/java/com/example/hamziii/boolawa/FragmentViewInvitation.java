package com.example.hamziii.boolawa;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mAni on 02/10/2017.
 */

public class FragmentViewInvitation extends Fragment {

    private RecyclerView minvitationList ;

    private DatabaseReference mDatabase , mDatabaseCard ;
    private Query mQueryCategory ;
    private FirebaseAuth mAuth;

    String cardId ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_invitation, container, false);

        mAuth = FirebaseAuth.getInstance() ;
        minvitationList = (RecyclerView) rootView.findViewById(R.id.invitationList);
        minvitationList.setHasFixedSize(true);
        minvitationList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("CreatedEvent").child(mAuth.getCurrentUser().getUid());
        mQueryCategory = mDatabase.orderByChild("status").equalTo("Invited");

        mDatabase.keepSynced(true);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

                    FirebaseRecyclerAdapter<Users , InvitationViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, InvitationViewHolder>(

                            Users.class ,
                            R.layout.user_invitation ,
                            InvitationViewHolder.class ,
                            mQueryCategory

                    ) {
                        @Override
                        protected void populateViewHolder(final InvitationViewHolder viewHolder, final Users model, int position) {

                            cardId = getRef(position).getKey();
                            mDatabaseCard = FirebaseDatabase.getInstance().getReference().child("CreatedEvent").child(cardId).child(mAuth.getCurrentUser().getUid());
                            viewHolder.setInvitationCard(getActivity() , model.getInvitationCard());
                            //viewHolder.setbtnInvisible(cardId);

                            viewHolder.btn_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mDatabaseCard.child("confirmation").setValue("Yes");

                                }
                            });

                            viewHolder.btn_maybe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mDatabaseCard.child("confirmation").setValue("Maybe");
                                }
                            });

                            viewHolder.btn_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mDatabaseCard.child("confirmation").setValue("No");
                                }
                            });

                            /*mDatabaseCard.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.hasChild("status")){


                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/

                        }
                    };

                    minvitationList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class InvitationViewHolder extends RecyclerView.ViewHolder{

        View mView ;
        Button btn_yes , btn_no , btn_maybe ;
        DatabaseReference mDatabaseCard ;
        FirebaseAuth mAuth ;

        public InvitationViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;

             mAuth = FirebaseAuth.getInstance();

            btn_yes = (Button)mView.findViewById(R.id.btn_yes);
            btn_no = (Button)mView.findViewById(R.id.btn_no);
            btn_maybe = (Button)mView.findViewById(R.id.btn_maybe);
        }


        public void setInvitationCard (Context ctx , String invitationCard){
            ImageView accessories_post_img = (ImageView)mView.findViewById(R.id.user_invitaionCard);
            Glide.with(ctx).load(invitationCard).into(accessories_post_img);
        }

        /*public void setbtnInvisible(String id){

            mDatabaseCard = FirebaseDatabase.getInstance().getReference().child("CreatedEvent").child(id).child(mAuth.getCurrentUser().getUid());

            mDatabaseCard.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if(dataSnapshot.child("confirmation").getValue().toString().equals("Yes")){

                        btn_yes.setText("Going");

                        btn_no.setEnabled(false);
                        btn_no.setVisibility(View.INVISIBLE);

                        btn_maybe.setEnabled(false);
                        btn_maybe.setVisibility(View.INVISIBLE);

                    }

                    else if(dataSnapshot.child("confirmation").getValue().toString().equals("Maybe")){

                        btn_maybe.setText("Intrested");

                        btn_no.setEnabled(false);
                        btn_no.setVisibility(View.INVISIBLE);

                        btn_yes.setEnabled(false);
                        btn_yes.setVisibility(View.INVISIBLE);

                    }

                    else if(dataSnapshot.child("confirmation").getValue().toString().equals("No")){

                        btn_no.setText("Not Going");

                        btn_yes.setEnabled(false);
                        btn_yes.setVisibility(View.INVISIBLE);

                        btn_maybe.setEnabled(false);
                        btn_maybe.setVisibility(View.INVISIBLE);

                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }*/

    }
}
