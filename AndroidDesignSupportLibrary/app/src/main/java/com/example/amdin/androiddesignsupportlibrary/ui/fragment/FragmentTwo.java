package com.example.amdin.androiddesignsupportlibrary.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.example.amdin.androiddesignsupportlibrary.R;
import com.example.amdin.androiddesignsupportlibrary.ui.adapter.QuickAdapter;
import com.example.amdin.androiddesignsupportlibrary.ui.bean.News;
import com.example.amdin.androiddesignsupportlibrary.ui.line.DividerItemDecoration;
import com.example.amdin.androiddesignsupportlibrary.ui.urls.Cans;
import com.example.amdin.androiddesignsupportlibrary.ui.util.FastJSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rv;
    private List<News> newsList = new ArrayList<>();
    private List<News> newsListt = new ArrayList<>();
    private QuickAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private JSONArray allMessage;
    private JSONArray imageurls;
    private int page = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            upData();
            switch (msg.what) {
                case 0:
                    //停止刷新
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    adapter.addData(newsListt);
                    break;
            }

        }
    };


    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmen_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sw);
        swipeRefreshLayout.setOnRefreshListener(this);
        //改变加载显示的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(107, 211, 255), Color.rgb(255, 113, 33), Color.rgb(255, 255, 0));
        swipeRefreshLayout.setRefreshing(true);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        getData(page);

    }


    private void upData() {
        if (adapter == null) {
            initAdapter();
            rv.setAdapter(adapter);
        }else {
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
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                // 一定要在mRecyclerView.post里面更新数据。
                rv.post(new Runnable() {
                    @Override
                    public void run() {
                        getData(++page);
                    }
                });
            }
        });
    }

    private void getData(int page) {
        OkGo.get(Cans.NEWS)
                .tag(this)
                .cacheKey("cacheKey")
                .cacheTime(5000)
                .headers("apikey", Cans.APIKEY)
                .params("channelName", "娱乐焦点")//
                .params("page", page)                              //初始化或者下拉刷新,默认加载第一页
                .cacheKey("TabFragment_" + "娱乐焦点")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String msg = FastJSON.getValueByKey(s, "showapi_res_body");
                        String pagebean = FastJSON.getValueByKey(msg, "pagebean");
                        String contentlist = FastJSON.getValueByKey(pagebean, "contentlist");
                        allMessage = JSONArray.parseArray(contentlist);
                        //解析list获得list
                        setContent();
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setContent() {
        newsListt.clear();
        for (int i = 0; i < allMessage.size(); i++) {
            String pubDate = allMessage.getJSONObject(i).getString("pubDate");
            String title = allMessage.getJSONObject(i).getString("title");
            String desc = allMessage.getJSONObject(i).getString("desc");
            String source = allMessage.getJSONObject(i).getString("source");
            Boolean havePic = allMessage.getJSONObject(i).getBoolean("havePic");
            List<News.ImageurlsBean> imageurlsList = new ArrayList<>();
            if (havePic = true) {
                imageurls = allMessage.getJSONObject(i).getJSONArray("imageurls");
                for (int j = 0; j < imageurls.size(); j++) {
                    int height = imageurls.getJSONObject(j).getInteger("height");
                    int width = imageurls.getJSONObject(j).getInteger("width");
                    String url = imageurls.getJSONObject(j).getString("url");
                    News.ImageurlsBean img = new News.ImageurlsBean(height, width, url);
                    imageurlsList.add(img);
                }
            }
            News news = new News(pubDate, havePic, title, desc, source, imageurlsList);
            newsListt.add(news);
        }
        if (page == 1) {
            newsList.addAll(newsListt);
            handler.sendEmptyMessage(0);
        } else {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onRefresh() {
        newsList.clear();
        page = 1;
        getData(page);
    }

}
