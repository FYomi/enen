package com.example.amdin.androiddesignsupportlibrary.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.amdin.androiddesignsupportlibrary.R;
import com.example.amdin.androiddesignsupportlibrary.ui.activity.DetailActivity;
import com.example.amdin.androiddesignsupportlibrary.ui.bean.News;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amdin on 2016/10/24.
 */
public class QuickAdapter extends BaseItemDraggableAdapter<News> {
    private List<News> list;

    public QuickAdapter(List<News> data) {
        super(R.layout.rv_mb, data);
        this.list = data;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, News news) {
        baseViewHolder.setText(R.id.title, news.getTitle())
                .setText(R.id.content, news.getDesc())
                .setText(R.id.pubDate, news.getPubDate())
                .setText(R.id.source, news.getSource());
        View view = baseViewHolder.getConvertView();
        int adapterPosition = baseViewHolder.getAdapterPosition();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        NineGridView nineGrid = baseViewHolder.getView(R.id.pic);
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<News.ImageurlsBean> images = news.getImageurls();
        if (images != null) {
            for (News.ImageurlsBean image : images) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(image.getUrl());
                info.setBigImageUrl(image.getUrl());
                imageInfo.add(info);
            }
        }
        nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));

        if (images != null && images.size() == 1) {
            nineGrid.setSingleImageRatio(images.get(0).getWidth() * 1.0f / images.get(0).getHeight());
        }

    }
}