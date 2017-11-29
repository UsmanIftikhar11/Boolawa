package com.example.hamziii.boolawa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingleInvitationStatus extends AppCompatActivity {

    private String cardKey ;

    private RecyclerView mFriendsList , mSingleStatusList ;
    private DatabaseReference mDatabaseInvitationStatus ;
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_invitation_status);

        cardKey = getIntent().getExtras().getString("CardKey");

        mSingleStatusList = (RecyclerView)findViewById(R.id.singleStatus_list);
        mSingleStatusList.setHasFixedSize(true);
        mSingleStatusList.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseInvitationStatus = FirebaseDatabase.getInstance().getReference().child("InvitationStatus").child(cardKey);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users , StatusViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, StatusViewHolder>(

                Users.class ,
                R.layout.invitation_status_row ,
                StatusViewHolder.class ,
                mDatabaseInvitationStatus
        ) {
            @Override
            protected void populateViewHolder(StatusViewHolder viewHolder, final Users model, int position) {

                final String user_id = getRef(position).getKey();


                viewHolder.setConfirmation(model.getConfirmation());
                viewHolder.setInvitationTo(model.getInvitationTo());



            }
        };
        mSingleStatusList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public StatusViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setInvitationTo(String invitationTo){
            TextView post_invitationTo = (TextView)mView.findViewById(R.id.txt_invitaionTo);
            post_invitationTo.setText(invitationTo);
        }

        public void setConfirmation(String confirmation){
            TextView post_confirmation = (TextView)mView.findViewById(R.id.txt_confirmation);
            post_confirmation.setText(confirmation);
        }
    }
}
