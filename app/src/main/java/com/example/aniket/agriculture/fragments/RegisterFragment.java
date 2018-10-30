package com.example.aniket.agriculture.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aniket.agriculture.R;
import com.example.aniket.agriculture.model_classes.UserDetails;
import com.example.aniket.agriculture.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.android.volley.VolleyLog.TAG;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private TextView textViewRegister;

    public FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser ;

    DatabaseReference databaseUser= FirebaseDatabase.getInstance().getReference("users");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.register, container, false);

        buttonRegister = (Button) root.findViewById(R.id.buttonRegister);
        editTextEmail = (EditText)root. findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) root.findViewById(R.id.editTextPassword);
        editTextFirstName = (EditText)root. findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)root. findViewById(R.id.editTextLastName);
        textViewRegister = (TextView)root.findViewById(R.id.textViewRegister);
        buttonRegister.setOnClickListener(this);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return root;
    }
    private void registerUser(){
        final String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        final  String fname=editTextFirstName.getText().toString().trim();
        final String lname=editTextLastName.getText().toString().trim();
        if(TextUtils.isEmpty(fname)&&TextUtils.isEmpty(lname)) {
            Toast.makeText(getContext(),"Incorrect name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            //Email is empty
            Toast.makeText(getContext(),"Please enter email id ",Toast.LENGTH_SHORT).show();
            //stopping execution
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(getContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            //stopping execution
            return;
        }
        //if all are valid . all good
//        progressDialog.setMessage("Registering UserDetails...!!");
        //      progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user registered successfully
                    String id = databaseUser.push().getKey();
                    UserDetails user = new UserDetails(fname, lname, id, email);
                    databaseUser.child(id).setValue(user);

                    firebaseUser = firebaseAuth.getCurrentUser();

                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
                    Toast.makeText(getContext(), "Registration Successfull.!!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
                else
                {
                    Toast.makeText(getContext(), "Registration Unsuccessfull,Please Try Again.!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }

    }
    public RegisterFragment(){

    }
}
