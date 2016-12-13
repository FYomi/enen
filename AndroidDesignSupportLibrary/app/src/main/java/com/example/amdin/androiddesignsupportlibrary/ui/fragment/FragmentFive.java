package com.example.amdin.androiddesignsupportlibrary.ui.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.amdin.androiddesignsupportlibrary.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFive extends Fragment {
    private RadioGroup rg;
    private RadioButton[] act_homepage_rb;
    private boolean choosePrice = false;
    private boolean isUp = false;

    public FragmentFive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_five, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rg = (RadioGroup) view.findViewById(R.id.actVG);
        RadioButton radioButton = (RadioButton) view.findViewById(R.id.actrb4);
        act_homepage_rb = new RadioButton[rg.getChildCount()];
        for (int i = 0; i < rg.getChildCount(); i++) {
            act_homepage_rb[i] = (RadioButton) rg.getChildAt(i);
        }
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosePrice) {
                    if (isUp) {
                        showDown();
                        //TODO 按价格从高到低排序

                    } else {
                        showUp();
                        //TODO 按价格从低往高排序
                    }
                }
            }
        });
        //点击按钮，更换Fragment
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < act_homepage_rb.length; i++) {
                    if (act_homepage_rb[i].getId() == checkedId) {
                        switch (i) {
                            case 0:
                                show();

                                break;
                            case 1:
                                show();
                                break;
                            case 2:
                                choosePrice = true;
                                break;
                            case 3:
                                show();
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });
    }

    private void showDown() {
        Drawable nav_up = getResources().getDrawable(R.drawable.y1);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        act_homepage_rb[2].setCompoundDrawables(null, null, nav_up, null);
        isUp = false;
    }

    private void showUp() {
        Drawable nav_up = getResources().getDrawable(R.drawable.y2);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        act_homepage_rb[2].setCompoundDrawables(null, null, nav_up, null);
        isUp = true;
    }

    private void show() {
        Drawable nav_up = getResources().getDrawable(R.drawable.no);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        act_homepage_rb[2].setCompoundDrawables(null, null, nav_up, null);
        choosePrice = false;
        isUp = false;
    }
}
