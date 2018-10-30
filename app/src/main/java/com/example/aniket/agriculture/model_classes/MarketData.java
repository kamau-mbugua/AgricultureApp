package com.example.aniket.agriculture.model_classes;

public class MarketData {
    private String CropName,District,Market,Price;

    public MarketData() {
        this.CropName = "";
        this.District = "";
        this.Market = "";
        this.Price = "";
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getMarket() {
        return Market;
    }

    public void setMarket(String market) {
        Market = market;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCropName() {
        return CropName;
    }

    public void setCropName(String cropName) {
        CropName = cropName;
    }
}
