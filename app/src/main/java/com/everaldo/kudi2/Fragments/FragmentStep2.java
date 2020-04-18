package com.everaldo.kudi2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.everaldo.kudi2.R;
import com.everaldo.kudi2.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStep2 extends Fragment {


    public FragmentStep2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register2, container, false);

        String[] idCardTypes = { "1-B.I", "2-Cartao Eleitor", "3-Passporte", "4-Carta Conducao"};
        Spinner spIdType = view.findViewById(R.id.sp_id_type);
        ArrayAdapter aa = new ArrayAdapter<>(this.getContext() ,android.R.layout.simple_spinner_item,idCardTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIdType.setAdapter(aa);
        return view;
    }
}
