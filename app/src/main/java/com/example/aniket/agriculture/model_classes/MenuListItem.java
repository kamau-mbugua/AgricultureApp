package com.example.aniket.agriculture.model_classes;

public class MenuListItem {
    private String name;
    private int image;
    private String price;
    private String count;

    public MenuListItem(String name, int image,String price,String count){
        this.name = name;
        this.image = image;
        this.price = price;
        this.count = count;

    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
