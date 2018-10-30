package com.example.aniket.agriculture.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aniket.agriculture.R;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class HomeActivity extends AppCompatActivity {

    LinearLayout card1,card2,card3,card4;
    SliderLayout sliderLayout;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.SLIDE); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        card1 = (LinearLayout)findViewById(R.id.card1);
        card2 = (LinearLayout)findViewById(R.id.card2);
        card3 = (LinearLayout)findViewById(R.id.card3);
        card4 = (LinearLayout)findViewById(R.id.card4);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("cardPosition",1);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("cardPosition",2);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("cardPosition",3);
                startActivity(intent);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("cardPosition",4);
                startActivity(intent);
            }
        });

        setSliderViews();

    }
    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.wheat);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.rice);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.sugarcane);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.oats);
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);

            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent login_intent = new Intent(HomeActivity.this, LoginActivity.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login_intent);
                finish();
                break;
            case R.id.help:
                Intent intent = new Intent(HomeActivity.this,HelpActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}