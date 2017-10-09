package com.example.hamziii.boolawa;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by mAni on 02/10/2017.
 */

public class FragmentViewInvitation extends Fragment {

    private RecyclerView minvitationList ;

    private DatabaseReference mDatabase ;
    private Query mQueryCategory ;
    private FirebaseAuth mAuth ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_invitation, container, false);

        mAuth = FirebaseAuth.getInstance() ;
        minvitationList = (RecyclerView) rootView.findViewById(R.id.invitationList);
        minvitationList.setHasFixedSize(true);
        minvitationList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserInvitation");
        mQueryCategory = mDatabase.orderByChild("UserId").equalTo(mAuth.getCurrentUser().getUid());

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
            protected void populateViewHolder(InvitationViewHolder viewHolder, Users model, int position) {


                viewHolder.setImage(getActivity() , model.getImage());

            }
        };

        minvitationList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class InvitationViewHolder extends RecyclerView.ViewHolder{

        View mView ;

        public InvitationViewHolder(View itemView) {
            super(itemView);

            mView = itemView ;
        }


        public void setImage (Context ctx , String image){
            ImageView accessories_post_img = (ImageView)mView.findViewById(R.id.user_invitaionCard);
            Glide.with(ctx).load(image).into(accessories_post_img);
        }


    }
}
