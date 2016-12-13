package com.example.amdin.androiddesignsupportlibrary.ui;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.amdin.androiddesignsupportlibrary.R;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.squareup.picasso.Picasso;

/**
 * Created by amdin on 2016/11/2.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        NineGridView.setImageLoader(new PicassoImageLoader());
        /** Picasso 加载 */
        super.onCreate();
        //必须调用初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以再请求的时候再设置一遍那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        OkGo.getInstance()
                //打开该调试开关,控制台会使用红色error级别打印log
                .debug("OkGo")
                //如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)
                //可以全局统一设置缓存模式,默认是不使用缓存,这个是先读缓存无论成功失败再获取网络数据
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                //可以全局统一设置缓存时间,默认永不过期
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);

    }

    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url)//
                    .placeholder(R.mipmap.o)//
                    .error(R.mipmap.o)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
