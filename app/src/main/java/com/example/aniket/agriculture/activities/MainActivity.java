package com.example.aniket.agriculture.activities;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aniket.agriculture.fragments.BuyFragment1;
import com.example.aniket.agriculture.fragments.CropDetailsFragment;
import com.example.aniket.agriculture.R;
import com.example.aniket.agriculture.fragments.WeatherFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity{
    FrameLayout fragment_container;

   private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_container = (FrameLayout)findViewById(R.id.fragment_container);

        int pos = getIntent().getIntExtra("cardPosition",0);

        if(pos == 1){
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            transaction1.setCustomAnimations(R.anim.enter_right,R.anim.exit_left);
            transaction1.replace(R.id.fragment_container,new CropDetailsFragment()).commit();
        }else if(pos == 2){
            FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
            transaction2.setCustomAnimations(R.anim.enter_right,R.anim.exit_left);
            transaction2.replace(R.id.fragment_container,new BuyFragment1()).commit();
        }else if(pos == 3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WeatherFragment()).commit();
        }else{
            Intent intent = new Intent(this,MapsActivity.class);
            startActivity(intent);
        }


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav_view);

        disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        circularRevealActivity();
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_crop:
                        circularRevealActivity();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CropDetailsFragment()).commit();
                        break;
                    case R.id.nav_buy:
                        circularRevealActivity();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BuyFragment1()).commit();
                        break;
                    case R.id.nav_location:
                        circularRevealActivity();
                        Intent intent1 = new Intent(MainActivity.this,MapsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_weather:
                        circularRevealActivity();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WeatherFragment()).commit();
                        break;

                }
                return true;
            }
        });

    }

    private void circularRevealActivity() {

        int cx = fragment_container.getWidth() / 2;
        int cy = fragment_container.getHeight();

        float finalRadius = Math.max(fragment_container.getWidth(), fragment_container.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(fragment_container, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        fragment_container.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    // Method for disabling ShiftMode of BottomNavigationView
    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);

                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
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
                Intent login_intent = new Intent(MainActivity.this, LoginActivity.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(login_intent);
                finish();
                break;
            case R.id.help:
                Intent intent = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }


}
