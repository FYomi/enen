package com.example.amdin.androiddesignsupportlibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.amdin.androiddesignsupportlibrary.R;
import com.example.amdin.androiddesignsupportlibrary.ui.fragment.FragmentFive;
import com.example.amdin.androiddesignsupportlibrary.ui.fragment.FragmentFour;
import com.example.amdin.androiddesignsupportlibrary.ui.fragment.FragmentOne;
import com.example.amdin.androiddesignsupportlibrary.ui.fragment.FragmentSix;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;//导航抽屉
    private ActionBarDrawerToggle mDrawerToggle;//是 DrawerLayout.DrawerListener实现。
    private View headerView, contentLayout;
    private int currentPosition;
    private List<Fragment> fragmentList;
    private RadioGroup rg;
    private RadioButton[] act_homepage_rb;
    private boolean isShow = true;
    private GoogleApiClient client;
    private float x1, x2, y1, y2;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initNavigationViewHeader();
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle("首页");
//        initDrawer(mToolbar);
        initFragment(savedInstanceState);
        initView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fabBtn);
        fab.setImageResource(R.mipmap.o);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KekeActivity.class);
                startActivity(intent);
            }
        });
        rg = (RadioGroup) findViewById(R.id.act_homepage_VG);
        act_homepage_rb = new RadioButton[rg.getChildCount()];
        for (int i = 0; i < rg.getChildCount(); i++) {
            act_homepage_rb[i] = (RadioButton) rg.getChildAt(i);
        }
        //点击按钮，更换Fragment
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < act_homepage_rb.length; i++) {
                    if (act_homepage_rb[i].getId() == checkedId) {
                        switch (i) {
                            case 0:
                                changeFragment(0);
                                fab.setVisibility(View.VISIBLE);
                                fab.setImageResource(R.mipmap.o);
                                break;
                            case 1:
                                changeFragment(1);
                                fab.setVisibility(View.VISIBLE);
                                fab.setImageResource(R.mipmap.a);
                                break;
                            case 2:
                                changeFragment(2);
                                fab.setVisibility(View.GONE);
                                break;
                            case 3:
                                changeFragment(3);
                                fab.setVisibility(View.GONE);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 更换Fragment
     *
     * @param i
     */
    private void changeFragment(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment targetFragment = fragmentList.get(i);
        Fragment currentFragment = fragmentList.get(currentPosition);
        if (targetFragment.isAdded()) {
            transaction.show(targetFragment).hide(currentFragment).commit();
        } else {
            transaction.add(R.id.contentLayout, targetFragment).hide(currentFragment).commit();
        }
        currentPosition = i;
    }
    private void initFragment(Bundle savedInstanceState) {
        fragmentList = new ArrayList<>();
        FragmentOne one = new FragmentOne();
        FragmentFour four = new FragmentFour();
        FragmentFive five = new FragmentFive();
        FragmentSix six = new FragmentSix();
        fragmentList.add(one);
        fragmentList.add(four);
        fragmentList.add(five);
        fragmentList.add(six);

        getSupportFragmentManager().beginTransaction().add(R.id.contentLayout, one).commit();

//        if (savedInstanceState == null) {
//            currentFragment = new FragmentOne();
//            switchContent(currentFragment);
//        } else {
//            //activity销毁后记住销毁前所在页面，用于夜间模式切换
//            currentIndex = savedInstanceState.getInt(AppConstants.CURRENT_INDEX);
//            switch (this.currentIndex) {
//                case 0:
//                    currentFragment = new FragmentOne();
//                    switchContent(currentFragment);
//                    break;
//                case 1:
//                    currentFragment = new FragmentTwo();
//                    switchContent(currentFragment);
//                    break;
//                case 2:
//                    currentFragment = new FragmentThree();
//                    switchContent(currentFragment);
//                    break;
//            }
//        }
    }

    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            mDrawerToggle.syncState();
            mDrawerLayout.addDrawerListener(mDrawerToggle);
        }
    }

    /**
     * 侧拉菜单的头部布局
     * 以及菜单的点击事件setNavigationItemSelectedListener
     */
    private void initNavigationViewHeader() {
        navigationView = (NavigationView) findViewById(R.id.navigation);
        //显示图标
        navigationView.setItemIconTintList(null);
        //设置头像，布局app:headerLayout="@layout/drawer_header"所指定的头布局
//        View view =
        navigationView.inflateHeaderView(R.layout.drawer_header);
        headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.touxiang).setOnClickListener(this);
//        TextView userName = (TextView) view.findViewById(R.id.userName);
//        userName.setText(R.string.author);
//        //View mNavigationViewHeader = View.inflate(MainActivity.this, R.layout.drawer_header, null);
//        //navigationView.addHeaderView(mNavigationViewHeader);//此方法在魅族note 1，头像显示不全
        //菜单点击事件
//        navigationView.setNavigationItemSelectedListener(new NavigationItemSelected());
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelected());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.touxiang:
                mDrawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, "头像", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class NavigationItemSelected implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            mDrawerLayout.closeDrawers();//关掉侧拉菜单
            switch (menuItem.getItemId()) {
                case R.id.navigation_item_1:
                    Toast.makeText(MainActivity.this, "首页", Toast.LENGTH_SHORT).show();
//                    currentIndex = 0;
//                    menuItem.setChecked(true);
//                    currentFragment = new FristFragment();
//                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_2:
                    Toast.makeText(MainActivity.this, "诶？", Toast.LENGTH_SHORT).show();
//                    currentIndex = 2;
//                    menuItem.setChecked(true);
//                    currentFragment = new ThirdFragment();
//                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_3:
                    Toast.makeText(MainActivity.this, "喵喵~", Toast.LENGTH_SHORT).show();
//                    currentIndex = 1;
//                    menuItem.setChecked(true);
//                    currentFragment = new SecondFragment();
//                    switchContent(currentFragment);
                    return true;
//                case R.id.navigation_item_night:
//                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, true);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    recreate();
//                    return true;
//                case R.id.navigation_item_day:
//                    SharedPreferencesUtil.setBoolean(mActivity, AppConstants.ISNIGHT, false);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    recreate();
//                    return true;
                default:
                    return true;
            }
        }
    }
}
