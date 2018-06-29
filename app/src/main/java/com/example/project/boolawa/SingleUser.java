package com.example.project.boolawa;

import android.icu.text.DateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class SingleUser extends AppCompatActivity {

    private String userName , userId;
    private TextView txt_userName ;
    private Button btn_sendReq , btn_declineReq ;

    private String mCurrent_State ;
    private String currentDate ;
    private String mCurrentUserName ;

    private DatabaseReference mFriendReqDatabase ;
    private DatabaseReference mFriendDatabase , mDatabaseCurrentUserName ;
    private FirebaseUser mCurrentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseCurrentUserName = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid()).child("UserName");

        mDatabaseCurrentUserName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mCurrentUserName = dataSnapshot.getValue().toString() ;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userName = getIntent().getExtras().getString("userName");
        userId = getIntent().getExtras().getString("user_id");



        Toast.makeText(getApplicationContext() , userId , Toast.LENGTH_LONG).show();

        mFriendReqDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(userId)){

                    String req_type = dataSnapshot.child(userId).child("request_type").getValue().toString();

                    if(req_type.equals("received")){

                        mCurrent_State = "req_received" ;
                        btn_sendReq.setText("Accept Friend Request");

                        btn_declineReq.setVisibility(View.VISIBLE);
                        btn_declineReq.setEnabled(true);
                    }
                    else if(req_type.equals("sent")){
                        mCurrent_State = "req_sent" ;
                        btn_sendReq.setText("Cancel Request");

                        btn_declineReq.setVisibility(View.INVISIBLE);
                        btn_declineReq.setEnabled(false);

                    }

                    else {
                        btn_declineReq.setVisibility(View.INVISIBLE);
                        btn_declineReq.setEnabled(false);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCurrent_State = "not_friends";

        txt_userName = (TextView)findViewById(R.id.txt_singleUserName);
        btn_sendReq = (Button)findViewById(R.id.btn_sendReq);
        btn_declineReq = (Button)findViewById(R.id.btn_declineReq);

        txt_userName.setText(userName);

        mFriendDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(userId)){

                    mCurrent_State = "friends" ;
                    btn_sendReq.setText("UnFriend " + userName);

                    btn_declineReq.setVisibility(View.INVISIBLE);
                    btn_declineReq.setEnabled(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(mCurrentUser.getUid().equals(userId)){

            btn_declineReq.setVisibility(View.INVISIBLE);
            btn_declineReq.setEnabled(false);

            btn_sendReq.setVisibility(View.INVISIBLE);
            btn_sendReq.setEnabled(false);
        }

        else {

            btn_declineReq.setVisibility(View.VISIBLE);
            btn_declineReq.setEnabled(true);

            btn_sendReq.setVisibility(View.VISIBLE);
            btn_sendReq.setEnabled(true);
        }

        if(mCurrent_State.equals("not_friends")){

            btn_declineReq.setVisibility(View.INVISIBLE);
            btn_declineReq.setEnabled(false);
        }

        btn_sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_sendReq.setEnabled(false);

                if(mCurrent_State.equals("not_friends")){

                    mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {


                                        mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).child("Sent_By").setValue(mCurrentUserName).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mCurrent_State = "req_sent" ;
                                                btn_sendReq.setText("Cancel Request");

                                                btn_declineReq.setVisibility(View.INVISIBLE);
                                                btn_declineReq.setEnabled(false);

                                                Toast.makeText(getApplicationContext() , "Request Sent!!!" , Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                });

                            }
                            else {

                                Toast.makeText(getApplicationContext() , "Failed Sending Request" , Toast.LENGTH_LONG).show();
                            }

                            btn_sendReq.setEnabled(true);
                        }
                    });
                }


                if(mCurrent_State.equals("req_sent")){

                    mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    btn_sendReq.setEnabled(true);
                                    mCurrent_State = "not_friends" ;
                                    btn_sendReq.setText("Send Friend Request");

                                    btn_declineReq.setVisibility(View.INVISIBLE);
                                    btn_declineReq.setEnabled(false);

                                }
                            });
                        }
                    });
                }

                if(mCurrent_State.equals("req_received")){

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        currentDate = DateFormat.getDateTimeInstance().format(new Date());
                    }
                    mFriendDatabase.child(mCurrentUser.getUid()).child(userId).child("friendsSince").setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendDatabase.child(mCurrentUser.getUid()).child(userId).child("friendName").setValue(userName);

                            mFriendDatabase.child(userId).child(mCurrentUser.getUid()).child("friendsSince").setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    //mFriendDatabase.child(userId).child(mCurrentUser.getUid()).child("friendsName").setValue(mDatabaseCurrentUserName.child("UserName"));

                                    mDatabaseCurrentUserName.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            mFriendDatabase.child(userId).child(mCurrentUser.getUid()).child("friendName").setValue(dataSnapshot.getValue());
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    btn_sendReq.setEnabled(true);
                                                    mCurrent_State = "friends" ;
                                                    btn_sendReq.setText("UnFriend " + userName);

                                                    btn_declineReq.setVisibility(View.INVISIBLE);
                                                    btn_declineReq.setEnabled(false);

                                                }
                                            });
                                        }
                                    });

                                }
                            });

                        }
                    });

                }

                if(mCurrent_State.equals("friends")){

                    mFriendDatabase.child(mCurrentUser.getUid()).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendDatabase.child(userId).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    btn_sendReq.setEnabled(true);
                                    mCurrent_State = "not_friends" ;
                                    btn_sendReq.setText("Send Friend Request");
                                }
                            });
                        }
                    });
                }
            }
        });

        btn_declineReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrent_State.equals("req_received")){

                    mFriendReqDatabase.child(mCurrentUser.getUid()).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendReqDatabase.child(userId).child(mCurrentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    btn_sendReq.setEnabled(true);
                                    mCurrent_State = "not_friends" ;
                                    btn_sendReq.setText("Send Friend Request");

                                    btn_declineReq.setVisibility(View.INVISIBLE);
                                    btn_declineReq.setEnabled(false);

                                }
                            });
                        }
                    });
                }
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();


    }
}
