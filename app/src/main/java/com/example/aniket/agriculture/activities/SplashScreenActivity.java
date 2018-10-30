package com.example.aniket.agriculture.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aniket.agriculture.R;
import com.example.aniket.agriculture.global_data.GlobalData;
import com.example.aniket.agriculture.model_classes.CropDetails;
import com.example.aniket.agriculture.model_classes.MarketData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "com.example.aniket.agriculture" ;
    ImageView splashImage;
    TextView splashText;
    RelativeLayout relativeLayout;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = firebaseDatabase.getReference("crops");
    private DatabaseReference databaseReference1 = firebaseDatabase.getReference("market").child("Maharashtra");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashImage = (ImageView)findViewById(R.id.splash_image);
        splashText = (TextView)findViewById(R.id.splash_text);
        relativeLayout = (RelativeLayout)findViewById(R.id.splash_layout);

        int splashTimeout = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this,LoginActivity.class);
                ActivityOptionsCompat activityOptionsCompat=
                        ActivityOptionsCompat.makeClipRevealAnimation(splashImage,
                                (splashImage.getScrollX()+splashImage.getWidth())/2,(splashImage.getScrollY()+splashImage.getHeight())/2,
                                splashImage.getWidth(),splashImage.getHeight());

                startActivity(i,activityOptionsCompat.toBundle());
                finish();
            }
        }, splashTimeout);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.slideup);
        splashImage.startAnimation(animation);
        splashText.startAnimation(animation1);

        fetchCropData();
        fetchMarketData();

        createNotificationChannel();
    }

    public void createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void fetchCropData() {
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CropDetails cropdetails = new CropDetails();
                    cropdetails.setTitle(ds.getValue(CropDetails.class).getTitle());
                    cropdetails.setType(ds.getValue(CropDetails.class).getType());
                    cropdetails.setSeason(ds.getValue(CropDetails.class).getSeason());
                    cropdetails.setSoiltype(ds.getValue(CropDetails.class).getSoiltype());
                    cropdetails.setDescription(ds.getValue(CropDetails.class).getDescription());

                    GlobalData.cropHashtable.put(ds.getValue(CropDetails.class).getTitle(), cropdetails);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchMarketData(){
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MarketData md = new MarketData();
                    md.setCropName(dataSnapshot1.getValue(MarketData.class).getCropName());
                    md.setDistrict(dataSnapshot1.getValue(MarketData.class).getDistrict());
                    md.setMarket(dataSnapshot1.getValue(MarketData.class).getMarket());
                    md.setPrice(dataSnapshot1.getValue(MarketData.class).getPrice());

                    GlobalData.marketData.put(dataSnapshot1.getValue(MarketData.class).getCropName(),md);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}