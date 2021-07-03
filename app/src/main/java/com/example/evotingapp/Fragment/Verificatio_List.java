package com.example.evotingapp.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evotingapp.R;
import com.example.evotingapp.model.userData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
public class Verificatio_List extends Fragment {
private RecyclerView recyclerView;
private FirebaseFirestore firebaseFirestore;
private FirestoreRecyclerAdapter adapter;
Query query;
TextView textView;
  public Verificatio_List(){
  }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_verificatio__list, container, false);
          RecyclerView recyclerView=view.findViewById(R.id.Recycler);
           TextView textView=view.findViewById(R.id.textoflist);
            firebaseFirestore=FirebaseFirestore.getInstance();
            String s= getArguments().getString("GetUser");
            if(s.equals("verified")) {
                query = firebaseFirestore.collection("Verified_User");
                textView.setText("List of Verified User");
            }else{
                query = firebaseFirestore.collection("Data_for_Verification");
                textView.setText("List For Verification of User");
            }
        FirestoreRecyclerOptions<userData> options=new FirestoreRecyclerOptions.Builder<userData>().setQuery(query,userData.class)
                .build();
        adapter= new FirestoreRecyclerAdapter<userData, DaoDataViewModel>(options) {
            @NonNull
            @Override
            public DaoDataViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);
                return new DaoDataViewModel(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull DaoDataViewModel holder, int position, @NonNull userData model) {
                holder.name.setText(model.getUserName());
                holder.mobi.setText(model.getMobile_Number());
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Verification_user_information user_information=new Verification_user_information();
                        Bundle arg=new Bundle();
                        String s= getArguments().getString("GetUser");
                        if(s.equals("verified")) {
                           arg.putString("val","true");
                        }else{
                            arg.putString("val","false");
                        }
                        arg.putString("Name",model.getUserName());
                        arg.putString("mobile",model.getMobile_Number());
                        arg.putString("father",model.getFatherName());
                        arg.putString("dob",model.getDob());
                        arg.putString("aadhar",model.getAadhar_Card());
                        arg.putString("voter",model.getVoterId());
                        arg.putString("gender",model.getGender());
                        arg.putString("area",model.getArea());
                        arg.putString("state",model.getState());
                        arg.putString("block",model.getBlock());
                        arg.putString("District",model.getDistrict());
                        arg.putString("pincode",model.getPincode());
                        user_information.setArguments(arg);
                        getFragmentManager().beginTransaction().replace(R.id.frame_layout,user_information).addToBackStack("Verification_List").commit();
                    }
                });
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

      return view;
    }

    private class DaoDataViewModel extends RecyclerView.ViewHolder {
        TextView name, mobi;

        public DaoDataViewModel(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name1);
            mobi = itemView.findViewById(R.id.mobile1);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}