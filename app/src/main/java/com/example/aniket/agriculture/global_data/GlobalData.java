package com.example.aniket.agriculture.global_data;

import com.example.aniket.agriculture.model_classes.CropDetails;
import com.example.aniket.agriculture.model_classes.MarketData;

import java.util.HashMap;
import java.util.Hashtable;

public class GlobalData {
    static public Hashtable<String,CropDetails> cropHashtable = new Hashtable<String,CropDetails>();
    static public HashMap<String,MarketData> marketData = new HashMap<String,MarketData>();
}