package com.example.project.boolawa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button Reg;
    EditText name;
    EditText email;
    EditText password;
    EditText r_password;

    TextView goTOLogin;

    DatabaseReference databaseUser;
    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("Users");

        name = (EditText) findViewById(R.id.editText_name);
        email = (EditText) findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);
        r_password = (EditText)findViewById(R.id.editText_r_password);


        progressDialog = new ProgressDialog(this);

        Reg = (Button)findViewById(R.id.btn_register);
        Reg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fill_data();
            }
        });

        goTOLogin = (TextView)findViewById(R.id.textView_gotoLogin);
        goTOLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            Intent intent_goToLogin = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent_goToLogin);
            }
        });

    }

    private void fill_data()
    {
        final String name1 = name.getText().toString();
        String email1 = email.getText().toString();
        String password1 = password.getText().toString();
        String r_password1 = r_password.getText().toString();

        if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(email1) &&!TextUtils.isEmpty(password1) && !TextUtils.isEmpty(r_password1))
        {


                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String user_id = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = databaseUser.child(user_id);
                            current_user_db.child("UserName").setValue(name1);

                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent_goToLoginPage = new Intent(SignUpActivity.this,Home.class);
                            intent_goToLoginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent_goToLoginPage);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }

        else
        {
            Toast.makeText(this,"Fill Empty Fileds",Toast.LENGTH_SHORT).show();
        }
    }
}
