package com.everaldo.kudi2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.everaldo.kudi2.Fragments.BottomBarAccountFragment;
import com.everaldo.kudi2.Fragments.BottomBarHomeFragment;
import com.everaldo.kudi2.Fragments.BottomBarNotifFragment;
import com.everaldo.kudi2.Fragments.HomeFragment;
import com.everaldo.kudi2.Fragments.ProfileFragment;
import com.everaldo.kudi2.util.ProfileCameraListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ProfileCameraListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private BottomBarHomeFragment barHomeFragment;
    private BottomBarNotifFragment barNotifFragment;
    private BottomBarAccountFragment barAccountFragment;
    private ProfileFragment profileFragment;
    private boolean isDrawerOpen = false;
    private Fragment activeFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        /*if (savedInstanceState == null){
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
        }*/
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        barHomeFragment = new BottomBarHomeFragment();
        barNotifFragment = new BottomBarNotifFragment();
        barAccountFragment = new BottomBarAccountFragment();

        activeFragment = barHomeFragment;


        fragmentManager.beginTransaction().add(R.id.content_frame, barAccountFragment, "3").hide(barAccountFragment).commit();
        fragmentManager.beginTransaction().add(R.id.content_frame, barNotifFragment, "2").hide(barNotifFragment).commit();
        fragmentManager.beginTransaction().add(R.id.content_frame,barHomeFragment, "1").commit();



        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                isDrawerOpen = true;
            }
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                isDrawerOpen = false;
            }
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
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

    private void setFragment(Fragment fragment){
        FragmentTransaction trans  = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.content_frame, fragment);
        trans.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                if (!isDrawerOpen) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    isDrawerOpen = true;
                }
                else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    isDrawerOpen = false;
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                //navigationView.setItemBackgroundResource(R.color.colorPrimary);
                Toast.makeText(this, "Home nav clicked", Toast.LENGTH_SHORT).show();
                //setFragment(barHomeFragment);

                fragmentManager.beginTransaction().hide(activeFragment).show(barHomeFragment).commit();
                activeFragment = barHomeFragment;

                //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_notif:
                //navigationView.setItemBackgroundResource(R.color.colorAccent);
                //setFragment(barNotifFragment);
                //Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().hide(activeFragment).show(barNotifFragment).commit();
                activeFragment = barNotifFragment;

                return true;
            case R.id.nav_account:
                //navigationView.setItemBackgroundResource(R.color.colorPrimaryDark);
                //setFragment(barAccountFragment);
                //Toast.makeText(MainActivity.this, "Account", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().hide(activeFragment).show(barAccountFragment).commit();
                activeFragment = barAccountFragment;
                return true;
        }

        return false;
    }

    @Override
    public void onProfileCameraClick() {


        // Here, this is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permisson Granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 100);
        }
        else{
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 100);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK)
        {
            ImageView imageView = findViewById(R.id.image_profile);
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(theImage);
        }
    }
}
