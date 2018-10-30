package com.example.aniket.agriculture.model_classes;

public class CartData {
    private String UiD;
    private String cropName;
    private double cropPrice;
    private int cropCount;
    private String cropWeight;
    private int cropImage;

    public CartData() {
        this.cropCount=0;
        this.cropName="";
        this.cropPrice=0;
        this.cropWeight="";
        this.UiD = "";
        this.cropImage = 0;
    }

    public CartData(String UiD, String cropName, double cropPrice, String cropWeight, int cropCount){
        this.UiD = UiD;
        this.cropName = cropName;
        this.cropPrice = cropPrice;
        this.cropWeight = cropWeight;
        this.cropCount = cropCount;
    }

    public String getCropWeight() {
        return cropWeight;
    }

    public void setCropWeight(String cropWeight) {
        this.cropWeight = cropWeight;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public double getCropPrice() {
        return cropPrice;
    }

    public void setCropPrice(double cropPrice) {
        this.cropPrice = cropPrice;
    }

    public int getCropAmount() {
        return cropCount;
    }

    public void setCropAmount(int cropCount) {
        this.cropCount = cropCount;
    }

    public String getUiD() {
        return UiD;
    }

    public void setUiD(String user) {
        this.UiD = UiD;
    }

    public int getCropImage() {
        return cropImage;
    }

    public void setCropImage(int cropImage) {
        this.cropImage = cropImage;
    }
}
