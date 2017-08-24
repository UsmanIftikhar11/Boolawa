package com.example.hamziii.boolawa;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class first_fragment extends Fragment {
    View myview;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myview =inflater.inflate(R.layout.first_item,container,false);
        return myview;
    }
}
