package com.blackey.fuel_delivery_app2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fueltype extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    static DatabaseReference Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fueltype);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        Data = FirebaseDatabase.getInstance().getReference();
        setPrices();

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("PETROL"));
        tabLayout.addTab(tabLayout.newTab().setText("DIESEL"));
        tabLayout.addTab(tabLayout.newTab().setText("ENGINE OIL"));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        Petrol petrol = new Petrol();
                        return petrol;
                    case 1:
                        Diesel diesel = new Diesel();
                        return diesel;
                    case 2:
                        EngineOil engineOil = new EngineOil();
                        return engineOil;
                    default:
                        return null; // Handle unknown position with a default case
                }
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setPrices() {
        Data.child("Fuelprice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                common.ppo92 = Float.parseFloat(snapshot.child("PetrolOctane92").getValue().toString());
                common.ppo95 = Float.parseFloat(snapshot.child("PetrolOctane95").getValue().toString());
                common.pseo = Float.parseFloat(snapshot.child("SyntheticEngineOil").getValue().toString());
                common.psbo = Float.parseFloat(snapshot.child("SyntheticBlendOil").getValue().toString());
                common.pceo = Float.parseFloat(snapshot.child("ConventionalEngineOils").getValue().toString());
                common.plad = Float.parseFloat(snapshot.child("LankaAutoDiesel").getValue().toString());
                common.plsd4se4 = Float.parseFloat(snapshot.child("LankaSuperDiesel4StarEuro4").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        });
    }
}
