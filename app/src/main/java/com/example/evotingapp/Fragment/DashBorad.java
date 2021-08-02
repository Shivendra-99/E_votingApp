package com.example.evotingapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
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
    TextView UserNam,UserI,fathername1,districtName1,phonenumber,pincodeEn,war,area;
    public DashBorad() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dash_borad, container, false);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        UserNam=view.findViewById(R.id.userName);
        UserI=view.findViewById(R.id.userId);
        fathername1=view.findViewById(R.id.fathername);
        phonenumber=view.findViewById(R.id.phoneNumber);
        districtName1=view.findViewById(R.id.district);
        pincodeEn=view.findViewById(R.id.Pincode);
        area=view.findViewById(R.id.area);
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
                        pincodeEn.setVisibility(View.VISIBLE);
                        area.setVisibility(View.VISIBLE);
                        districtName1.setVisibility(View.VISIBLE);
                        fathername1.setVisibility(View.VISIBLE);
                        UserNam.setVisibility(View.VISIBLE);
                        UserI.setVisibility(View.VISIBLE);
                        war.setVisibility(View.INVISIBLE);
                        userData set = document.toObject(userData.class);
                        UserI.setText(set.getUserid());
                        UserNam.setText(set.getUserName());
                        fathername1.setText(set.getFatherName());
                        districtName1.setText(set.getDistrict());
                        area.setText(set.getArea());
                        phonenumber.setText(set.getMobile_Number());
                        pincodeEn.setText(set.getPincode());
                    }else{
                        pincodeEn.setVisibility(View.INVISIBLE);
                        area.setVisibility(View.INVISIBLE);
                        districtName1.setVisibility(View.INVISIBLE);
                        fathername1.setVisibility(View.INVISIBLE);
                        UserNam.setVisibility(View.INVISIBLE);
                        UserI.setVisibility(View.INVISIBLE);
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