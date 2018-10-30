package com.example.aniket.agriculture.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aniket.agriculture.R;
import com.example.aniket.agriculture.activities.DetailsActivity;
import com.example.aniket.agriculture.global_data.GlobalData;
import com.example.aniket.agriculture.model_classes.CropDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    private List<CropDetails> mData;
    private Hashtable<String,CropDetails> cropHashtable = new Hashtable<String,CropDetails>();
    public FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference=firebaseDatabase.getReference("crops");
    public RecyclerViewAdapter(Context mContext, List<CropDetails> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view= mInflater.inflate(R.layout.cardview_item_crop,parent,false);
        /*databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    CropDetails cropdetails=new CropDetails();
                    cropdetails.setTitle(ds.getValue(CropDetails.class).getTitle());
                    cropdetails.setType(ds.getValue(CropDetails.class).getType());
                    cropdetails.setSeason(ds.getValue(CropDetails.class).getSeason());
                    cropdetails.setSoiltype(ds.getValue(CropDetails.class).getSoiltype());
                    cropdetails.setDescription(ds.getValue(CropDetails.class).getDescription());

                    cropHashtable.put(ds.getValue(CropDetails.class).getTitle(),cropdetails);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tv_crop_name.setText(mData.get(position).getTitle());
        //holder.iv_crop_thumbnail.setImageResource(mData.get(position).getThumbnails());
        Glide.with(mContext).load(mData.get(position).getThumbnail()).into(holder.iv_crop_thumbnail);
        //try


        holder.linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
            //Toast.makeText(mContext, mData.get(position).getCropName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, DetailsActivity.class);
            String name= mData.get(position).getTitle();

            CropDetails cropdetails=new CropDetails();
            cropdetails=(CropDetails) GlobalData.cropHashtable.get(name);
            String Description,season,soiltype,type;
            Description=cropdetails.getDescription();
            season=cropdetails.getSeason();
            soiltype=cropdetails.getSoiltype();
            type=cropdetails.getType();

            ActivityOptionsCompat optionsCompat;
            Pair<View, String> pair1= Pair.create((View)holder.iv_crop_thumbnail,"myImage");
            Pair<View, String> pair2= Pair.create((View)holder.tv_crop_name,"myText");

           optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext,pair1,pair2);
           intent.putExtra("Title",name);
           intent.putExtra("Description",Description);
           intent.putExtra("Season",season);
           intent.putExtra("Type",type);
           intent.putExtra("SoilType",soiltype);
           intent.putExtra("photo",mData.get(position).getThumbnail());
           intent.putExtra("name",name);

            mContext.startActivity(intent,optionsCompat.toBundle());

        }
    });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_crop_name;

        ImageView iv_crop_thumbnail;
        ConstraintLayout linearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_crop_name=(TextView)itemView.findViewById(R.id.crop_text_id);
            iv_crop_thumbnail=(ImageView)itemView.findViewById(R.id.crop_image_id);
            linearLayout=(ConstraintLayout)itemView.findViewById(R.id.Linear_layout);
        }
    }




}
