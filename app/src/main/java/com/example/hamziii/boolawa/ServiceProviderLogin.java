package com.example.hamziii.boolawa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ServiceProviderLogin extends AppCompatActivity {

    EditText etService_email , etService_email_pass ;
    Button btn_servicelogin , btn_servicecreate ;
    TextView txt_serviceforget ;

    MaterialSpinner spinner ;

    private Boolean caterer = false ;
    private Boolean photographer = false ;

    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mDatabase , mDatabaseCaterer , mDatabasePhotographer ;
    private ProgressDialog mProgress ;

    private static final String TAG="ServiceLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Hire");
        mDatabaseCaterer = mDatabase.child("Caterer");
        mDatabasePhotographer = mDatabase.child("Photographer");
        // mDatabasecompany.keepSynced(true);
        mProgress = new ProgressDialog(this);

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(ServiceProviderLogin.this, ServceProviderShowHire.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        etService_email = (EditText) findViewById(R.id.editText_service_email);
        etService_email_pass = (EditText) findViewById(R.id.editText_service_password);
        btn_servicelogin = (Button) findViewById(R.id.serviceLogin_button);
        btn_servicecreate = (Button) findViewById(R.id.serviceSignup_button);
        txt_serviceforget = (TextView) findViewById(R.id.txt_serviceForgetPass);

        spinner = (MaterialSpinner) findViewById(R.id.spinnerLoginService);
        spinner.setItems("Your Service" , "caterer", "photographer");

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

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

        btn_servicelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        btn_servicecreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceProviderLogin.this , ServiceProviderSignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkLogin() {
        String email = etService_email.getText().toString().trim();
        String password = etService_email_pass.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProgress.setMessage("Checking Login...");
            mProgress.show();

            mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        mProgress.dismiss();
                        checkUserExist();
                    }
                    else {
                        mProgress.dismiss();
                        Toast.makeText(ServiceProviderLogin.this , "Error Login " , Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(ServiceProviderLogin.this , "Fill all fields " , Toast.LENGTH_LONG).show();
        }

    }

    private void checkUserExist() {

        if(caterer == true){

            final String user_id = mAuth.getCurrentUser().getUid();
            mDatabaseCaterer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(user_id)){

                        Intent mainIntent = new Intent(ServiceProviderLogin.this , ServceProviderShowHire.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);

                    }
                    else {
                        // later on give the link of edit page saying you have incomplete data fill details again
                   /* Intent setupIntent = new Intent(CompanyLogin.this , CompanyRegistration.class);
                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setupIntent);*/
                        Toast.makeText(ServiceProviderLogin.this , "Error Login " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else {

            final String user_id = mAuth.getCurrentUser().getUid();
            mDatabasePhotographer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(user_id)){

                        Intent mainIntent = new Intent(ServiceProviderLogin.this , ServceProviderShowHire.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);

                    }
                    else {
                        // later on give the link of edit page saying you have incomplete data fill details again
                   /* Intent setupIntent = new Intent(CompanyLogin.this , CompanyRegistration.class);
                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setupIntent);*/
                        Toast.makeText(ServiceProviderLogin.this , "Error Login " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        Intent intent = new Intent(ServiceProviderLogin.this , MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
        this.finish();

        /*Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*/
    }
}
