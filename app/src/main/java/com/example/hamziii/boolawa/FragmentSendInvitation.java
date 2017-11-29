package com.example.hamziii.boolawa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mAni on 02/10/2017.
 */

public class FragmentSendInvitation extends Fragment {

    private ImageView addImage ;
    private Button sendGmail , sendWhatsapp , btn_inApp ;

    private Uri imageUri = null ;

    private static final int GALLERY_REQUEST = 1 ;

    private StorageReference mStorage ;
    private DatabaseReference mDatabse ;
    private FirebaseAuth mAuth ;
    private ProgressDialog mProgress ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_send_invitation, container, false);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabse = FirebaseDatabase.getInstance().getReference().child("CreatedEvent");

        mProgress = new ProgressDialog(getActivity());

        addImage = (ImageView)rootView.findViewById(R.id.add_btn);
        sendGmail = (Button)rootView.findViewById(R.id.btn_sendGmail);
        sendWhatsapp = (Button)rootView.findViewById(R.id.btn_sendWhatsapp);

        btn_inApp = (Button)rootView.findViewById(R.id.btn_inApp);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent , GALLERY_REQUEST);
            }
        });

        sendGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                emailIntent.setType("image/jpeg");
                emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(emailIntent, "Send your email in:"));

                /*Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("plain/text");
                emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"someone@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Yo");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi");
                startActivity(emailIntent);*/
            }
        });

        sendWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("image/jpg");
                sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }

        });

        btn_inApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPosting();
                /*Intent intent = new Intent(getActivity() , InAppInvitation.class);
                intent.putExtra("imageUri" , imageUri);
                startActivity(intent);*/
            }
        });

        return rootView;
    }

    private void startPosting() {

        if(imageUri != null){

            mProgress.setMessage("Posting Image...");
            mProgress.show();

            StorageReference filePath = mStorage.child("InvitationCards").child(imageUri + random());
            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") final Uri downlaodUrl = taskSnapshot.getDownloadUrl();
                    final DatabaseReference newProduct = mDatabse.push();

                    mDatabse.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newProduct.child("InvitationCard").setValue(downlaodUrl.toString());
                            newProduct.child("CreatedBy").setValue(mAuth.getCurrentUser().getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    /*Intent intent = new Intent(getActivity() , UserCreatedInvitations.class);
                                    startActivity(intent);*/

                                    Toast.makeText(getActivity() , "Event Created" , Toast.LENGTH_LONG).show();

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mProgress.dismiss();
                    imageUri = null ;
                }
            });

        }
        else {
            Toast.makeText(getActivity() , "Must select an invitation card" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            addImage.setImageURI(imageUri);
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
