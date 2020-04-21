package com.everaldo.kudi2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.everaldo.kudi2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomBarHomeFragment extends Fragment {

    public BottomBarHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_bar_home_fragment, container, false);
    }
}
