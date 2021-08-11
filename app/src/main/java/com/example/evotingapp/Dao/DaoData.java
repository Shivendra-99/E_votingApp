package com.example.evotingapp.Dao;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.evotingapp.model.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DaoData {
    String mess="";
    String da="";
    List<SetData> setDataList=new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = db.collection("Data_for_Verification");
    CollectionReference collectionReference1=db.collection("Verified_User");
    CollectionReference getCollectionReference2=db.collection("SetVotingDateAndTime");
    public void addUser(userData userData) {
        collectionReference.document(userData.getUserid()).set(userData);
    }
    public void deleteUser(String userId)
    {
        db.collection("Data_for_Verification").document(userId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mess="Verification Done";
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mess=e.getMessage().toString();
            }
        });
    }
    public String get(){
        return mess;
    }
    public void VerifiedUser(userData userData) {
        collectionReference1.document(userData.getUserid()).set(userData);
    }
    public void setVotingtime(setVotingDate setVotingDate){
        getCollectionReference2.document(setVotingDate.getBlock()).set(setVotingDate);
    }
    public CollectionReference getData(){
       return getCollectionReference2;
    }
    public List<SetData> getCond(){
        Log.d("count Data",setDataList.get(0).getName());
        return setDataList;
    }
}
