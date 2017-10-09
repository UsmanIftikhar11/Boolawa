package com.example.hamziii.boolawa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mAni on 02/10/2017.
 */

public class FragmentSendInvitation extends Fragment {

    private ImageView addImage ;
    private Button sendGmail , sendWhatsapp ;

    private Uri imageUri = null ;

    private static final int GALLERY_REQUEST = 1 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_send_invitation, container, false);

        addImage = (ImageView)rootView.findViewById(R.id.add_btn);
        sendGmail = (Button)rootView.findViewById(R.id.btn_sendGmail);
        sendWhatsapp = (Button)rootView.findViewById(R.id.btn_sendWhatsapp);

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

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            addImage.setImageURI(imageUri);
        }
    }
}
