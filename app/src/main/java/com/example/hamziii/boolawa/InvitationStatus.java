package com.example.hamziii.boolawa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class InvitationStatus extends AppCompatActivity {

    private RecyclerView mcreatedEvent_list ;

    private DatabaseReference mDatabase ;
    private FirebaseAuth mAuth ;

    private Query mQueryCurrentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_status);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CreatedEvent");
        mQueryCurrentUser = mDatabase.orderByChild("CreatedBy").equalTo(mAuth.getCurrentUser().getUid());

        mcreatedEvent_list = (RecyclerView)findViewById(R.id.userInvitationstatus_list);
        mcreatedEvent_list.setHasFixedSize(true);
        mcreatedEvent_list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users , CreatedEventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, CreatedEventViewHolder>(

                Users.class ,
                R.layout.user_invitation_row ,
                CreatedEventViewHolder.class ,
                mQueryCurrentUser
        ) {
            @Override
            protected void populateViewHolder(final CreatedEventViewHolder viewHolder, final Users model, int position) {

                final String card_key = getRef(position).getKey();
                viewHolder.setInvitationCard(getApplicationContext() , model.getInvitationCard());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(InvitationStatus.this  , SingleInvitationStatus.class);
                        intent.putExtra("CardKey" , card_key);
                        startActivity(intent);
                    }
                });

            }
        };
        mcreatedEvent_list.setAdapter(firebaseRecyclerAdapter);

    }

    public static class CreatedEventViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public CreatedEventViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setInvitationCard(Context ctx , String invitationCard){
            ImageView post_eventImg = (ImageView)mView.findViewById(R.id.post_createdEventImg);
            Glide.with(ctx).load(invitationCard).into(post_eventImg);
        }
    }
}
