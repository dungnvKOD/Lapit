package com.dung.lapit;

import android.app.Application;
import android.graphics.drawable.Drawable;
import com.example.dung.applabit.Model.User;

public class App extends Application {

    private double latitude;
    private double longitude;
    private boolean gender;
    private Drawable drawable;
    private boolean isLike;
    private User user;

    private static App insatnce;

    @Override
    public void onCreate() {
        super.onCreate();
        insatnce = this;
    }

    public static App getInsatnce() {
        if (insatnce == null) {
            insatnce = new App();
        }
        return insatnce;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
