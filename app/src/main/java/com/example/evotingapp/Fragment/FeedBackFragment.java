package com.example.evotingapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.evotingapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FeedBackFragment extends Fragment {
    TextInputLayout subject;
    TextInputLayout pho;
    public FeedBackFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_feed_back, container, false);
        Button  button=view.findViewById(R.id.button);
        subject=view.findViewById(R.id.subject);
        pho=view.findViewById(R.id.phone);
        TextInputLayout feed=view.findViewById(R.id.feedback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),subject.getEditText().getText().toString(),Toast.LENGTH_LONG).show();
                subject.getEditText().setText("");
                pho.getEditText().setText("");
                feed.getEditText().setText("");
            }
        });
        return view;
    }

}