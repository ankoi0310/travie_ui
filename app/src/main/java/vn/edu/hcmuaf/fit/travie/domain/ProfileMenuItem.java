package vn.edu.hcmuaf.fit.travie.domain;

import android.app.Activity;

public class ProfileMenuItem {
    private String title;
    private int icon;
    private int cardBackgroundColor;
    private Class<? extends Activity> activity;

    public ProfileMenuItem(String title, int icon, int cardBackgroundColor, Class<? extends Activity> activity) {
        this.title = title;
        this.icon = icon;
        this.cardBackgroundColor = cardBackgroundColor;
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCardBackgroundColor() {
        return cardBackgroundColor;
    }

    public void setCardBackgroundColor(int cardBackgroundColor) {
        this.cardBackgroundColor = cardBackgroundColor;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }
}
