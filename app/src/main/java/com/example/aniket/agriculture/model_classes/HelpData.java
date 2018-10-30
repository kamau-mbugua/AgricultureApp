package com.example.aniket.agriculture.model_classes;

import java.util.HashMap;

public class HelpData {
    public static HashMap<String,String> getData(){
        HashMap<String,String> expandableListDetail = new HashMap<String,String>();

        String cropInfo = new String("Gives Deatils of Crops");
        String buy = new String("Buy Crop seeds and fertilisers");
        String location = new String("Nearest Markets");
        String weather = new String("Current weather conditions");

        expandableListDetail.put("Crop Information",cropInfo);
        expandableListDetail.put("Buy",buy);
        expandableListDetail.put("Location",location);
        expandableListDetail.put("Weather",weather);


        return expandableListDetail;
    }
}
