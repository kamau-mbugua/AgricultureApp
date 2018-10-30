package com.example.aniket.agriculture.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aniket.agriculture.activities.CartActivity;
import com.example.aniket.agriculture.adapters.MenuAdapter;
import com.example.aniket.agriculture.model_classes.MenuListItem;
import com.example.aniket.agriculture.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.aniket.agriculture.adapters.MenuAdapter.ctr;

public class CropMenuFragment extends Fragment implements View.OnClickListener{
    RecyclerView menuRecyclerView;
    RecyclerView.Adapter menuAdapter;
    CardView menuView;
    FloatingActionButton viewCart;
    TextView notify;

    String[] c = {"Bottle Gourd", "Brinjal", "Cabbage", "Carrot", "Coriander", "Cucumber", "Garlic", "Green Chilli", "LadyFinger", "Millet", "Oats", "Potato", "Spinach", "Sugarcane", "Tomato"};
    int[] img = {R.drawable.bottlegourd,R.drawable.brinjal,R.drawable.cabbage,R.drawable.carrot,
            R.drawable.coriander,R.drawable.cucumber,R.drawable.garlic,R.drawable.greenchilli,
            R.drawable.ladyfinger,R.drawable.millet,R.drawable.oats,R.drawable.potato,
            R.drawable.spinach,R.drawable.sugarcane,R.drawable.tomato};
    String[] p = {"Rs. 400","Rs. 350","Rs. 1000","Rs. 580","Rs. 64","Rs. 460","Rs. 126","Rs. 200","Rs. 1200","Rs. 450","Rs. 3000","Rs. 70","Rs. 65","Rs. 310","Rs. 1000"};
    int[] count = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String[] weights = {"250gm", "500gm", "1kg","5kg"};
    double[] w = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_recycler,container,false);
        menuRecyclerView = (RecyclerView)v.findViewById(R.id.menu_recycler_view);
        menuView = (CardView)v.findViewById(R.id.menu);
        viewCart = (FloatingActionButton) v.findViewById(R.id.viewCart);
        notify = (TextView)v.findViewById(R.id.notifyCount);
        displayMenu();
        return v;
    }

    public void displayMenu(){
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MenuListItem> listItems = new ArrayList<>();

        for(int i=0;i<15;i++){
            MenuListItem listItem = new MenuListItem(c[i],img[i],p[i],"0");
            listItems.add(listItem);
        }

        menuAdapter = new MenuAdapter(listItems,getContext(),count,weights,w,notify);
        menuRecyclerView.setAdapter(menuAdapter);

        viewCart.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getContext(),CartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pos",0);
        bundle.putIntArray("itemCount",count);
        bundle.putDoubleArray("weights",w);
        intent.putExtras(bundle);
        notify.setText("");
        ctr = 0;
        notify.setVisibility(View.INVISIBLE);
        startActivity(intent);

    }
}
