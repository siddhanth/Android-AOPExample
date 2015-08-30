package org.android10.viewgroupperformance.activity;

/**
 * Created by hsingh on 8/30/2015.
 */
public class FeedModel {

    String name;
    String time;
    String data;
    int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public String getData() {
        return data;
    }

    public int getImage() {
        return image;
    }
}
