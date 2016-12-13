package com.example.amdin.androiddesignsupportlibrary.ui.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.amdin.androiddesignsupportlibrary.R;
import com.example.amdin.androiddesignsupportlibrary.ui.adapter.GridViewAdapter;
import com.example.amdin.androiddesignsupportlibrary.ui.util.GetBitmapForURL;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFour extends Fragment {
    private ImageView mainImageView;
    private ProgressBar pb_main;
    private Button b1, b2;
    private GridView gvOther;

    private final String url = "http://www.iamxiarui.com/wp-content/uploads/2016/06/套路.png";
    private final String url1 = "http://www.iamxiarui.com/wp-content/uploads/2016/06/套路.png";
    private final String url2 = "http://www.iamxiarui.com/wp-content/uploads/2016/06/为什么我的流量又没了.png";
    private final String url3 = "http://www.iamxiarui.com/wp-content/uploads/2016/05/cropped-iamxiarui.com_2016-05-05_14-42-31.jpg";
    private final String url4 = "http://www.iamxiarui.com/wp-content/uploads/2016/05/微信.png";
    private List<Bitmap> list = new ArrayList<>();
    //一组Url数据
    private final String[] urls = new String[]{url1, url2, url3, url4};
    public FragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_four, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainImageView = (ImageView) view.findViewById(R.id.iv_main);
        pb_main = (ProgressBar) view.findViewById(R.id.pb_main);
        b1 = (Button) view.findViewById(R.id.button);
        b2 = (Button) view.findViewById(R.id.button2);
        gvOther= (GridView) view.findViewById(R.id.gv_other);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_main.setVisibility(View.VISIBLE);
                setBitmap3();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_main.setVisibility(View.VISIBLE);
                setBitmap();
            }
        });
    }

    /**
     * from与flatMap的使用
     * take与doOnNext的使用
     */
    private void setBitmap() {
        Observable.from(urls).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s);
            }
        }).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                return GetBitmapForURL.getBitmap(s);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                Toast.makeText(getActivity(), "图片增加", Toast.LENGTH_SHORT).show();
            }
        })
        .subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                //将获取到的Bitmap对象添加到集合中
                list.add(bitmap);
                //设置图片
                gvOther.setAdapter(new GridViewAdapter(getActivity(), list));
                pb_main.setVisibility(View.GONE);
            }
        });
    }

    private void setBitmap2() {
        Observable.just(url).map(new Func1<String,Bitmap>(){

            @Override
            public Bitmap call(String s) {
                //通过Map转换成Bitmap类型发送出去
                return GetBitmapForURL.getBitmap(s);
            }
        })
        .subscribeOn(Schedulers.io())//指定subscribe()发生在IO线程
        .observeOn(AndroidSchedulers.mainThread())//指定Subscriber的回调发生在UI线程
        //可以看到,这里接受的类型是Bitmap,而不是String
        .subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                mainImageView.setImageBitmap(bitmap);
                pb_main.setVisibility(View.GONE);
            }
        })
        ;
    }

    private void setBitmap3() {
        //创建被观察者
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            /**
             * 复写call方法
             * @param subscriber 观察者对象
             */
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                //通过URL得到图片的bitmap对象
                Bitmap bitmap = GetBitmapForURL.getBitmap(url);
                //回调观察方法
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
                Log.i(" call ---> ", "运行在" + Thread.currentThread().getName() + "线程");

            }
        })
                .subscribeOn(Schedulers.io()) // 指定subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定Subscriber的回调发生在UI线程
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        mainImageView.setImageBitmap(bitmap);
                        Log.i(" onNext ---> ", "运行在 " + Thread.currentThread().getName() + " 线程");
                    }

                    @Override
                    public void onCompleted() {
                        pb_main.setVisibility(View.GONE);
                        Log.i(" onCompleted ---> ", "完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(" onError --->", e.toString());
                    }


                });
    }
}
