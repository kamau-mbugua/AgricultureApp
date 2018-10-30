package com.example.aniket.agriculture.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aniket.agriculture.R;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getIncomingIntent();
    }

    public void getIncomingIntent(){
        // if(getIntent().hasExtra("photo")&&getIntent().hasExtra("name")&&getIntent().hasExtra("details")){
        String name,description,season,type,soiltype;

        int photo;
        Bundle bundle=getIntent().getExtras();
        name=bundle.getString("name");
        photo= bundle.getInt("photo");
        ImageView imageView=(ImageView)findViewById(R.id.imageonclick_id) ;
        Glide.with(DetailsActivity.this).load(photo).into(imageView);
        description=getIntent().getStringExtra("Description");
        season=getIntent().getStringExtra("Season");
        type=getIntent().getStringExtra("Type");
        soiltype=getIntent().getStringExtra("SoilType");

        TextView textView1=(TextView)findViewById(R.id.cat1);
        TextView textView2=(TextView)findViewById(R.id.cat2);
        TextView textView3=(TextView)findViewById(R.id.cat3);
        TextView textView4=(TextView)findViewById(R.id.cat4);
        TextView textView5=(TextView)findViewById(R.id.cat5);

        textView1.setText(name);
        textView2.setText(description);
        textView2.setMovementMethod(new ScrollingMovementMethod());
        textView3.setText(type);
        textView4.setText(season);
        textView5.setText(soiltype);


        // imageView.setImageResource(photo);


    }

}
