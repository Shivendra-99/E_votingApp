package com.example.evotingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.evotingapp.Fragment.Verificatio_List;
import com.example.evotingapp.Fragment.SetDateAndTIme;

public class MainActivity4 extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        bottomNavigation = findViewById(R.id.navigation_bar);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.verified_user));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.verification_list));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.date));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.feedback));
        if(savedInstanceState==null) {
            Verificatio_List fragment=new Verificatio_List();
            Bundle bundle=new Bundle();
            bundle.putString("GetUser","verified");
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                if(item.getId()==1){
                    fragment=new Verificatio_List();
                    Bundle bundle=new Bundle();
                    bundle.putString("GetUser","verified");
                    fragment.setArguments(bundle);
                }else if(item.getId()==2){
                    fragment=new Verificatio_List();
                    Bundle bundle=new Bundle();
                    bundle.putString("GetUser","Verification");
                    fragment.setArguments(bundle);
                }else if(item.getId()==3){
                    fragment=new SetDateAndTIme();
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
}