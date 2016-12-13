package com.example.amdin.androiddesignsupportlibrary.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amdin on 2016/10/24.
 */
public class News implements Serializable {
    private String pubDate;
    private boolean havePic;
    private String title;
    private String desc;
    private String source;
    /**
     * height : 427
     * width : 640
     * url : http://img1.gtimg.com/sports/pics/hv1/0/164/2149/139780545.jpg
     */

    private List<ImageurlsBean> imageurls;

    public News(String pubDate, boolean havePic, String title, String desc, String source, List<ImageurlsBean> imageurls) {
        this.pubDate = pubDate;
        this.havePic = havePic;
        this.title = title;
        this.desc = desc;
        this.source = source;
        this.imageurls = imageurls;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public List<ImageurlsBean> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<ImageurlsBean> imageurls) {
        this.imageurls = imageurls;
    }

    public static class ImageurlsBean {
        private int height;
        private int width;
        private String url;

        public ImageurlsBean(int height, int width, String url) {
            this.height = height;
            this.width = width;
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

//    private String title;
//    private String content;
//
//    public String getPic() {
//        return pic;
//    }
//
//    public void setPic(String pic) {
//        this.pic = pic;
//    }
//
//    private String pic;
//
//    public News(String title, String content, String pic) {
//        this.title = title;
//        this.content = content;
//        this.pic = pic;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

}
