package com.example.amdin.androiddesignsupportlibrary.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amdin.androiddesignsupportlibrary.R;
import com.example.amdin.androiddesignsupportlibrary.ui.view.FunSwitch;
import com.example.amdin.androiddesignsupportlibrary.ui.view.SwitchButton;
import com.example.amdin.androiddesignsupportlibrary.ui.view.SwitchButtonView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSix extends Fragment {
    private SwitchButton btn;
    private View view;
    private FunSwitch btnn;
    private SwitchButtonView btnnn;

    public FragmentSix() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_six, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = (SwitchButton) view.findViewById(R.id.btn);
        btn.setInitialState(false);
        if (btn.isChecked()) {
            Toast.makeText(getActivity(), "日间模式", Toast.LENGTH_SHORT).show();
            view.setBackgroundResource(R.color.defaultSwitchOnColor);
        } else {
            Toast.makeText(getActivity(), "夜间模式", Toast.LENGTH_SHORT).show();
            view.setBackgroundResource(R.color.defaultSwitchOffColor);
        }
        btn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton s, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "日间模式", Toast.LENGTH_SHORT).show();
                    view.setBackgroundResource(R.color.defaultSwitchOnColor);
                } else {
                    Toast.makeText(getActivity(), "夜间模式", Toast.LENGTH_SHORT).show();
                    view.setBackgroundResource(R.color.defaultSwitchOffColor);
                }
            }
        });

        btnn = (FunSwitch) view.findViewById(R.id.btnn);
        btnn.setState(true);
        if (btnn.getState()) {
            Toast.makeText(getActivity(), "yohoo", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "aha", Toast.LENGTH_SHORT).show();
        }
        btnn.setOnSwitchListener(new FunSwitch.onSwitchListener() {
            @Override
            public void onSwitchChanged(boolean isCheck) {
                if (isCheck) {
                    Toast.makeText(getActivity(), "yohoo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "aha", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
