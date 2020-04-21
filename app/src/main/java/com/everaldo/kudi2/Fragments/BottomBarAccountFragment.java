package com.everaldo.kudi2.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.everaldo.kudi2.R;
import com.everaldo.kudi2.util.ProfileCameraListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomBarAccountFragment extends Fragment {
    private ProfileCameraListener listener;


    public BottomBarAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //ProfileCameraListener
        try {
            listener = (ProfileCameraListener) context;
        }
        catch(ClassCastException ex){
            throw new ClassCastException(context.toString() + " Must implement ProfileCameraListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_bar_account, container, false);
        ImageButton imageProfile = view.findViewById(R.id.camera_profile);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProfileCameraClick();
            }
        });
        Log.i("COCO", "onCreateView called");
        return view;
    }
}
