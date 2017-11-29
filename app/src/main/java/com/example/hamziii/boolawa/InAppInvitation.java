package com.example.hamziii.boolawa;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InAppInvitation extends AppCompatActivity {

    private String cardKey ;
    private String user_id ;

    private RecyclerView mInvitationList;
    private DatabaseReference mDatabase , mCurrentUserDatabase , mInvitedUsersDatabase ;
    private FirebaseAuth mAuth ;

    FirebaseUser mCurrentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_invitation);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");

        mCurrentUser = mAuth.getCurrentUser();
        mCurrentUserDatabase = mDatabase.child(mCurrentUser.getUid());

        mInvitationList = (RecyclerView)findViewById(R.id.invitation_list);
        mInvitationList.setHasFixedSize(true);
        mInvitationList.setLayoutManager(new LinearLayoutManager(this));

        cardKey = getIntent().getExtras().getString("CardKey");


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users , InvitationViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, InvitationViewHolder>(

                Users.class ,
                R.layout.invitation_row ,
                InvitationViewHolder.class ,
                mCurrentUserDatabase
        ) {
            @Override
            protected void populateViewHolder(final InvitationViewHolder viewHolder, final Users model, final int position) {

                String id = getRef(position).getKey();

                viewHolder.setFriendName(model.getFriendName());
                viewHolder.setBtnText(id , cardKey);

                Toast.makeText(getApplicationContext() , id, Toast.LENGTH_LONG).show();

                viewHolder.btn_sendInvitation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        user_id = getRef(position).getKey();
                        mInvitedUsersDatabase = FirebaseDatabase.getInstance().getReference().child("CreatedEvent").child(cardKey);
                        mInvitedUsersDatabase.child(user_id).setValue("Invited");
                        //viewHolder.btn_sendInvitation.setText("Invitation sent");

                    }
                });

            }
        };
        mInvitationList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class InvitationViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Button btn_sendInvitation ;
        DatabaseReference mDatabase , mDatabaseUsers ;

        public InvitationViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mDatabase = FirebaseDatabase.getInstance().getReference().child("CreatedEvent");

            btn_sendInvitation = (Button)mView.findViewById(R.id.btn_sendInvitaion);
        }

        public void setFriendName(String friendName){
            TextView post_sendInvitation = (TextView)mView.findViewById(R.id.txt_sendInvitation);
            post_sendInvitation.setText(friendName);
        }

        public void setBtnText(final String id , final String cardKey){

            //mDatabaseUsers = mDatabase.child(user_id) ;

            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.child(cardKey).hasChild(id)){

                        btn_sendInvitation.setText("Invitation Sent");
                    }
                    else {

                        btn_sendInvitation.setText("Send Invitation");
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
        }
    }
}
