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

public class FertiliserMenuFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    RecyclerView menuRecyclerView;
    RecyclerView.Adapter menuAdapter;
    CardView menuView;
    FloatingActionButton viewCart;
    TextView notify1;

    String[] f = {"Slash","Hexaban","Hexamida","Agent Plus","Bio-Humate","Criyazyme","Growstim","Ethrel","Bio-NPK","Isabion","Samaras","Bio-Maxx","Felix","Imida Gold","Hexavin"};
    int[] fertiliserImage = {R.drawable.slash,R.drawable.hexaban,R.drawable.hexamida,R.drawable.agent_plus,
            R.drawable.bio_humate,R.drawable.criyazyme,R.drawable.growtism,R.drawable.etherel,
            R.drawable.slash,R.drawable.hexaban,R.drawable.hexamida,R.drawable.agent_plus,
            R.drawable.bio_humate,R.drawable.criyazyme,R.drawable.growtism};
    String[] fertiliserPrice = {"Rs. 150","Rs. 180","Rs. 1300","Rs. 900","Rs. 645","Rs. 795","Rs. 520","Rs. 1876","Rs. 1395","Rs. 403","Rs. 670","Rs. 238","Rs. 142","Rs. 684","Rs. 109"};
    public static int[] fertiliserCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String[] fertiliserWeight = {"250ml", "500ml", "1l","5l"};
    double[] m = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_recycler,container,false);
        menuRecyclerView = (RecyclerView)v.findViewById(R.id.menu_recycler_view);
        menuView = (CardView)v.findViewById(R.id.menu);
        viewCart = (FloatingActionButton)v.findViewById(R.id.viewCart);
        notify1 = (TextView)v.findViewById(R.id.notifyCount);
        displayMenu();
        return v;
    }

    public void displayMenu(){
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MenuListItem> listItems = new ArrayList<>();

        for(int i=0;i<15;i++){
            MenuListItem listItem = new MenuListItem(f[i],fertiliserImage[i],fertiliserPrice[i],"0");
            listItems.add(listItem);
        }

        menuAdapter = new MenuAdapter(listItems,getContext(),fertiliserCount,fertiliserWeight,m,notify1);
        menuRecyclerView.setAdapter(menuAdapter);

        viewCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(),CartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pos",1);
        bundle.putIntArray("itemCountFertiliser",fertiliserCount);
        bundle.putDoubleArray("weightFertiliser",m);
        intent.putExtras(bundle);
        notify1.setText("");
        ctr = 0;
        notify1.setVisibility(View.INVISIBLE);
        startActivity(intent);

    }
}
