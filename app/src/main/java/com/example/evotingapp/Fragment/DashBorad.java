package com.example.evotingapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evotingapp.MainActivity;
import com.example.evotingapp.MainActivity3;
import com.example.evotingapp.R;
import com.example.evotingapp.model.userData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashBorad extends Fragment {
    TextView UserNam,UserI,fathername1,districtName1,Areaname,pincodeEn,war;
    LinearLayout Pin,area,dist,fathe,userN,userI;
    public DashBorad() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dash_borad, container, false);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        UserNam=view.findViewById(R.id.UserNam);
        UserI=view.findViewById(R.id.UserI);
        fathername1=view.findViewById(R.id.fathername1);
        districtName1=view.findViewById(R.id.districtName1);
        Areaname=view.findViewById(R.id.Areaname);
        pincodeEn=view.findViewById(R.id.pincodeEn);
        Pin=view.findViewById(R.id.Pin);
        area=view.findViewById(R.id.area);
        dist=view.findViewById(R.id.dist);
        fathe=view.findViewById(R.id.fathe);
        userN=view.findViewById(R.id.userN);
        userI=view.findViewById(R.id.userI);
        war=view.findViewById(R.id.war);
        updateData(firebaseAuth.getCurrentUser().getUid());
        return view;
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
                        Pin.setVisibility(View.VISIBLE);
                        area.setVisibility(View.VISIBLE);
                        dist.setVisibility(View.VISIBLE);
                        fathe.setVisibility(View.VISIBLE);
                        userN.setVisibility(View.VISIBLE);
                        userI.setVisibility(View.VISIBLE);
                        war.setVisibility(View.INVISIBLE);
                        userData set = document.toObject(userData.class);
                        war.setVisibility(View.GONE);
                        UserI.setText(set.getUserid());
                        UserNam.setText(set.getUserName());
                        fathername1.setText(set.getFatherName());
                        districtName1.setText(set.getDistrict());
                        Areaname.setText(set.getArea());
                        pincodeEn.setText(set.getPincode());
                    }else{
                        Pin.setVisibility(View.INVISIBLE);
                        area.setVisibility(View.INVISIBLE);
                        dist.setVisibility(View.INVISIBLE);
                        fathe.setVisibility(View.INVISIBLE);
                        userN.setVisibility(View.INVISIBLE);
                        userI.setVisibility(View.INVISIBLE);
                        war.setVisibility(View.INVISIBLE);
                        war.setVisibility(View.VISIBLE);
                        war.setText("Dear User Your Not Verified By Admin");
                        Toast.makeText(getContext(),"This User is not Exits",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Error is coming",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}