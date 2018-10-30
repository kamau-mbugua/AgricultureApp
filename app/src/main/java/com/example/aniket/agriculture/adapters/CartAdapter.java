package com.example.aniket.agriculture.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aniket.agriculture.model_classes.CartData;
import com.example.aniket.agriculture.R;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private  ArrayList<CartData> cartDataArrayList;
    private LayoutInflater mInflater;
    private Context context;

    public CartAdapter(Context context, ArrayList<CartData> cartDataArrayList){
        this.cartDataArrayList = cartDataArrayList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return cartDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view == null){
            view = mInflater.inflate(R.layout.cart_content,null);
            holder = new ViewHolder();
            holder.cartCropName = (TextView)view.findViewById(R.id.cart_crop_name);
            holder.cartCropPrice = (TextView)view.findViewById(R.id.cart_crop_price);
            holder.cartCropAmount = (TextView)view.findViewById(R.id.cart_crop_amount);
            holder.cartCropWeight = (TextView)view.findViewById(R.id.cart_crop_weight);
            holder.cartCropImage = (ImageView)view.findViewById(R.id.cart_crop_image);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.cartCropName.setText(cartDataArrayList.get(position).getCropName());
        holder.cartCropPrice.setText(Double.toString(cartDataArrayList.get(position).getCropPrice()));
        String a = Integer.toString(cartDataArrayList.get(position).getCropAmount());
        holder.cartCropAmount.setText(a);
        holder.cartCropWeight.setText(cartDataArrayList.get(position).getCropWeight());
        Glide.with(context).load(cartDataArrayList.get(position).getCropImage()).into(holder.cartCropImage);

        return view;
    }
    static class ViewHolder{
        TextView cartCropName;
        TextView cartCropPrice;
        TextView cartCropAmount;
        TextView cartCropWeight;
        ImageView cartCropImage;
    }
}