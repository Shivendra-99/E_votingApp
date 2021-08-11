package com.example.evotingapp.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.evotingapp.R;
import com.example.evotingapp.model.SetData;
import com.example.evotingapp.model.setVotingDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.Executor;
public class VoteFragment extends Fragment {
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    Button button;
    AutoCompleteTextView spinner;
    ArrayList<String> has;
    ArrayList<SetData> list;
    ArrayList<String> li=new ArrayList<>();
    public VoteFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vote, container, false);
        list=new ArrayList<>();
        has=new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        spinner=v.findViewById(R.id.spinner1);
        firebaseUser = firebaseAuth.getCurrentUser();
        button = v.findViewById(R.id.Verification);
        if (firebaseUser != null) {

            BiometricManager biometricManager=BiometricManager.from(getContext());
            switch (biometricManager.canAuthenticate()){
                case BiometricManager.BIOMETRIC_SUCCESS:
                    Toast.makeText(getContext(),"Finger Print sensor is Present",Toast.LENGTH_LONG).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Toast.makeText(getContext(),"Your Device Do not Have Finger Print ",Toast.LENGTH_LONG).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Toast.makeText(getContext(),"Your Device Error No Hardware",Toast.LENGTH_LONG).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Toast.makeText(getContext(),"No Finger Print Enrolled",Toast.LENGTH_LONG).show();
                    break;
            }
        Executor executor= ContextCompat.getMainExecutor(getContext());
        BiometricPrompt biometricPrompt=new BiometricPrompt(getActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getContext(),errString.toString(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getContext(),"Authentication Done",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
            }
        });
            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Verification")
                    .setDescription("Use Your Finger Print To For the Vote")
                    .setNegativeButtonText("Cancel")
                    .build();
            button.setOnClickListener(v1 -> biometricPrompt.authenticate(promptInfo));
        }
        ArrayList<String> b=new ArrayList<>();
        b.add("Shivendra");
        b.add("Satya");
        b.add("Krishna");
        b.add("Shailendra");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,b);
        spinner.setThreshold(1);
        spinner.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = db.collection("SetVotingDateAndTime").document("Kunda");
        docIdRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    setVotingDate setVotingDate=document.toObject(setVotingDate.class);
                    assert setVotingDate != null;

                }else{
                    Toast.makeText(getContext(),"This User is not Exits",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getContext(),"Error is coming",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show());
        return v;
    }
}