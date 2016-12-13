package com.example.amdin.androiddesignsupportlibrary.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.example.amdin.androiddesignsupportlibrary.R;
import com.example.amdin.androiddesignsupportlibrary.ui.adapter.QuickAdapter;
import com.example.amdin.androiddesignsupportlibrary.ui.bean.News;
import com.example.amdin.androiddesignsupportlibrary.ui.line.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class KekeActivity extends AppCompatActivity {
    private RecyclerView rv;
    private FloatingActionButton fab;
    private List<News> newsList = new ArrayList<>();
    private QuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keke);
        initView();
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fabbbb);
        fab.setImageResource(R.mipmap.o);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.VERTICAL_LIST));
        getData();
        upData();
    }

    private void upData() {
        if (adapter == null) {
            initAdapter();
            rv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    private void initAdapter() {
        adapter = new QuickAdapter(newsList);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rv);
        //滑动动画
        adapter.openLoadAnimation();
        //取消滑动动画只一次
        adapter.isFirstOnly(false);
        //透明
//        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //从下面上来
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //左边滑动过来
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //右边？
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        //这是啥
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.openLoadMore(30);
    }

    private void getData() {
        for (int i = 0; i < 30; i++) {
//            News news = new News("呦呦呦", "我现在只想发财");
//            newsList.add(news);
        }
    }
}
