package com.example.hamziii.boolawa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mAni on 02/10/2017.
 */

public class FragmentNavigate extends Fragment {

    private ImageView img_add_btn ;
    private EditText et_picDetail ;
    private Button btn_sharePic ;
    private RecyclerView mMemories_list ;

    private Uri imageUri = null ;

    private static final int GALLERY_REQUEST = 1 ;

    private StorageReference mStorage ;
    private DatabaseReference mDatabse , mDatabaseFriends , mDatabaseCurrentUser ;
    private FirebaseAuth mAuth ;
    private ProgressDialog mProgress ;

    private String mUploaderId ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigate, container, false);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabse = FirebaseDatabase.getInstance().getReference().child("EventPics");
        mDatabaseFriends = FirebaseDatabase.getInstance().getReference().child("Friends");
        mDatabaseCurrentUser = mDatabaseFriends.child(mAuth.getCurrentUser().getUid());

        mProgress = new ProgressDialog(getActivity());

        img_add_btn = (ImageView)rootView.findViewById(R.id.img_btn_add);
        et_picDetail = (EditText)rootView.findViewById(R.id.txt_picDetail);
        btn_sharePic = (Button)rootView.findViewById(R.id.btn_share);
        mMemories_list = (RecyclerView)rootView.findViewById(R.id.memories_list);
        mMemories_list.setHasFixedSize(true);
        mMemories_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        img_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent , GALLERY_REQUEST);
            }
        });

        btn_sharePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPosting();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users , MemoriesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, MemoriesViewHolder>(

                Users.class ,
                R.layout.memories_row ,
                MemoriesViewHolder.class ,
                mDatabse
        ) {
            @Override
            protected void populateViewHolder(final MemoriesViewHolder viewHolder, final Users model, int position) {

                //product_key = getRef(position).getKey();

                final String uploaderId = model.getUploaderId() ;

                mDatabaseCurrentUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(uploaderId).hasChild("friendName")){

                            viewHolder.setCaption(model.getCaption());
                            viewHolder.setEventImage(getActivity() , model.getEventImage());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        mMemories_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MemoriesViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageView postImage_line , postSale_img ;
        Context context ;

        public MemoriesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setCaption(String caption){
            TextView post_caption = (TextView)mView.findViewById(R.id.post_caption);
            post_caption.setText(caption);
        }
        public void setEventImage(Context ctx , String eventImage){
            ImageView post_eventImg = (ImageView)mView.findViewById(R.id.post_eventImg);
            Glide.with(ctx).load(eventImage).into(post_eventImg);
        }
    }

    private void startPosting() {

        final String title_string = et_picDetail.getText().toString().toLowerCase().trim();

        if(!TextUtils.isEmpty(title_string) && imageUri != null){

            mProgress.setMessage("Posting Image...");
            mProgress.show();

            StorageReference filePath = mStorage.child("EventPictures").child(imageUri.getLastPathSegment());
            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") final Uri downlaodUrl = taskSnapshot.getDownloadUrl();
                    final DatabaseReference newProduct = mDatabse.push();

                    mDatabse.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newProduct.child("Caption").setValue(title_string);
                            newProduct.child("EventImage").setValue(downlaodUrl.toString());
                            newProduct.child("UploaderId").setValue(mAuth.getCurrentUser().getUid());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mProgress.dismiss();
                    et_picDetail.setText("");
                    imageUri = null ;
                }
            });

        }
        else {
            Toast.makeText(getActivity() , "Add Image and Caption" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            img_add_btn.setImageURI(imageUri);
        }
    }



    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(5);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
