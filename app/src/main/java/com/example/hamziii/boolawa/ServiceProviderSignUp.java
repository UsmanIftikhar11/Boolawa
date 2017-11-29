package com.example.hamziii.boolawa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ServiceProviderSignUp extends AppCompatActivity {

    private MaterialSpinner spinner_Available , spinner_service ;

    private Boolean available = false ;
    private Boolean not_available = false ;
    private Boolean caterer = false ;
    private Boolean photographer = false ;

    private EditText etService_name , etService_pass , etService_confirm_pass , etService_email , etService_charges , etService_phone ;
    private Button btn_create ;

    private FirebaseAuth mAuth ;
    private ProgressDialog mprogress ;
    private DatabaseReference mDatabaseCaterer , mDatabasePhotographer , mDatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mprogress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Hire");
        mDatabaseCaterer = mDatabase.child("Caterer");
        mDatabasePhotographer = mDatabase.child("Photographer");

        etService_name = (EditText)findViewById(R.id.editText_servicename);
        etService_pass = (EditText)findViewById(R.id.editText_servicepassword);
        etService_confirm_pass = (EditText)findViewById(R.id.editText_r_servicepassword);
        etService_email = (EditText)findViewById(R.id.editText_serviceemail);
        etService_charges = (EditText)findViewById(R.id.editText_servicecharges);
        etService_phone = (EditText)findViewById(R.id.editText_servicecontact);
        btn_create = (Button)findViewById(R.id.btn_register);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

        spinner_Available = (MaterialSpinner) findViewById(R.id.spinner_Availability);
        spinner_Available.setItems("Availability" , "Available", "Not Available");

        spinner_Available.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.toString()=="Available")
                {
                    available = true ;
                    not_available = false ;
                }
                else if (item.toString()=="Not Available")
                {
                    available = false ;
                    not_available = true ;
                }
                else {
                    available = false ;
                    not_available = true ;
                }
            }
        });

        spinner_service = (MaterialSpinner) findViewById(R.id.spinner_Service);
        spinner_service.setItems("Your Service" , "caterer", "photographer");

        spinner_service.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.toString()=="caterer")
                {
                   caterer = true;
                    photographer = false ;
                }
                else if (item.toString()=="photographer")
                {
                    caterer = false;
                    photographer = true ;
                }
                else {
                    caterer = true;
                    photographer = false ;
                }
            }
        });
    }

    private void startRegister() {

        final String nameString = etService_name.getText().toString().trim();
        String passString = etService_pass.getText().toString().trim();
        String confirmPassString = etService_confirm_pass.getText().toString().trim();
        String emailString = etService_email.getText().toString().trim();
        final String phoneString = etService_phone.getText().toString().trim();
        final String chargesString = etService_charges.getText().toString().trim();


        if(!TextUtils.isEmpty(nameString) && !TextUtils.isEmpty(passString) && !TextUtils.isEmpty(confirmPassString)  && !TextUtils.isEmpty(emailString) && !TextUtils.isEmpty(phoneString) && !TextUtils.isEmpty(chargesString)){

            //if(passString== confirmPassString){
            mprogress.setMessage("Creating account...");
            mprogress.show();

            if(available == true){

                if(caterer == true){

                    mAuth.createUserWithEmailAndPassword(emailString , passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference caterer_user_db = mDatabaseCaterer.child(user_id);
                                caterer_user_db.child("name").setValue(nameString);
                                caterer_user_db.child("phone").setValue(phoneString);
                                caterer_user_db.child("charges").setValue(chargesString);
                                caterer_user_db.child("Service").setValue("caterer");
                                caterer_user_db.child("Availability").setValue("Available");

                                mprogress.dismiss();
                                Intent intent = new Intent(ServiceProviderSignUp.this , ServceProviderShowHire.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }
                    });
                }
                else{

                    mAuth.createUserWithEmailAndPassword(emailString , passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference photographer_user_db = mDatabasePhotographer.child(user_id);
                                photographer_user_db.child("name").setValue(nameString);
                                photographer_user_db.child("phone").setValue(phoneString);
                                photographer_user_db.child("charges").setValue(chargesString);
                                photographer_user_db.child("Service").setValue("photographer");
                                photographer_user_db.child("Availability").setValue("Available");

                                mprogress.dismiss();
                                Intent intent = new Intent(ServiceProviderSignUp.this , ServceProviderShowHire.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }
                    });
                }
            }

            if(not_available == true){

                if(caterer == true){

                    mAuth.createUserWithEmailAndPassword(emailString , passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference caterer_user_db = mDatabaseCaterer.child(user_id);
                                caterer_user_db.child("name").setValue(nameString);
                                caterer_user_db.child("phone").setValue(phoneString);
                                caterer_user_db.child("charges").setValue(chargesString);
                                caterer_user_db.child("Service").setValue("caterer");
                                caterer_user_db.child("Availability").setValue("Not Available");

                                mprogress.dismiss();
                                Intent intent = new Intent(ServiceProviderSignUp.this , ServceProviderShowHire.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }
                    });
                }

                else{

                    mAuth.createUserWithEmailAndPassword(emailString , passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference photographer_user_db = mDatabasePhotographer.child(user_id);
                                photographer_user_db.child("name").setValue(nameString);
                                photographer_user_db.child("phone").setValue(phoneString);
                                photographer_user_db.child("charges").setValue(chargesString);
                                photographer_user_db.child("Service").setValue("photographer");
                                photographer_user_db.child("Availability").setValue("Not Available");

                                mprogress.dismiss();
                                Intent intent = new Intent(ServiceProviderSignUp.this , ServceProviderShowHire.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        }
        else {

            Toast.makeText(getApplicationContext() , "Fill all fields!!!" , Toast.LENGTH_LONG).show();
        }

    }
}
