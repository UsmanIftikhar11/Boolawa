package com.example.hamziii.boolawa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventDetailsFromUser extends AppCompatActivity {

    private EditText et_event_title , et_event_person_name , et_event_date , et_event_time , et_event_adress , et_event_hoestedBy ;
    private Button btn_done ;
    private int imgSampleNo ;

    private DatabaseReference mDatabse ;
    private FirebaseAuth mAuth ;

    private String title_string , personName_string , date_string , time_string , adress_string , hostedBy_string ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_from_user);

        mAuth = FirebaseAuth.getInstance();
        mDatabse = FirebaseDatabase.getInstance().getReference().child("EventDetails");

        imgSampleNo = getIntent().getExtras().getInt("imageNo");

        et_event_title = (EditText)findViewById(R.id.editText_event_title);
        et_event_person_name = (EditText)findViewById(R.id.editText_person_name);
        et_event_date = (EditText)findViewById(R.id.editText_event_date);
        et_event_time = (EditText)findViewById(R.id.editText_event_time);
        et_event_adress = (EditText)findViewById(R.id.editText_event_adress);
        et_event_hoestedBy = (EditText)findViewById(R.id.editText_event_hostedBy);




        btn_done = (Button)findViewById(R.id.btn_done);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveEventDetails();
                personName_string = et_event_person_name.getText().toString().trim();
                date_string = et_event_date.getText().toString().trim();
                time_string = et_event_time.getText().toString().trim();
                adress_string = et_event_adress.getText().toString().trim();
                hostedBy_string = et_event_hoestedBy.getText().toString().trim();
                title_string = et_event_title.getText().toString();
                Intent intent = new Intent(EventDetailsFromUser.this , FinalizedInvitationCard.class);
                intent.putExtra("EventTitle" , title_string ) ;
                intent.putExtra("EventPersonName" , personName_string ) ;
                intent.putExtra("EventDate" , date_string ) ;
                intent.putExtra("EventTime" , time_string ) ;
                intent.putExtra("EventAdress" , adress_string ) ;
                intent.putExtra("EventHostedBy" , hostedBy_string ) ;
                intent.putExtra("imgSampleNo" , imgSampleNo);
                startActivity(intent);
                //Toast.makeText(getApplicationContext() , title_string , Toast.LENGTH_LONG).show();

            }
        });

    }

    private void saveEventDetails() {

        final String title_string = et_event_title.getText().toString().toLowerCase().trim();
        final String personName_string = et_event_person_name.getText().toString().trim();
        final String date_string = et_event_date.getText().toString().trim();
        final String time_string = et_event_time.getText().toString().trim();
        final String adress_string = et_event_adress.getText().toString().trim();
        final String hostedBy_string = et_event_hoestedBy.getText().toString().trim();

        if(!TextUtils.isEmpty(title_string) && !TextUtils.isEmpty(personName_string) && !TextUtils.isEmpty(date_string) && !TextUtils.isEmpty(time_string) && !TextUtils.isEmpty(adress_string) && !TextUtils.isEmpty(hostedBy_string) ){

            final DatabaseReference newEventDetails = mDatabse.push();
            mDatabse.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newEventDetails.child("EventTitle").setValue(title_string);
                    newEventDetails.child("PersonName").setValue(personName_string);
                    newEventDetails.child("EventDate").setValue(date_string);
                    newEventDetails.child("EventTime").setValue(time_string);
                    newEventDetails.child("EventAdress").setValue(adress_string);
                    newEventDetails.child("EventHostedBy").setValue(hostedBy_string);
                    newEventDetails.child("UserId").setValue(mAuth.getCurrentUser().getUid());
                    newEventDetails.child("EventSelectedSample").setValue(imgSampleNo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        else {
            Toast.makeText(getApplicationContext() , "Fill All Fields" , Toast.LENGTH_LONG).show();
        }
    }
}
