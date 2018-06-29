package com.example.project.boolawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FragmentDesignCard extends Fragment {

    private ImageView img_sample1 , img_sample2 , img_sample3;
    private int imgNo ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_design_card, container, false);

        img_sample1 = (ImageView) rootView.findViewById(R.id.img_sample1);
        img_sample2 = (ImageView) rootView.findViewById(R.id.img_sample2);
        img_sample3 = (ImageView) rootView.findViewById(R.id.img_sample3);

        img_sample1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNo = 1 ;
                Intent intent = new Intent(getActivity() , EventDetailsFromUser.class);
                intent.putExtra("imageNo" , imgNo);
                startActivity(intent);
            }
        });

        img_sample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNo = 2 ;
                Intent intent = new Intent(getActivity() , EventDetailsFromUser.class);
                intent.putExtra("imageNo" , imgNo);
                startActivity(intent);
            }
        });

        img_sample3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNo = 3 ;
                Intent intent = new Intent(getActivity() , EventDetailsFromUser.class);
                intent.putExtra("imageNo" , imgNo);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
