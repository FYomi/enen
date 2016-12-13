package com.example.amdin.androiddesignsupportlibrary.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amdin.androiddesignsupportlibrary.R;
import com.example.amdin.androiddesignsupportlibrary.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends BaseFragment {
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private Bundle data;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        mToolbar.setTitle("首页");
        //设置标题居中
        mToolbar.setTitle("");
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Biu");
        ((MainActivity) getActivity()).initDrawer(mToolbar);
        initTabLayout(view);

    }

    private void initTabLayout(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        // 设置ViewPager的数据等
        tabLayout.setupWithViewPager(viewPager);
        //适合很多tab
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab均分,适合少的tab
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tab均分,适合少的tab,TabLayout.GRAVITY_CENTER
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Fragment newfragment = new FragmentOne();
         data = new Bundle();
//        data.putInt("id", 0);
//        data.putString("title", getString(R.string.page1));
//        newfragment.setArguments(data);
//        adapter.addFrag(newfragment, getString(R.string.page1));

        newfragment = new FragmentTwo();
//        data = new Bundle();
//        data.putInt("id", 1);
//        data.putString("title", getString(R.string.page2));
//        newfragment.setArguments(data);
        adapter.addFrag(newfragment, getString(R.string.page2));


        newfragment = new FragmentTwo();
//        data = new Bundle();
//        data.putInt("id", 2);
//        data.putString("title", getString(R.string.page3));
//        newfragment.setArguments(data);
        adapter.addFrag(newfragment, getString(R.string.page3));


        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
