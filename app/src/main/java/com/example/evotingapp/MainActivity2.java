package com.example.evotingapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.evotingapp.Adopter.CustomeProgressDialog;
import com.example.evotingapp.Dao.DaoData;
import com.example.evotingapp.model.userData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {
  AutoCompleteTextView gen,area;
  TextInputEditText dob,pincode,district,state,block,user,father,mobi,aadh,voterId,pass,con_pass;
  TextInputLayout pin,userName,fatherName,birth,gender,District,sta,blo,are,mobile,aadhar,voter,Enter_pass,confirm_pass;
  String url;
    CustomeProgressDialog customeProgressDialog;
  FirebaseAuth mauth;
  FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        user=findViewById(R.id.user);
        father=findViewById(R.id.father);
        mobi=findViewById(R.id.mobile);
        aadh=findViewById(R.id.aadhar);
        voterId=findViewById(R.id.voter);
        pass=findViewById(R.id.password);
        con_pass=findViewById(R.id.con_pass);
        dob=findViewById(R.id.bob);
        gen=findViewById(R.id.gen);
        pincode=findViewById(R.id.pincode);
        pin=findViewById(R.id.pin);
        district=findViewById(R.id.dis);
        state=findViewById(R.id.sta);
        block=findViewById(R.id.blo);
        area=findViewById(R.id.are);
        userName=findViewById(R.id.userName);
        fatherName=findViewById(R.id.fatherName);
        birth=findViewById(R.id.birth);
        gender=findViewById(R.id.gender);
        District=findViewById(R.id.District);
        sta=findViewById(R.id.state);
        blo=findViewById(R.id.Block);
        are=findViewById(R.id.Area);
        mobile=findViewById(R.id.MobileNumber);
        aadhar=findViewById(R.id.aadharNumber);
        voter=findViewById(R.id.VoterId);
        Enter_pass=findViewById(R.id.EnterPassword);
        confirm_pass=findViewById(R.id.confirmPassword);
        mauth=FirebaseAuth.getInstance();
        customeProgressDialog=new CustomeProgressDialog(MainActivity2.this);
        firebaseFirestore=FirebaseFirestore.getInstance();
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        dob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str[]={"Male","Female","Transgender"};
                List<String> ls= Arrays.asList(str);
                ArrayAdapter j=new ArrayAdapter(MainActivity2.this,R.layout.gender_layout,ls);
                gen.setAdapter(j);
            }
        });
    }
    private void stateList(View view){
        customeProgressDialog.show();
        RequestQueue queue= Volley.newRequestQueue(this);
        Toast.makeText(MainActivity2.this,"Please Wait",Toast.LENGTH_LONG).show();
        if(!valid(pincode.getText().toString().trim()))
        {
            customeProgressDialog.cancel();
            return;
        }
        url="https://api.postalpincode.in/pincode/"+ Objects.requireNonNull(pincode.getText()).toString().trim();
        JsonArrayRequest json=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String s=response.getJSONObject(0).getString("Message").toString();
                    String k=response.getJSONObject(0).getString("Status").toString();
                    JSONArray jsonArray=response.getJSONObject(0).getJSONArray("PostOffice");
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String sta=jsonObject.getString("State");
                        String dis=jsonObject.getString("District");
                        String blo=jsonObject.getString("Block");
                    pin.setErrorEnabled(false);
                    pin.setError(null);
                        state.setText(sta);
                        district.setText(dis);
                        block.setText(blo);
                        List<String> arr=new ArrayList<>();
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String name=jsonObject1.getString("Name");
                            arr.add(name);
                        }
                        ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity2.this,R.layout.gender_layout,arr);
                        area.setAdapter(arrayAdapter);
                        Toast.makeText(MainActivity2.this,"Success",Toast.LENGTH_LONG).show();
                        customeProgressDialog.cancel();
                }
                catch (Exception e) {
                    pin.setErrorEnabled(true);
                    pin.setError("Wrong Pincode Enter");
                    customeProgressDialog.cancel();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,"Getting wrong message",Toast.LENGTH_LONG).show();
                customeProgressDialog.cancel();
            }
        });
        queue.add(json);
    }
    private boolean valid(String password) {
        if(password.isEmpty())
        {
            pin.setError("Pincode can not be Empty");
            return false;
        }
        else if(password.length()<6)
        {
            pin.setError("InValid Pincode");
            return false;
        }
        else
        {
            pin.setError(null);
            pin.setErrorEnabled(false);
            return true;
        }
    }
    private void Register(View view) {
        customeProgressDialog.show();
        if(!validate(user.getText().toString().trim(),father.getText().toString().trim(),dob.getText().toString().trim(),gen.getText().toString().trim(),area.getText().toString().trim(),pincode.getText().toString().trim(),
               district.getText().toString().trim(),state.getText().toString().trim(),block.getText().toString().trim(),mobi.getText().toString().trim(),aadh.getText().toString().trim(),voterId.getText().toString().trim(),
               pass.getText().toString().trim(),con_pass.getText().toString().trim()))
       {
           customeProgressDialog.cancel();
           return;
       }
       verification_of_Number();
    }
    private void verification_of_Number() {
        String phoneNumber="+91"+mobi.getText().toString().trim();
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mauth).setPhoneNumber(phoneNumber)
                .setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(MainActivity2.this,phoneNumber,Toast.LENGTH_LONG).show();
    }
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInwithPhoneAuth(phoneAuthCredential);
            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                 Toast.makeText(MainActivity2.this,e.getMessage(),Toast.LENGTH_LONG).show();
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
                            updateData();
                            customeProgressDialog.cancel();
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(MainActivity2.this,"Verification Faield",Toast.LENGTH_LONG).show();
                                customeProgressDialog.cancel();
                            }
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity2.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                customeProgressDialog.cancel();
            }
        });
    }
    private void updateData() {
       userData userData=new userData(user.getText().toString(),father.getText().toString(),dob.getText().toString(),gen.getText().toString(),pincode.getText().toString(),district.getText().toString(),state.getText().toString(),block.getText().toString(),area.getText().toString(),mobi.getText().toString(),aadh.getText().toString(), voterId.getText().toString(),pass.getText().toString(),mauth.getCurrentUser().getUid());
        DaoData daoData=new DaoData();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = db.collection("Data_for_Verification").document(userData.getUserid());
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(MainActivity2.this,"User is Already Exits",Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    } else {
                        daoData.addUser(userData);
                        Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity2.this,"Registration completed",Toast.LENGTH_LONG).show();
                        finish();
                        customeProgressDialog.cancel();
                    }
                }
                else{
                    Toast.makeText(MainActivity2.this,"Error is coming",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity2.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean validate(String trim, String trim1, String trim2, String trim3, String trim4, String trim5, String trim6, String trim7, String trim8, String trim9, String trim10, String trim11, String trim12, String trim13) {
        boolean f=true;
        if(trim.isEmpty() | trim.length()==0)
        {
            userName.setError("User Name Can not be Empty");
            userName.setErrorEnabled(true);
            f=false;
        }
        if(!trim.isEmpty())
        {
            userName.setError(null);
            userName.setErrorEnabled(false);
        }
        if(trim1.isEmpty() | trim1.length()==0)
        {
            fatherName.setError("Father Name Can not be Empty");
            fatherName.setErrorEnabled(true);
            f=false;
        }
        if(!trim1.isEmpty())
        {
            fatherName.setError(null);
            fatherName.setErrorEnabled(false);
        }
        String s=trim2.substring(trim2.length()-4,trim2.length());
        if(trim2.length()>2){
            if(s.compareTo("2004")>=0) {
                birth.setError("Candidate Should be 18 or 18+");
                birth.setErrorEnabled(true);
                f = false;
            }
        }
        if(trim2.isEmpty() | trim2.length()==0)
        {
            birth.setError("Data of Birth Can not be Empty");
            birth.setErrorEnabled(true);
            f=false;
        }
        if(trim3.isEmpty() | trim3.length()==0)
        {
            gender.setError("Gender Can not be Empty");
            gender.setErrorEnabled(true);
            f=false;
        }
        if(!trim3.isEmpty())
        {
            gender.setError(null);
            gender.setErrorEnabled(false);
        }
        if(trim4.isEmpty() | trim4.length()==0)
        {
            are.setError("Area Can not be Empty");
            are.setErrorEnabled(true);
            f=false;
        }
        if(!trim4.isEmpty())
        {
            are.setError(null);
            are.setErrorEnabled(false);
        }
        if(trim5.isEmpty() | trim5.length()==0)
        {
            pin.setError("Pincode Can not be Empty");
            pin.setErrorEnabled(true);
            f=false;
        }if(trim6.isEmpty() | trim6.length()==0)
        {
            District.setError("District Can not be Empty");
            District.setErrorEnabled(true);
            f=false;
        }
        if(!trim6.isEmpty())
        {
            District.setError(null);
            District.setErrorEnabled(false);
        }
        if(trim7.isEmpty() | trim7.length()==0)
        {
            sta.setError("State Can not be Empty");
            sta.setErrorEnabled(true);
           f=false;
        }
        if(!trim7.isEmpty())
        {
            sta.setError(null);
            sta.setErrorEnabled(false);
        }
        if(trim8.isEmpty() | trim8.length()==0)
        {
            blo.setError("Block Can not be Empty");
            blo.setErrorEnabled(true);
            f= false;
        }
        if(!trim8.isEmpty())
        {
            blo.setError(null);
            blo.setErrorEnabled(false);
        }
        if(trim9.isEmpty() | trim9.length()<10)
        {
            mobile.setError("Mobile Number Can not be Empty or less than Ten");
            mobile.setErrorEnabled(true);
            f=false;
        }
        if(!trim9.isEmpty() & trim9.length()==10)
        {
            mobile.setError(null);
            mobile.setErrorEnabled(false);
        }
        if(trim10.isEmpty() | trim10.length()==0)
        {
            aadhar.setError("Aadhaar Number Can not be Empty");
            aadhar.setErrorEnabled(true);
            f=false;
        }
        if(!trim10.isEmpty())
        {
            aadhar.setError(null);
            aadhar.setErrorEnabled(false);
        }
        if(trim11.isEmpty() | trim11.length()==0)
        {
            voter.setError("VoterId Can not be Empty");
            voter.setErrorEnabled(true);
            f=false;
        }
        if(!trim11.isEmpty())
        {
            voter.setError(null);
            voter.setErrorEnabled(false);
        }
        if(trim12.isEmpty() | trim12.length()==0)
        {
            Enter_pass.setError("Password Can not be Empty");
            Enter_pass.setErrorEnabled(true);
            f=false;
        }
        if(!trim12.isEmpty() & trim12.length()>=8)
        {
            Enter_pass.setError(null);
            Enter_pass.setErrorEnabled(false);
        }
        if(trim12.length()<8)
        {
            Enter_pass.setError("Password length should be more 8 ");
            Enter_pass.setErrorEnabled(true);
            f=false;
        }
        if(!trim12.equals(trim13))
        {
            Enter_pass.setError("Password should be same");
            Enter_pass.setErrorEnabled(true);
            f=false;
        }
        if(trim13.isEmpty() | trim13.length()==0)
        {
            confirm_pass.setError("Confirm Password Can not be Empty");
            confirm_pass.setErrorEnabled(true);
            f=false;
        }
        return f;
    }
}