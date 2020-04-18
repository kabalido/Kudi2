package com.everaldo.kudi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.everaldo.kudi2.Fragments.HomeFragment;
import com.everaldo.kudi2.Fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeMainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (savedInstanceState == null){
            homeFragment = new HomeFragment();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.content_frame, homeFragment);
            //trans.replace(R.id.content_frame, new FragmentStep1());
            trans.commit();

            profileFragment = new ProfileFragment();
        }
        else{
            if (profileFragment == null)
                profileFragment = new ProfileFragment();
            if (homeFragment == null)
                homeFragment = new HomeFragment();
        }

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.menu_home:
                        menuItem.setChecked(true);
                        fragment = homeFragment;
                        break;
                    case R.id.menu_account:
                        menuItem.setChecked(true);
                        fragment = profileFragment;
                        //getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        //drawerLayout.closeDrawers();
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
