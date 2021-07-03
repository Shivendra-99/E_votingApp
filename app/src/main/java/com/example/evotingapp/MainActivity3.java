package com.example.evotingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.evotingapp.Fragment.DashBorad;
import com.example.evotingapp.Fragment.FeedBackFragment;
import com.example.evotingapp.Fragment.ResultFragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.evotingapp.Fragment.VoteFragment;
import com.example.evotingapp.model.SetData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    FirebaseAuth mauth;
    private FirebaseFirestore db;
    private TextInputLayout textInputLayout;
    private CollectionReference getCollectionReference2;
    private AutoCompleteTextView spinner;
    List<SetData> list;
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mauth = FirebaseAuth.getInstance();
        bottomNavigation = findViewById(R.id.navigation_bar);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.vote));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.poll));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.person));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.feedback));
        if(savedInstanceState==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, new VoteFragment())
                    .commit();
        }
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new VoteFragment();
                        break;
                    case 2:
                        fragment = new ResultFragment();
                        break;
                    case 3:
                        fragment = new DashBorad();
                        break;
                    case 4:
                        fragment = new FeedBackFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });
        bottomNavigation.show(1, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Clicked " + item.getId(), Toast.LENGTH_LONG).show();
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Reclicked " + item.getId(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
               FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
                finish();
               return true;
            case R.id.home:
                loadFragment(new VoteFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}