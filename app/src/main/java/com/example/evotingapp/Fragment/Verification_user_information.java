 package com.example.evotingapp.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evotingapp.Dao.DaoData;
import com.example.evotingapp.R;
import com.example.evotingapp.model.userData;
import com.google.firebase.firestore.FirebaseFirestore;

public class Verification_user_information extends Fragment{
   TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    String userId,userName,father,aadhar,dob,pincode,state,block,area,gender,District,mobile,voter,password,verified;
    Button b1,b2;
   public Verification_user_information(){
   }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_verification_user_information, container, false);
       b1=view.findViewById(R.id.confirm);
       b2=view.findViewById(R.id.Reject);
       String y=getArguments().getString("val");
       if(y.equals("true")){
           b1.setVisibility(View.INVISIBLE);
           b2.setVisibility(View.INVISIBLE);
       }else{
           b1.setVisibility(View.VISIBLE);
           b2.setVisibility(View.VISIBLE);
       }
       String name=getArguments().getString("Name");
       String mobile= getArguments().getString("mobile");
       String father= getArguments().getString("father");
       String dob= getArguments().getString("dob");
       String aadhar= getArguments().getString("aadhar");
       String voter= getArguments().getString("voter");
       String gender= getArguments().getString("gender");
       String area= getArguments().getString("area");
       String state= getArguments().getString("state");
       String block= getArguments().getString("block");
       String ditrict= getArguments().getString("District");
       String pincode= getArguments().getString("pincode");
       t1=view.findViewById(R.id.Name);
       t2=view.findViewById(R.id.fade);
       t3=view.findViewById(R.id.dob1);
       t4=view.findViewById(R.id.mobile5);
       t5=view.findViewById(R.id.aadhar2);
       t6=view.findViewById(R.id.voter2);
       t7=view.findViewById(R.id.gender2);
       t8=view.findViewById(R.id.area2);
       t9=view.findViewById(R.id.state2);
       t10=view.findViewById(R.id.block2);
       t11=view.findViewById(R.id.District2);
       t12=view.findViewById(R.id.pin2);
       t1.setText(name);
       t2.setText(father);
       t3.setText(dob);
       t4.setText(mobile);
       t5.setText(aadhar);
       t6.setText(voter);
       t7.setText(gender);
       t8.setText(area);
       t9.setText(state);
       t10.setText(block);
       t11.setText(ditrict);
       t12.setText(pincode);
       return view;
    }
    public void Confirm(View view) {
        DaoData daoData=new DaoData();
        daoData.deleteUser(userId);
        String mess=daoData.get();
        userData userData=new userData(userName,father,dob,gender,pincode,District,state,block,area,mobile,aadhar,voter,password,userId);
        DaoData daoData1=new DaoData();
        daoData1.VerifiedUser(userData);
        Toast.makeText(getContext(), mess, Toast.LENGTH_LONG).show();
    }
    public void Reject(View view) {
        DaoData daoData=new DaoData();
        daoData.deleteUser(userId);
        String mess=daoData.get();
        Toast.makeText(getContext(), mess, Toast.LENGTH_LONG).show();
    }

}