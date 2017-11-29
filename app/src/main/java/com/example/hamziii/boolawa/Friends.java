package com.example.hamziii.boolawa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Friends extends AppCompatActivity {

    private RecyclerView mFriendsList , mFriendReqList ;
    private DatabaseReference mDatabase , mDatabaseCurrentUsers , mDatabaseFriendReq , mDatabaseCurrentReqUsers;
    private FirebaseAuth mAuth ;

    private Button btn_friends , btn_friendReq ;

    //only received request can show , height of both view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mFriendReqList = (RecyclerView)findViewById(R.id.friendReq_list);
        mFriendReqList.setHasFixedSize(true);
        mFriendReqList.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();

        btn_friends = (Button)findViewById(R.id.btn_friends);
        btn_friendReq = (Button)findViewById(R.id.btn_friendsReq);

        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
                mDatabaseCurrentUsers = mDatabase.child(mAuth.getCurrentUser().getUid());

                FirebaseRecyclerAdapter<Users , FriendsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, FriendsViewHolder>(

                        Users.class ,
                        R.layout.friends_row ,
                        FriendsViewHolder.class ,
                        mDatabaseCurrentUsers
                ) {
                    @Override
                    protected void populateViewHolder(FriendsViewHolder viewHolder, final Users model, int position) {

                        final String user_id = getRef(position).getKey();


                        viewHolder.setFriendName(model.getFriendName());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent singleProduct = new Intent(Friends.this , SingleUser.class);
                                singleProduct.putExtra("userName" , model.getFriendName());
                                singleProduct.putExtra("user_id" , user_id);

                                startActivity(singleProduct);
                            }
                        });


                    }
                };
                mFriendReqList.setAdapter(firebaseRecyclerAdapter);


            }
        });

        btn_friendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseFriendReq = FirebaseDatabase.getInstance().getReference().child("Friend_req");
                mDatabaseCurrentReqUsers = mDatabaseFriendReq.child(mAuth.getCurrentUser().getUid());

                FirebaseRecyclerAdapter<Users , FriendReqViewHolder> firebaseRecyclerAdapter1 = new FirebaseRecyclerAdapter<Users, FriendReqViewHolder>(

                        Users.class ,
                        R.layout.friend_req_row ,
                        FriendReqViewHolder.class ,
                        mDatabaseCurrentReqUsers
                ) {
                    @Override
                    protected void populateViewHolder(final FriendReqViewHolder viewHolder, final Users model, int position) {

                        final String user_id = getRef(position).getKey();

                        viewHolder.setSent_By(model.getSent_By());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent singleProduct = new Intent(Friends.this , SingleUser.class);
                                singleProduct.putExtra("userName" , model.getSent_By());
                                singleProduct.putExtra("user_id" , user_id);

                                startActivity(singleProduct);
                            }
                        });

                        /*mDatabaseCurrentReqUsers.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("Sent_By")){


                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/

                /*viewHolder.setSent_By(model.getSent_By());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent singleProduct = new Intent(Friends.this , SingleUser.class);
                        singleProduct.putExtra("userName" , model.getUserName());
                        singleProduct.putExtra("user_id" , user_id);

                        startActivity(singleProduct);
                    }
                });*/



                    }
                };
                mFriendReqList.setAdapter(firebaseRecyclerAdapter1);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setFriendName(String friendName){
            TextView post_friendName = (TextView)mView.findViewById(R.id.txt_friend);
            post_friendName.setText(friendName);
        }
    }

    public static class FriendReqViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public FriendReqViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setSent_By(String sent_by){
            TextView post_sent_by = (TextView)mView.findViewById(R.id.txt_friend_Req);
            post_sent_by.setText(sent_by);
        }
    }
}
