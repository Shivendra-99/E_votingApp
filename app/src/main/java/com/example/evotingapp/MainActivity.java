package com.example.evotingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.evotingapp.Adopter.CustomeProgressDialog;
import com.example.evotingapp.model.userData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
TextInputLayout layout;
TextInputLayout textInputLayout;
FirebaseAuth mauth;
FirebaseUser user;
CustomeProgressDialog customeProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=findViewById(R.id.password_input);
        textInputLayout=findViewById(R.id.textInput);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        customeProgressDialog=new CustomeProgressDialog(MainActivity.this);
    }
    private boolean validPass(){
        String password= Objects.requireNonNull(layout.getEditText()).getText().toString().trim();
        if(password.isEmpty())
        {
            layout.setError("Password can not be Empty");
            return false;
        }
        else if(password.length()<8)
        {
            layout.setError("Password is too short");
            return false;
        }
       else
        {
           layout.setError(null);
           layout.setErrorEnabled(false);
           return true;
        }
    }
    private boolean validInput(){
        String userid= Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
        if(userid.isEmpty())
        {
            textInputLayout.setError("User id can not be Empty");
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }
    public void confirm(View view) {
        if(!validPass() | !validInput())
        {
            return;
        }
        Toast.makeText(MainActivity.this,"Clicked log in",Toast.LENGTH_LONG).show();
        if(Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim().equals("9936120982") && layout.getEditText().getText().toString().trim().equals("12345678")){
            Intent b=new Intent(MainActivity.this,MainActivity4.class);
            startActivity(b);
            customeProgressDialog.show();
        }
        else {
            customeProgressDialog.show();
            verification_of_Number();
        }
    }
    public void createAccount(View view) {
        Intent intent=new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(user!=null)
        {
            Intent intent=new Intent(MainActivity.this,MainActivity3.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(user!=null) {
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            startActivity(intent);
            finish();
        }
    }
    private void verification_of_Number(){
        String phoneNumber="+91"+textInputLayout.getEditText().getText().toString().trim();
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mauth).setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            signInwithPhoneAuth(phoneAuthCredential);
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(MainActivity.this,"Incorrect Phone Number",Toast.LENGTH_LONG).show();
            customeProgressDialog.cancel();
        }
    };
    private  void signInwithPhoneAuth(PhoneAuthCredential phoneAuthCredential)
    {
        mauth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                          String ui=task.getResult().getUser().getUid();
                          updateData(ui);
                           Toast.makeText(MainActivity.this,"Login Completed",Toast.LENGTH_LONG).show();
                            customeProgressDialog.cancel();
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(MainActivity.this,"Incorrect Number",Toast.LENGTH_LONG).show();
                                customeProgressDialog.cancel();
                            }
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                customeProgressDialog.cancel();
            }
        });
    }
    private void updateData(String ui) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = db.collection("Verified_User").document(ui);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userData set = document.toObject(userData.class);
                        if (set.getPassword().equals(layout.getEditText().getText().toString())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                            startActivity(intent);
                            finish();
                        } else {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(MainActivity.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"This User is not Exits",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Error is coming",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}