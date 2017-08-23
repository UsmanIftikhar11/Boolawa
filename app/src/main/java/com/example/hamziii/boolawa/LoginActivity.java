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
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static final String TAG="MAIN_ACTIVITY";
    private FirebaseAuth.AuthStateListener mAuthListner;

    private EditText mEmailField,mpasswordField;
    private Button mloginBtn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText)findViewById(R.id.editText_login_email);
        mpasswordField = (EditText)findViewById(R.id.editText_login_password);
        mloginBtn = (Button)findViewById(R.id.login_button111);

        progressDialog = new ProgressDialog(this);

        mGoogleBtn = (SignInButton)findViewById(R.id.gogleBtn);
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(LoginActivity.this, Main2Activity.class));
                }
            }
        };
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener()
                {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(LoginActivity.this,"You Got An Error",Toast.LENGTH_SHORT).show();
                    }
                })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signIn();
            }
        });
    }


    protected void onStart()
    {
        super.onStart();

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mEmail,mPassword;
                  mEmail=mEmailField.getText().toString();
                  mPassword=mpasswordField.getText().toString();

                if (TextUtils.isEmpty(mEmail))
                {
                    Toast.makeText(LoginActivity.this,"Enter Email",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(mPassword))
                {
                    Toast.makeText(LoginActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.setMessage("Loading. . .");
                    progressDialog.show();
                    emailLogin();
                }
            }
        });

        mAuth.addAuthStateListener(mAuthListner);
    }

    //for Email Login

    private void emailLogin()
    {
    final String mEmail,mPassword;
        mEmail=mEmailField.getText().toString();
        mPassword= mpasswordField.getText().toString();

            mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {

                if (task.isSuccessful())
                {
                    progressDialog.hide();
                    Toast.makeText(LoginActivity.this,"Welcome  "+mEmail,Toast.LENGTH_SHORT).show();
                    Intent intent_accountActivity = new Intent(LoginActivity.this,Main2Activity.class);
                    startActivity(intent_accountActivity);
                }
                else
                {
                    progressDialog.hide();
                    Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_SHORT).show();

                }
                }
            });

    }

    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                     Log.d(TAG,"signInWithCredential:onComplete:"+task.isSuccessful());
                        if (!task.isSuccessful())
                        {
                            Log.w(TAG, "signInWithCredential",task.getException());
                            Toast.makeText(LoginActivity.this,"Autjentication Failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    public void goToPasswordResetActivity(View view)
    {
        Intent startNewActivity= new Intent(LoginActivity.this,PasswordResetActivity.class);
        startActivity(startNewActivity);

    }

    public void onBackPressed() {
        Intent startNewActivity= new Intent(LoginActivity.this,MainActivity.class);
        startActivity(startNewActivity);
    }
}