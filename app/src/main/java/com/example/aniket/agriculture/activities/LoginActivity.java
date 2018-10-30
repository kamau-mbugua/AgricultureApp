package com.example.aniket.agriculture.activities;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aniket.agriculture.global_data.GlobalData;
import com.example.aniket.agriculture.model_classes.CropDetails;
import com.example.aniket.agriculture.model_classes.MarketData;
import com.example.aniket.agriculture.R;
import com.example.aniket.agriculture.fragments.RegisterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonLogin;
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;
    private TextView textViewSignup;
    private TextView forgotPassword;
    private ImageView visibility;
    private RelativeLayout relativeLayout;
    public boolean flag = false;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmailLogin = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = (EditText) findViewById(R.id.editTextPasswordLogin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUP);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        visibility = (ImageView) findViewById(R.id.visibility);

        relativeLayout = (RelativeLayout) findViewById(R.id.layout_login);

        firebaseAuth = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(LoginActivity.this);
        visibility.setOnClickListener(LoginActivity.this);
        buttonLogin.setOnClickListener(LoginActivity.this);
        textViewSignup.setOnClickListener(LoginActivity.this);

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser() != null){
                    fetchUserData();
                    Intent logintent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(logintent);
                }
            }
        };


    }

    private void fetchUserData() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //String name = user.getDisplayName();
            //String email1 = user.getEmail();

            for(UserInfo profile : user.getProviderData()){
                String providerId = profile.getProviderId();
                String uid = profile.getUid();

                String name1 = profile.getDisplayName();
                //Log.e("Name",name1);
                //Toast.makeText(this,name1,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void userLogin() {
        String email = editTextEmailLogin.getText().toString().trim();
        final String password = editTextPasswordLogin.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            //Email is empty
            Toast.makeText(LoginActivity.this, "Please enter email id ", Toast.LENGTH_SHORT).show();
            //stopping execution
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        //if both valid . all good
        progressDialog.setMessage("Signing in...!!!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class), options.toBundle());
                    editTextPasswordLogin.getText().clear();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    editTextPasswordLogin.getText().clear();
                    editTextEmailLogin.getText().clear();
                }

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            userLogin();
        } else if (view == textViewSignup) {
            RegisterFragment Register = new RegisterFragment();
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            frameLayout.setId(R.id.fragmentLayout);
            setContentView(frameLayout);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, Register).commit();
        } else if (view == forgotPassword) {
            resetPassword();

        } else if (view == visibility) {
            if (!flag) {
                visibility.setImageResource(R.drawable.visibility_on);
                flag = true;
                editTextPasswordLogin.setTransformationMethod(null);
            } else {
                visibility.setImageResource(R.drawable.visibility_off);
                flag = false;
                editTextPasswordLogin.setTransformationMethod(new PasswordTransformationMethod());
            }


        }
    }

    private void resetPassword() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.reset_password);
        dialog.setTitle("Reset Password");
        final EditText emailID = (EditText) dialog.findViewById(R.id.emailID);
        Button button = (Button) dialog.findViewById(R.id.reset);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mail=emailID.getText().toString().trim();
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(LoginActivity.this,"Enter email id...!",Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    progressDialog.setMessage("Resetting..!!!");
                    progressDialog.show();
                    auth.sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Check your mail.!", Toast.LENGTH_SHORT).show();
                                        //getMarketData();
                                    } else {
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "resetting unsuccessful.!", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }

            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(relativeLayout.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(mAuthListener);
    }

}