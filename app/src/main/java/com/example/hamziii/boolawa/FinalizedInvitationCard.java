package com.example.hamziii.boolawa;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FinalizedInvitationCard extends AppCompatActivity {

    public String eventTitle , eventPersonName , eventDate , eventTime , eventAdress , eventHostedBy ;
    private int imgSampleNo ;

    private TextView txt_eventTitle1 , txt_eventDate1 , txt_eventTime1 , txt_eventAdress1 , txt_eventHostedBy1  , txt_eventPersonName1;

    private TextView txt_eventTitle2 , txt_eventDate2 , txt_eventTime2 , txt_eventAdress2 , txt_eventHostedBy2  , txt_eventPersonName2;

    private TextView txt_eventTitle3 , txt_eventDateTime3 , txt_eventAdress3 , txt_eventHostedBy3 , txt_eventPersonName3 , txt_check;

    private Button btn_saveCard1 , btn_editCard1 , btn_saveCard2 , btn_editCard2 , btn_saveCard3 , btn_editCard3;

    RelativeLayout invitationLayout1 , abc;

    private View main ;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventTitle = getIntent().getExtras().getString("EventTitle") ;
        eventPersonName = getIntent().getExtras().getString("EventPersonName") ;
        eventDate = getIntent().getExtras().getString("EventDate") ;
        eventTime = getIntent().getExtras().getString("EventTime") ;
        eventAdress = getIntent().getExtras().getString("EventAdress") ;
        eventHostedBy = getIntent().getExtras().getString("EventHostedBy") ;
        imgSampleNo = getIntent().getExtras().getInt("imgSampleNo");

        abc = (RelativeLayout)findViewById(R.id.abc);
        invitationLayout1 = (RelativeLayout)findViewById(R.id.layout_invitation1);

        switch (imgSampleNo){
            case 1 :
                setContentView(R.layout.invitationcard1);
                txt_eventTitle1 = (TextView)findViewById(R.id. txt_event_title1) ;
                txt_eventDate1 = (TextView)findViewById(R.id.txt_event_date1) ;
                txt_eventTime1 = (TextView)findViewById(R.id.txt_event_time1) ;
                txt_eventAdress1 = (TextView)findViewById(R.id.txt_event_adress1) ;
                btn_saveCard1 = (Button)findViewById(R.id.btn_saveCard1);
                btn_editCard1 = (Button)findViewById(R.id.btn_editCard1);

                imageView = (ImageView)findViewById(R.id.imageView);

                txt_eventTitle1.setText(eventTitle);
                txt_eventDate1.setText(eventDate);
                txt_eventTime1.setText(eventTime);
                txt_eventAdress1.setText(eventAdress);

                btn_saveCard1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View view = findViewById(R.id.layout_invitation1) ;
                        view.setDrawingCacheEnabled(true);
                        view.buildDrawingCache(true);
                        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
                        view.setDrawingCacheEnabled(false);
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
                        String imgName = timeStamp + ".jpg";
                        //imageView.setImageBitmap(b);
                        //saveMyImage(b);
                        File filename = null;
                        try {
                            String path1 = android.os.Environment.getExternalStorageDirectory()
                                    .toString();
                            Log.i("in save()", "after mkdir");
                            File file = new File(path1 + "/" + "Boolawa");
                            if (!file.exists())
                                file.mkdirs();
                            filename = new File(file.getAbsolutePath() + "/" + "img1" + ".jpg");
                            Log.i("in save()", "after file");
                            FileOutputStream out = new FileOutputStream(filename);
                            Log.i("in save()", "after outputstream");
                            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                            Log.i("in save()", "after outputstream closed");
                            //File parent = filename.getParentFile();
                            ContentValues image = getImageContent(filename);
                            Uri result = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
                            Toast.makeText(getApplicationContext(),
                                    "File is Saved in  " + filename, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File f = new File(String.valueOf(filename));
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);


                    }

                });

                btn_editCard1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FinalizedInvitationCard.this , EventDetailsFromUser.class);
                        intent.putExtra("imageNo" , imgSampleNo);
                        startActivity(intent);
                    }
                });
                break;
            case 2:
                setContentView(R.layout.invitationcard2);
                txt_eventTitle2 = (TextView)findViewById(R.id.txt_event_title2) ;
                txt_eventDate2 = (TextView)findViewById(R.id.txt_event_date2) ;
                txt_eventTime2 = (TextView)findViewById(R.id.txt_eventtime2) ;
                txt_eventAdress2 = (TextView)findViewById(R.id.txt_event_adress2) ;
                txt_eventHostedBy2 = (TextView)findViewById(R.id.txt_event_HostedBy2) ;
                btn_saveCard2 = (Button)findViewById(R.id.btn_saveCard2);
                btn_editCard2 = (Button)findViewById(R.id.btn_editCard2);

                txt_eventTitle2.setText(eventTitle);
                txt_eventDate2.setText(eventDate);
                txt_eventTime2.setText(eventTime);
                txt_eventAdress2.setText(eventAdress);
                txt_eventHostedBy2.setText(eventHostedBy);

                btn_saveCard2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View view = findViewById(R.id.layout_invitation2) ;
                        view.setDrawingCacheEnabled(true);
                        view.buildDrawingCache(true);
                        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
                        view.setDrawingCacheEnabled(false);
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
                        String imgName = timeStamp + ".jpg";
                        //imageView.setImageBitmap(b);
                        //saveMyImage(b);
                        File filename = null;
                        try {
                            String path1 = android.os.Environment.getExternalStorageDirectory()
                                    .toString();
                            Log.i("in save()", "after mkdir");
                            File file = new File(path1 + "/" + "Boolawa");
                            if (!file.exists())
                                file.mkdirs();
                            filename = new File(file.getAbsolutePath() + "/" + "img1" + ".jpg");
                            Log.i("in save()", "after file");
                            FileOutputStream out = new FileOutputStream(filename);
                            Log.i("in save()", "after outputstream");
                            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                            Log.i("in save()", "after outputstream closed");
                            //File parent = filename.getParentFile();
                            ContentValues image = getImageContent(filename);
                            Uri result = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
                            Toast.makeText(getApplicationContext(),
                                    "File is Saved in  " + filename, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File f = new File(String.valueOf(filename));
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                    }
                });
                btn_editCard2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FinalizedInvitationCard.this , EventDetailsFromUser.class);
                        intent.putExtra("imageNo" , imgSampleNo);
                        startActivity(intent);
                    }
                });
                break;
            case 3 :
                setContentView(R.layout.invitationcard3);
                txt_eventTitle3 = (TextView)findViewById(R.id.txt_event_title3) ;
                txt_eventDateTime3 = (TextView)findViewById(R.id.txt_event_dateTime) ;
                txt_eventAdress3 = (TextView)findViewById(R.id.txt_event_adress3) ;
                txt_eventHostedBy3 = (TextView)findViewById(R.id.txt_event_HostedBy3) ;
                txt_eventPersonName3 = (TextView)findViewById(R.id.txt_event_personName) ;
                btn_saveCard3 = (Button)findViewById(R.id.btn_saveCard3);
                btn_editCard3 = (Button)findViewById(R.id.btn_editCard3);

                txt_eventTitle3.setText(eventTitle);
                txt_eventDateTime3.setText(eventDate + "\n" + eventTime);
                txt_eventAdress3.setText(eventAdress);
                txt_eventHostedBy3.setText(eventHostedBy);
                txt_eventPersonName3.setText(eventPersonName);

                btn_saveCard3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = findViewById(R.id.layout_invitation3) ;
                        view.setDrawingCacheEnabled(true);
                        view.buildDrawingCache(true);
                        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
                        view.setDrawingCacheEnabled(false);
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
                        String imgName = timeStamp + ".jpg";
                        //imageView.setImageBitmap(b);
                        //saveMyImage(b);
                        File filename = null;
                        try {
                            String path1 = android.os.Environment.getExternalStorageDirectory()
                                    .toString();
                            Log.i("in save()", "after mkdir");
                            File file = new File(path1 + "/" + "Boolawa");
                            if (!file.exists())
                                file.mkdirs();
                            filename = new File(file.getAbsolutePath() + "/" + "img1" + ".jpg");
                            Log.i("in save()", "after file");
                            FileOutputStream out = new FileOutputStream(filename);
                            Log.i("in save()", "after outputstream");
                            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();
                            Log.i("in save()", "after outputstream closed");
                            //File parent = filename.getParentFile();
                            ContentValues image = getImageContent(filename);
                            Uri result = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
                            Toast.makeText(getApplicationContext(),
                                    "File is Saved in  " + filename, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File f = new File(String.valueOf(filename));
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                    }
                });

                btn_editCard3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FinalizedInvitationCard.this , EventDetailsFromUser.class);
                        intent.putExtra("imageNo" , imgSampleNo);
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    public ContentValues getImageContent(File parent) {
        ContentValues image = new ContentValues();
        image.put(MediaStore.Images.Media.TITLE, "Boolawa");
        image.put(MediaStore.Images.Media.DISPLAY_NAME, "img1");
        image.put(MediaStore.Images.Media.DESCRIPTION, "App Image");
        image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        image.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        image.put(MediaStore.Images.Media.ORIENTATION, 0);
        image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString()
                .toLowerCase().hashCode());
        image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
                .toLowerCase());
        image.put(MediaStore.Images.Media.SIZE, parent.length());
        image.put(MediaStore.Images.Media.DATA, parent.getAbsolutePath());
        return image;
    }
}
