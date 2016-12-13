package com.lzy.ninegrid.preview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Response;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImagePreviewAdapter extends PagerAdapter implements PhotoViewAttacher.OnPhotoTapListener, View.OnClickListener {
    private int pos;
    private PopupWindow mPopWindow;
    private List<ImageInfo> imageInfo;
    private Context context;
    private View currentView;
    private Bitmap cacheImage;

    public ImagePreviewAdapter(Context context, @NonNull List<ImageInfo> imageInfo) {
        super();
        this.imageInfo = imageInfo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageInfo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    public ImageView getPrimaryImageView() {
        return (ImageView) currentView.findViewById(R.id.pv);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);
        final ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);
        final PhotoView imageView = (PhotoView) view.findViewById(R.id.pv);

        ImageInfo info = this.imageInfo.get(position);
        imageView.setOnPhotoTapListener(this);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO 在这里出选择框 保存
                //发送给朋友 保存到手机 收藏 投诉
                pos = position;
                showPopupWindow();

                return false;
            }
        });
        showExcessPic(info, imageView);

        //如果需要加载的loading,需要自己改写,不能使用这个方法
        NineGridView.getImageLoader().onDisplayImage(view.getContext(), imageView, info.bigImageUrl);

//        pb.setVisibility(View.VISIBLE);
//        Glide.with(context).load(info.bigImageUrl)//
//                .placeholder(R.drawable.ic_default_image)//
//                .error(R.drawable.ic_default_image)//
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        pb.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        pb.setVisibility(View.GONE);
//                        return false;
//                    }
//                }).into(imageView);

        container.addView(view);
        return view;
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //这一句可以让popupwindow按空白处可返回
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_computer);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_financial);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_manage);
        TextView tv4 = (TextView) contentView.findViewById(R.id.pop_tousu);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_preview, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.pop_computer) {
            Toast.makeText(context, "其实我就想写保存", Toast.LENGTH_SHORT).show();
            mPopWindow.dismiss();

        } else if (id == R.id.pop_financial) {
            saveThePic();
            mPopWindow.dismiss();

        } else if (id == R.id.pop_manage) {
            Toast.makeText(context, "其实我就想写保存", Toast.LENGTH_SHORT).show();
            mPopWindow.dismiss();

        } else if (id == R.id.pop_tousu) {
            Toast.makeText(context, "其实我就想写保存", Toast.LENGTH_SHORT).show();
            mPopWindow.dismiss();
        }
    }

    private void saveThePic() {
        if (cacheImage != null) {//= =.其实好像一直都是null
            saveImageToGallery(context, cacheImage);
        } else {
            ImageInfo imageInfo = this.imageInfo.get(pos);
            String bigImageUrl = imageInfo.getBigImageUrl();
            OkGo.get(bigImageUrl)//
                    .tag(this)//
                    .execute(new BitmapCallback() {
                        @Override
                        public void onSuccess(Bitmap bitmap, okhttp3.Call call, Response response) {
                            saveImageToGallery(context, bitmap);
                        }
                    });
        }


    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库,防止这里存两份图,注释掉
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        String path = file.getPath();
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示过度图片
     */
    private void showExcessPic(ImageInfo imageInfo, PhotoView imageView) {
        //先获取大图的缓存图片
        cacheImage = NineGridView.getImageLoader().getCacheImage(imageInfo.bigImageUrl);
        //如果大图的缓存不存在,在获取小图的缓存
        if (cacheImage == null)
            cacheImage = NineGridView.getImageLoader().getCacheImage(imageInfo.thumbnailUrl);
        //如果没有任何缓存,使用默认图片,否者使用缓存
        if (cacheImage == null) {
            imageView.setImageResource(R.drawable.ic_default_color);
        } else {
            imageView.setImageBitmap(cacheImage);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 单击屏幕关闭
     */
    @Override
    public void onPhotoTap(View view, float x, float y) {
        ((ImagePreviewActivity) context).finishActivityAnim();
    }

}