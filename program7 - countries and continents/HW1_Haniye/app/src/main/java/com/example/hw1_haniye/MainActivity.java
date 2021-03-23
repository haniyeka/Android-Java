package com.example.hw1_haniye;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    ActionBarDrawerToggle drawerToggle;
    CountryData countryData = new CountryData();
    FloatingActionButton fabbut ;
    String Continent = "HW1_Haniye";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get toolbat
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get drawer layout
        drawer = findViewById(R.id.drawer_layout);

        //enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //enable drawer toggle
        drawerToggle = new ActionBarDrawerToggle(this,drawer,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(Continent);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(Continent);
                invalidateOptionsMenu();
            }
        };

        drawer.addDrawerListener(drawerToggle);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.fab).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Continent == "HW1_Haniye"){
                    Toast.makeText(getBaseContext(),"You should first choose one continent",Toast.LENGTH_SHORT).show();
                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Add new country");
                    builder.setMessage("Enter country name");
                    builder.setIcon(R.drawable.ic_add);
                    builder.setCancelable(true);

                    final EditText input = new EditText(MainActivity.this);


                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    lp.setMargins(250,50,250,50);
                    input.setLayoutParams(lp);


                    builder.setView(input);

                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String str = input.getText().toString();
                            if(str != ""){
                                int contID = countryData.name2num(Continent);
                                if( countryData.add(str,contID)){
                                    Toast.makeText(MainActivity.this, "You added \""+ str + "\"! ",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here.

            Fragment fragment = null;
            int id = item.getItemId();
            Continent = "HW1_Haniye";
            switch (id){
                case R.id.Asia:
                    Continent = "asia";
                    fragment = new FragmentAsia(countryData);
                    break;
                case R.id.Africa:
                    Continent = "africa";
                    fragment = new FragmentAfrica(countryData);
                    break;
                case R.id.Europe:
                    Continent = "europe";
                    fragment = new FragmentEurope(countryData);
                    break;
                case R.id.Antarctica:
                    Continent = "Antarctica";
                    fragment = new FragmentAntarctica(countryData);
                    break;
                case R.id.Oceania:
                    Continent = "oceania";
                    fragment = new FragmentOceania(countryData);
                    break;
                case R.id.NorthAmerica:
                    Continent = "north america";
                    fragment = new FragmentNorthamerica(countryData);
                    break;
                case R.id.SouthAmerica:
                    Continent = "south america";
                    fragment = new FragmentSouthamerica(countryData);
                    break;
            }
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
