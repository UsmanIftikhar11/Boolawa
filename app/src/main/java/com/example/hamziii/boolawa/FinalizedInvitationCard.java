package com.example.hamziii.boolawa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

import github.nisrulz.screenshott.ScreenShott;

public class FinalizedInvitationCard extends AppCompatActivity {

    public String eventTitle , eventPersonName , eventDate , eventTime , eventAdress , eventHostedBy ;
    private int imgSampleNo ;

    private TextView txt_eventTitle1 , txt_eventDate1 , txt_eventTime1 , txt_eventAdress1 , txt_eventHostedBy1  , txt_eventPersonName1;

    private TextView txt_eventTitle2 , txt_eventDate2 , txt_eventTime2 , txt_eventAdress2 , txt_eventHostedBy2  , txt_eventPersonName2;

    private TextView txt_eventTitle3 , txt_eventDateTime3 , txt_eventAdress3 , txt_eventHostedBy3 , txt_eventPersonName3 , txt_check;

    private Button btn_saveCard1 , btn_editCard1 , btn_saveCard2 , btn_editCard2 , btn_saveCard3 , btn_editCard3;

    RelativeLayout invitationLayout1 , abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_finalized_invitation_card);

        //txt_check = (TextView)findViewById(R.id.txt_check);


        eventTitle = getIntent().getExtras().getString("EventTitle") ;
        eventPersonName = getIntent().getExtras().getString("EventPersonName") ;
        eventDate = getIntent().getExtras().getString("EventDate") ;
        eventTime = getIntent().getExtras().getString("EventTime") ;
        eventAdress = getIntent().getExtras().getString("EventAdress") ;
        eventHostedBy = getIntent().getExtras().getString("EventHostedBy") ;
        imgSampleNo = getIntent().getExtras().getInt("imgSampleNo");

        invitationLayout1 = (RelativeLayout)findViewById(R.id.layout_invitation1);
        abc = (RelativeLayout)findViewById(R.id.abc);

        switch (imgSampleNo){

            case 1 :
                setContentView(R.layout.invitationcard1);

                /*eventTitle = getIntent().getExtras().getString("EventTitle").toString() ;
                eventPersonName = getIntent().getExtras().getString("EventPersonName").toString() ;
                eventDate = getIntent().getExtras().getString("EventDate").toString() ;
                eventTime = getIntent().getExtras().getString("EventTime").toString() ;
                eventAdress = getIntent().getExtras().getString("EventAdress").toString() ;
                eventHostedBy = getIntent().getExtras().getString("EventHostedBy").toString() ;
                imgSampleNo = getIntent().getExtras().getInt("imgSampleNo");*/
                break;
            case 2 :
                setContentView(R.layout.invitationcard2);
                break;
            case 3 :
                setContentView(R.layout.invitationcard3);
                break;
        }

        switch (imgSampleNo){
            case 1 :
                txt_eventTitle1 = (TextView)findViewById(R.id.txt_event_title1) ;
                txt_eventDate1 = (TextView)findViewById(R.id.txt_event_date1) ;
                txt_eventTime1 = (TextView)findViewById(R.id.txt_event_time1) ;
                txt_eventAdress1 = (TextView)findViewById(R.id.txt_event_adress1) ;
                btn_saveCard1 = (Button)findViewById(R.id.btn_saveCard1);
                btn_editCard1 = (Button)findViewById(R.id.btn_editCard1);

                txt_eventTitle1.setText(eventTitle);
                txt_eventDate1.setText(eventDate);
                txt_eventTime1.setText(eventTime);
                txt_eventAdress1.setText(eventAdress);

                btn_saveCard1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bitmap bitmap_hiddenview = ScreenShott.getInstance().takeScreenShotOfRootView(invitationLayout1);

                        //startSave();
                        /*invitationLayout1.post(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap image = takeImage(invitationLayout1) ;

                                try {
                                    if(image != null){
                                        saveImage(image);
                                        Toast.makeText(getApplicationContext() , "Saved" , Toast.LENGTH_LONG).show();
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });*/

                        /*RelativeLayout invitationLayout1 = (RelativeLayout)findViewById(R.id.layout_invitation1);
                        Bitmap bitmap = Bitmap.createBitmap(invitationLayout1.getWidth(), invitationLayout1.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas c = new Canvas(bitmap);
                        invitationLayout1.draw(c);
                        String a="/root/sdcard/Pictures/img0001.jpg";
                        File outputFile = new File(a); // Where to save it
                        //FileOutputStream out = new FileOutputStream(imageFile);
                        boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try{
                            RelativeLayout invitationLayout1 = (RelativeLayout)findViewById(R.id.abc);
                            Bitmap bitmap = Bitmap.createBitmap(invitationLayout1.getMeasuredWidth(), invitationLayout1.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                            Canvas c = new Canvas(bitmap);
                            invitationLayout1.draw(c);

                            ByteArrayOutputStream bao = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , bao);

                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "invitation.png");
                            file.createNewFile();

                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(bao.toByteArray());
                            fos.close();
                            fos.flush();

                            Toast.makeText(getApplicationContext() , "Card Saved..." , Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                            e.printStackTrace();
                        }*/


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
                        RelativeLayout shareLayout1 = (RelativeLayout)findViewById(R.id.layout_invitation1);
                        //shareLayout1.setDrawingCacheEnabled(false);
                        shareLayout1.buildDrawingCache();
                        Bitmap bm1 = shareLayout1.getDrawingCache();
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(bm1));
                            bm1.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception e) {
                            // TODO: handle exception
                        } finally {
                            shareLayout1.destroyDrawingCache();
                        }
                        //ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
                        //bm1.compress(Bitmap.CompressFormat.JPEG, 100, bytes1);
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
                        RelativeLayout shareLayout2 = (RelativeLayout)findViewById(R.id.layout_invitation1);
                        shareLayout2.setDrawingCacheEnabled(true);
                        shareLayout2.buildDrawingCache();
                        Bitmap bm2 = shareLayout2.getDrawingCache();
                        ByteArrayOutputStream bytes2 = new ByteArrayOutputStream();
                        bm2.compress(Bitmap.CompressFormat.JPEG, 100, bytes2);
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

    public static Bitmap viewToBitmap(View view , int width , int height){

        Bitmap bitmap = Bitmap.createBitmap(width , height , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap ;
    }

    public void startSave(){

        FileOutputStream fileOutputStream = null ;
        File file = getDisc();
        if(!file.exists() && !file.mkdirs()){
            Toast.makeText(this , "Error" , Toast.LENGTH_LONG).show();
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "Img" + date + ".jpg";
        String fileName = file.getAbsolutePath() + "/" + name ;

        File new_file = new File(fileName);

        try{

            fileOutputStream = new FileOutputStream(new_file);
            Bitmap bitmap = viewToBitmap(abc , abc.getMeasuredWidth() , abc.getMeasuredHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , fileOutputStream);
            Toast.makeText(this , "Saved" , Toast.LENGTH_LONG).show();
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        refreshGalery(new_file);
    }

    public void refreshGalery(File file){

        Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);

        //getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+    Environment.getExternalStorageDirectory())));
    }

    private File getDisc(){

        File file = Environment.getRootDirectory();
        return new File(file , "image demo");
    }

    /*private Bitmap takeImage (View v){

        Bitmap takeimage = null;

        try {
            int width = v.getMeasuredWidth() ;
            int height = v.getMeasuredHeight() ;

            takeimage = Bitmap.createBitmap(width , height , Bitmap.Config.ARGB_8888);

            Canvas c = new Canvas(takeimage);
            v.draw(c);

        }catch (Exception e){
            e.printStackTrace();
        }
        return takeimage ;
    }*/

    /*private void saveImage (Bitmap bm){

        ByteArrayOutputStream bao = null ;
        File file = null ;

        try {
            bao = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG , 40 , bao);

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "card.png");
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bao.toByteArray());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }*/
}
