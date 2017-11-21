package com.example.hamziii.boolawa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class ServiceProviderHome extends AppCompatActivity {

    MaterialSpinner spinnerUpdate ;
    private boolean available = false ;
    private boolean not_available = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_home);

        spinnerUpdate = (MaterialSpinner) findViewById(R.id.spinner_updateAvailability);
        spinnerUpdate.setItems("Availability" , "Available", "Not Available");

        spinnerUpdate.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

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
    }
}
