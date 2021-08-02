package com.example.evotingapp.Fragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.evotingapp.Dao.DaoData;
import com.example.evotingapp.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.evotingapp.model.setVotingDate;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
public class SetDateAndTIme extends Fragment {
    private TextInputLayout inputLayout, inputLayout2, pincode, state;
    private TextInputLayout startTime, district,CondidateName;
    private TextInputEditText block;
    private ProgressBar progressBar;
    int hour, minu;
    private ArrayList<String> list;
    private ScrollView scrollView;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public SetDateAndTIme(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_set_date_and_t_ime, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(INVISIBLE);
        Button add=view.findViewById(R.id.addUser);
        Button verify=view.findViewById(R.id.verify);
        Button submit=view.findViewById(R.id.submit);
        inputLayout = view.findViewById(R.id.EnterDate);
        inputLayout2 = view.findViewById(R.id.Block1);
        startTime = view.findViewById(R.id.StartTime);
        pincode = view.findViewById(R.id.EnterPincode);
        district = view.findViewById(R.id.District1);
        state = view.findViewById(R.id.state);
        scrollView = view.findViewById(R.id.scroll);
        block = view.findViewById(R.id.block);
        CondidateName=view.findViewById(R.id.userInput);
        list=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        storageReference=FirebaseStorage.getInstance().getReference("Upload");
        databaseReference= FirebaseDatabase.getInstance().getReference("upload");
        inputLayout.getEditText().setOnClickListener((View v) -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        inputLayout.getEditText().setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
        });
        inputLayout2.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        inputLayout2.getEditText().setText(date);
                    }
                }, year1, month1, day1);
                datePickerDialog.show();
            }
        });
        add.setOnClickListener((View v) -> {
                list.add(CondidateName.getEditText().getText().toString());
                Snackbar snackbar = Snackbar.make(getContext(), scrollView, "Addition Of Candidate is Done", Snackbar.LENGTH_LONG);
                snackbar.show();
                CondidateName.getEditText().setText("");
    });
        submit.setOnClickListener((View v) -> {
            progressBar.setVisibility(VISIBLE);
            setVotingDate setVotingDate = new setVotingDate(list,inputLayout.getEditText().getText().toString().trim(), inputLayout2.getEditText().getText().toString().trim(), startTime.getEditText().getText().toString().trim()
                    , block.getText().toString(), pincode.getEditText().getText().toString().trim(), district.getEditText().getText().toString().trim(), state.getEditText().getText().toString().trim());
            DaoData daoData = new DaoData();
            daoData.setVotingtime(setVotingDate);
            Snackbar snackbar = Snackbar.make(getContext(), scrollView, "Voting Date And Time Set Success", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar snackbar1 = Snackbar.make(scrollView, "Done", Snackbar.LENGTH_SHORT);
                            snackbar1.setTextColor(Color.GREEN);
                            snackbar1.show();
                            progressBar.setVisibility(INVISIBLE);
                        }
                    }).setActionTextColor(Color.GREEN);
            View snackView = snackbar.getView();
            TextView textView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.GREEN);
            snackbar.show();
        });
        startTime.getEditText().setOnClickListener((View v) ->{
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minu = minute;
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(0, 0, 0, hour, minu);
                        startTime.getEditText().setText(DateFormat.format("hh:mm aa", calendar1));
                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(hour, minu);
                timePickerDialog.show();
        });
        verify.setOnClickListener((View v)-> {
            stateList();
        });
        return view;
    }
    public void stateList() {
        progressBar.setVisibility(VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Toast.makeText(getContext(), "Please Wait", Toast.LENGTH_LONG).show();
        if (!valid(pincode.getEditText().getText().toString().trim())) {
            progressBar.setVisibility(INVISIBLE);
            return;
        }
        String url = "https://api.postalpincode.in/pincode/" + Objects.requireNonNull(pincode.getEditText().getText()).toString().trim();
        JsonArrayRequest json = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String s = response.getJSONObject(0).getString("Message").toString();
                    String k = response.getJSONObject(0).getString("Status").toString();
                    JSONArray jsonArray = response.getJSONObject(0).getJSONArray("PostOffice");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String sta = jsonObject.getString("State");
                    String dis = jsonObject.getString("District");
                    String bl=jsonObject.getString("Block");
                    pincode.setErrorEnabled(false);
                    pincode.setError(null);
                    state.getEditText().setText(sta);
                    district.getEditText().setText(dis);
                    block.setText(bl);
                    progressBar.setVisibility(INVISIBLE);
                } catch (Exception e) {
                    pincode.setErrorEnabled(true);
                    pincode.setError("Wrong Pincode Enter");
                    progressBar.setVisibility(INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Getting wrong message", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(INVISIBLE);
            }
        });
        queue.add(json);
    }

    private boolean valid(String password) {
        if (password.isEmpty()) {
            pincode.setError("Pincode can not be Empty");
            return false;
        } else if (password.length() < 6) {
            pincode.setError("InValid Pincode");
            return false;
        } else {
            pincode.setError(null);
            pincode.setErrorEnabled(false);
            return true;
        }
    }
}