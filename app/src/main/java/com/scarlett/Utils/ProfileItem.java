package com.scarlett.Utils;

public class ProfileItem {

    private String title;
    private int icon;
    private String price = "0";

    public ProfileItem(String title, int icon, String price) {
        this.title = title;
        this.icon = icon;
        this.price = price;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
