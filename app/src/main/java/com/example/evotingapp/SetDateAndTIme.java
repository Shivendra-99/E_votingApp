package com.example.evotingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.evotingapp.Dao.DaoData;
import com.example.evotingapp.Fragment.Verificatio_List;
import com.example.evotingapp.model.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import javax.xml.transform.Result;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SetDateAndTIme extends AppCompatActivity {
    private TextInputLayout inputLayout, inputLayout2, pincode, state;
    private TextInputLayout startTime, block, district,CondidateName;
    private ProgressBar progressBar;
    int hour, minu;
    private final int pickImageRequest = 1;
    private Uri uri;
    private EditText signImage;
    private String url;
    private HashMap<String,String> hashMap;
    private ScrollView scrollView;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date_and_t_ime);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(INVISIBLE);
        inputLayout = findViewById(R.id.EnterDate);
        inputLayout2 = findViewById(R.id.Block1);
        startTime = findViewById(R.id.StartTime);
        pincode = findViewById(R.id.EnterPincode);
        district = findViewById(R.id.District1);
        signImage=findViewById(R.id.signImage);
        state = findViewById(R.id.state);
        scrollView = findViewById(R.id.scroll);
        block = findViewById(R.id.block);
        CondidateName=findViewById(R.id.userInput);
        hashMap=new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        storageReference=FirebaseStorage.getInstance().getReference("Upload");
        databaseReference= FirebaseDatabase.getInstance().getReference("upload");
        inputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SetDateAndTIme.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        inputLayout.getEditText().setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        inputLayout2.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SetDateAndTIme.this, new DatePickerDialog.OnDateSetListener() {
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
        startTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SetDateAndTIme.this, new TimePickerDialog.OnTimeSetListener() {
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
            }
        });
    }
    public void stateList(View view) {
        progressBar.setVisibility(VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        Toast.makeText(SetDateAndTIme.this, "Please Wait", Toast.LENGTH_LONG).show();
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
                    block.getEditText().setText(bl);
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
                Toast.makeText(SetDateAndTIme.this, "Getting wrong message", Toast.LENGTH_LONG).show();
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
    public void Submit(View view) {
        progressBar.setVisibility(VISIBLE);
        setVotingDate setVotingDate = new setVotingDate(hashMap,inputLayout.getEditText().getText().toString().trim(), inputLayout2.getEditText().getText().toString().trim(), startTime.getEditText().getText().toString().trim()
                , block.getEditText().getText().toString(), pincode.getEditText().getText().toString().trim(), district.getEditText().getText().toString().trim(), state.getEditText().getText().toString().trim());
        DaoData daoData = new DaoData();
        daoData.setVotingtime(setVotingDate);
        Snackbar snackbar = Snackbar.make(SetDateAndTIme.this, scrollView, "Voting Date And Time Set Success", Snackbar.LENGTH_INDEFINITE)
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
    }
    public void upload_Image(View view) {
        openFileChooser();
    }
    public void add_candidate(View view) {
       hashMap.put(CondidateName.getEditText().getText().toString(),url);
        Snackbar snackbar = Snackbar.make(SetDateAndTIme.this, scrollView, "Addition Of Candidate is Done", Snackbar.LENGTH_LONG);
        snackbar.show();
        CondidateName.getEditText().setText("");
        signImage.setText("");
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pickImageRequest);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pickImageRequest && resultCode== RESULT_OK && data!=null && data.getData()!=null){
            uri=data.getData();

            progressBar.setVisibility(VISIBLE);
            AddImage();
        }
    }
    public void AddImage(){
        StorageReference storageReference1=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        UploadTask uploadTask=storageReference1.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url=uri.toString();
                        uploadImage uploadImage=new uploadImage(signImage.getText().toString(),url);
                        String uploadId=databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(uploadImage);
                        Snackbar snackbar = Snackbar.make(SetDateAndTIme.this, scrollView, "Image upload Successfully", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        progressBar.setVisibility(INVISIBLE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SetDateAndTIme.this,e.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(INVISIBLE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Verificatio_List fragment=new Verificatio_List();
        Bundle bundle=new Bundle();
        bundle.putString("GetUser","verified");
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}