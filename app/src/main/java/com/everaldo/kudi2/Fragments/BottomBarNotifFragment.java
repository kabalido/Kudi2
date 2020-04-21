package com.everaldo.kudi2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.everaldo.kudi2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomBarNotifFragment extends Fragment {

    public BottomBarNotifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bottom_bar_notif, container, false);

        return view;
    }
}
