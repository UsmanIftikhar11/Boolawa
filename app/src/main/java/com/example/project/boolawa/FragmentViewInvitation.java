package com.example.project.boolawa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

    private DatabaseReference mDatabase , mDatabaseCard , mDatabaseUsers ;
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

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CreatedEvent");
        mQueryCategory = mDatabase.orderByChild(mAuth.getCurrentUser().getUid()).equalTo("Invited");

        mDatabase.keepSynced(true);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

                    FirebaseRecyclerAdapter<Users , InvitationViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, InvitationViewHolder>(

                            Users.class ,
                            R.layout.user_invitation_row ,
                            InvitationViewHolder.class ,
                            mQueryCategory

                    ) {
                        @Override
                        protected void populateViewHolder(final InvitationViewHolder viewHolder, final Users model, int position) {

                            cardId = getRef(position).getKey();
                            mDatabaseCard = FirebaseDatabase.getInstance().getReference().child("InvitationStatus").child(cardId).child(mAuth.getCurrentUser().getUid());
                            viewHolder.setInvitationCard(getActivity() , model.getInvitationCard());


                        }
                    };

                    minvitationList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class InvitationViewHolder extends RecyclerView.ViewHolder{

        View mView ;
        DatabaseReference mDatabaseCard ;
        FirebaseAuth mAuth ;

        public InvitationViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;

             mAuth = FirebaseAuth.getInstance();
        }


        public void setInvitationCard (Context ctx , String invitationCard){
            ImageView accessories_post_img = (ImageView)mView.findViewById(R.id.post_createdEventImg);
            Glide.with(ctx).load(invitationCard).into(accessories_post_img);
        }

    }
}
