package com.example.aniket.agriculture.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aniket.agriculture.R;
import com.example.aniket.agriculture.adapters.RecyclerViewAdapter;
import com.example.aniket.agriculture.model_classes.CropDetails;

import java.util.ArrayList;
import java.util.List;

public class CropDetailsFragment extends Fragment {
    List<CropDetails> cropList;
    RecyclerView mRecyclerView;

    String[] crops = {"Bottle Gourd", "Brinjal", "Cabbage", "Carrot", "Coriander", "Cucumber", "Garlic", "Green Chilli", "LadyFinger", "Millet", "Oats", "Potato", "Spinach", "Sugarcane", "Tomato"};
    int[] cropImages = {R.drawable.bottlegourd,R.drawable.brinjal,R.drawable.cabbage,R.drawable.carrot,
            R.drawable.coriander,R.drawable.cucumber,R.drawable.garlic,R.drawable.greenchilli,
            R.drawable.ladyfinger,R.drawable.millet,R.drawable.oats,R.drawable.potato,
            R.drawable.spinach,R.drawable.sugarcane,R.drawable.tomato};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.crop_information,container,false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_id);

        cropList = new ArrayList<>();

        add();

        return root;
    }
    public void add(){

        for(int i=0;i<crops.length;i++){
            cropList.add(new CropDetails(crops[i],cropImages[i]));
        }

        RecyclerViewAdapter mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), cropList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

}