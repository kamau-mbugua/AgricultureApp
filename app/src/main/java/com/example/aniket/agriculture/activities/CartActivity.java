package com.example.aniket.agriculture.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.aniket.agriculture.model_classes.CartData;
import com.example.aniket.agriculture.R;
import com.example.aniket.agriculture.adapters.CartAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class CartActivity extends AppCompatActivity {

    SwipeMenuListView cartListView;
    CartAdapter cartAdapter;
    TextView totalPrice, success;
    Button pay;
    public static double total = 0;
    int pos;


    private static final String CHANNEL_ID = "com.example.aniket.agriculture" ;

    Button proceed;

    TextView username, billTotal, otpNumber;
    EditText credit_number, cvv_number;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCropDatabaseReference;
    private DatabaseReference mFertiliserDatabaseReference;
    private FirebaseAuth mAuth;
    private String UiD;

    private PopupWindow window;
    private RelativeLayout cartLayout;

    ArrayList<CartData> cartData;

    public static int[] itemsCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static double[] w = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static int[] itemsCountFertiliser = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static double[] m = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] priceFertiliser = {150, 180, 1300, 900, 645, 795, 520, 1876,1395,403,670,238,142,684,109};
    int[] price = {400,350,1000,580,64,460,126,200,1200,450,3000,70,65,310,1000};

    int[] img = {R.drawable.bottlegourd,R.drawable.brinjal,R.drawable.cabbage,R.drawable.carrot,
    R.drawable.coriander,R.drawable.cucumber,R.drawable.garlic,R.drawable.greenchilli,
    R.drawable.ladyfinger,R.drawable.millet,R.drawable.oats,R.drawable.potato,
    R.drawable.spinach,R.drawable.sugarcane,R.drawable.tomato};
    int[] img1 = {R.drawable.slash,R.drawable.hexaban,R.drawable.hexamida,R.drawable.agent_plus,
            R.drawable.bio_humate,R.drawable.criyazyme,R.drawable.growtism,R.drawable.etherel,
            R.drawable.slash,R.drawable.hexaban,R.drawable.hexamida,R.drawable.agent_plus,
            R.drawable.bio_humate,R.drawable.criyazyme,R.drawable.growtism};

    String[] c = {"Bottle Gourd", "Brinjal", "Cabbage", "Carrot", "Coriander", "Cucumber", "Garlic", "Green Chilli", "LadyFinger", "Millet", "Oats", "Potato", "Spinach", "Sugarcane", "Tomato"};
    String[] c1 = {"Slash","Hexaban","Hexamida","Agent Plus","Bio-Humate","Criyazyme","Growstim","Ethrel","Slash","Hexaban","Hexamida","Agent Plus","Bio-Humate","Criyazyme","Growstim"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = (SwipeMenuListView) findViewById(R.id.cartListView);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        pay = (Button) findViewById(R.id.pay);
        cartLayout = (RelativeLayout) findViewById(R.id.cartLayout);

        pos = getIntent().getIntExtra("pos", 0);

        if (pos == 0) {
            itemsCount = getIntent().getIntArrayExtra("itemCount");
            w = getIntent().getDoubleArrayExtra("weights");
        } else if (pos == 1) {
            itemsCountFertiliser = getIntent().getIntArrayExtra("itemCountFertiliser");
            m = getIntent().getDoubleArrayExtra("weightFertiliser");
        }

        cartData = getCartData();//returns final listview


        for (int i = 0; i < 15; i++) {
            if (itemsCount[i] != 0) {
                total = total + itemsCount[i] * w[i] * price[i];
            }

            if (itemsCountFertiliser[i] != 0) {
                total = total + itemsCountFertiliser[i] * m[i] * priceFertiliser[i];
            }
        }


        String str = Double.toString(total);
        totalPrice.setText(str);

        cartAdapter = new CartAdapter(this, cartData);
        cartListView.setAdapter(cartAdapter);

        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        UiD = mAuth.getUid();

        mCropDatabaseReference = mFirebaseDatabase.getReference().child("order").child("Crops");
        mFertiliserDatabaseReference = mFirebaseDatabase.getReference().child("order").child("Fertilisers");


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());

                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));

                deleteItem.setWidth(160);

                deleteItem.setIcon(R.drawable.ic_delete);

                menu.addMenuItem(deleteItem);
            }
        };

        cartListView.setMenuCreator(creator);

        cartListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        removeItem(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < 15; i++) {
                    if (itemsCount[i] != 0) {
                        double t = w[i] * 1000;
                        String s = Double.toString(t);
                        s = s + "gm";
                        CartData cart = new CartData(UiD,c[i], price[i], s, itemsCount[i]);
                        mCropDatabaseReference.push().setValue(cart);
                    }

                    if (itemsCountFertiliser[i] != 0) {
                        double t = m[i] * 1000;
                        String s = Double.toString(t);
                        s = s + "ml";
                        CartData cart = new CartData(UiD,c1[i], priceFertiliser[i], s, itemsCountFertiliser[i]);
                        mFertiliserDatabaseReference.push().setValue(cart);
                    }
                }

                if (total != 0) {
                    showPopupWindow(total);
                } else {
                    Toast.makeText(CartActivity.this, "Bill amount is 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ArrayList<CartData> getCartData() {
        ArrayList<CartData> data = new ArrayList<CartData>();

        CartData cd;
        String s;
        total = 0;
        double perItemPrice ;

        for (int i = 0; i < 15; i++) {
            if (itemsCount[i] != 0) {
                cd = new CartData();
                cd.setCropName(c[i]);
                perItemPrice = price[i]*w[i]*itemsCount[i];
                cd.setCropPrice(perItemPrice);
                cd.setCropAmount(itemsCount[i]);
                cd.setCropImage(img[i]);
                double d = w[i] * 1000;
                s = Double.toString(d);
                s = s + "gm";
                cd.setCropWeight(s);
                data.add(cd);
            }

            if (itemsCountFertiliser[i] != 0) {
                cd = new CartData();
                cd.setCropName(c1[i]);
                perItemPrice = priceFertiliser[i]*m[i]*itemsCountFertiliser[i];
                cd.setCropPrice(perItemPrice);
                cd.setCropAmount(itemsCountFertiliser[i]);
                cd.setCropImage(img1[i]);
                double d = m[i] * 1000;
                s = Double.toString(d);
                s = s + "ml";
                cd.setCropWeight(s);
                data.add(cd);
            }

        }

        return data;

    }

    public void removeItem(final int position) {

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.slideright);
        anim.setDuration(500);
        View v = cartListView.getChildAt(position);
        v.startAnimation(anim);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                CartData deleteItem = cartData.remove(position);
                double itemPrice = deleteItem.getCropPrice();

                total = total - itemPrice;
                cartAdapter.notifyDataSetChanged();
                String str = Double.toString(total);
                totalPrice.setText(str);

            }
        }, anim.getDuration());
    }


    @SuppressLint("SetTextI18n")
    private void showPopupWindow(double total) {
        try {

            cartLayout.setBackgroundColor(Color.TRANSPARENT);

            LayoutInflater inflater = (LayoutInflater) CartActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.bill_popup, null);
            window = new PopupWindow(layout, 900, 1400, true);

            username = (TextView) layout.findViewById(R.id.username);
            billTotal = (TextView) layout.findViewById(R.id.bill_total);
            otpNumber = (TextView) layout.findViewById(R.id.otp_number);
            success = (TextView) layout.findViewById(R.id.succesfull);
            credit_number = (EditText)layout.findViewById(R.id.credit_number);
            cvv_number = (EditText)layout.findViewById(R.id.cvv_number);

            proceed = (Button) layout.findViewById(R.id.proceed);

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setOutsideTouchable(true);
            window.showAtLocation(layout, Gravity.CENTER, 10, 60);

            username.setText("");
            billTotal.setText(Double.toString(total));

            Random random = new Random();
            long otp = random.nextInt(100000);

            //SEND NOTIFICATIONS

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.wheat)
                    .setContentTitle("AgriApp")
                    .setContentText("OTP: " + Long.toString(otp))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Payment through SBI Credit Card"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(0, mBuilder.build());
            Toast.makeText(this,"OTP sent through app",Toast.LENGTH_SHORT).show();


            //SEND SMS OF OTP

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("8208149335",null,"OTP: " + Long.toString(otp),null,null);
            Toast.makeText(this,"SMS sent to registered number",Toast.LENGTH_SHORT).show();

            otpNumber.setText(Long.toString(otp));

            proceed.setEnabled(true);

            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String credit1 = credit_number.getText().toString().trim();
                    String cvv1 = cvv_number.getText().toString().trim();

                    if(credit1.length()!=12){
                        credit_number.setError("Invalid number");
                    }else if(cvv1.length()!=3){
                        cvv_number.setError("Invalid CVV");
                    }else{
                        Toast.makeText(CartActivity.this,"payment Successful ",Toast.LENGTH_SHORT).show();
                        cartData.removeAll(cartData);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                clearCart();
                            }
                        },2500);

                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearCart() {
        window.dismiss();
        totalPrice.setText("0.0");
    }
}


