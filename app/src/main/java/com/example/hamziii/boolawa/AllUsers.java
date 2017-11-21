package com.example.hamziii.boolawa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AllUsers extends AppCompatActivity {

    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListner ;

    private RecyclerView mUsersList ;
    private DatabaseReference mDatabase ;
    private FirebaseUser mCurrentUser ;

    private Query mQueryUser ;

    private String user_id , currentUser;

    private EditText edit_search ;
    private Button btn_search ;

    private String searchString ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        mUsersList = (RecyclerView)findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();

        //currentUser = mAuth.getCurrentUser().getDisplayName();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser() ;


        edit_search = (EditText)findViewById(R.id.et_searchUser);
        btn_search = (Button)findViewById(R.id.btn_searchUser);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                searchString = edit_search.getText().toString().toLowerCase();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                mQueryUser = mDatabase.orderByChild("UserName").startAt(searchString).endAt(searchString + "~");

                FirebaseRecyclerAdapter<Users , UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                        Users.class ,
                        R.layout.users_row ,
                        UsersViewHolder.class ,
                        mQueryUser
                ) {
                    @Override
                    protected void populateViewHolder(UsersViewHolder viewHolder, final Users model, int position) {

                        final String user_id = getRef(position).getKey();

                        viewHolder.setUserName(model.getUserName());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent singleProduct = new Intent(AllUsers.this , SingleUser.class);
                                singleProduct.putExtra("userName" , model.getUserName());
                                singleProduct.putExtra("user_id" , user_id);

                                startActivity(singleProduct);
                            }
                        });



                    }
                };
                mUsersList.setAdapter(firebaseRecyclerAdapter);
            }
        });



        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null) {
                    Intent LoginIntent = new Intent(AllUsers.this , LoginActivity.class);
                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(LoginIntent);
                }

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users , UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                Users.class ,
                R.layout.users_row ,
                UsersViewHolder.class ,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, final Users model, int position) {

                final String user_id = getRef(position).getKey();

                viewHolder.setUserName(model.getUserName());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent singleProduct = new Intent(AllUsers.this , SingleUser.class);
                        singleProduct.putExtra("userName" , model.getUserName());
                        singleProduct.putExtra("user_id" , user_id);

                        startActivity(singleProduct);
                    }
                });



            }
        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setUserName(String userName){
            TextView post_UserName = (TextView)mView.findViewById(R.id.txt_userName);
            post_UserName.setText(userName);
        }
    }
}
