package com.example.project.boolawa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToLoginActivity(View view)
    {
        Intent  startNewActivity= new Intent(MainActivity.this,LoginActivity.class);
        startActivity(startNewActivity);
        this.finish();

    }

    public void goToSignUpActivity(View view)
    {
        Intent startNewActivity = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(startNewActivity);
        this.finish();

    }

    public void goToServiceProviderActivity(View view)
    {
        Intent startNewActivity = new Intent(MainActivity.this,ServiceProviderLogin.class);
        startActivity(startNewActivity);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //finish();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(a);

    }
}

