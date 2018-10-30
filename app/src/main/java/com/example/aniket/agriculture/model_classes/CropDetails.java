package com.example.aniket.agriculture.model_classes;

public class CropDetails {

    private String title;
    private String type;
    private String description;
    private String soiltype;
    private String season;
    private int thumbnail;



    public CropDetails(){
        this.title = "";
        this.type = "";
        this.description = "";
        this.soiltype = "";
        this.season = "";
    }

    public CropDetails(String title, int thumbnails){
        this.title = title;
        this.thumbnail = thumbnails;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSoiltype() {
        return soiltype;
    }

    public void setSoiltype(String soiltype) {
        this.soiltype = soiltype;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getThumbnail() { return thumbnail; }

    public void setThumbnail(int thumbnail) { this.thumbnail = thumbnail; }
}
