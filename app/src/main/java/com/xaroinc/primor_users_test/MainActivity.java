package com.xaroinc.primor_users_test;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.xaroinc.primor_users_test.View_UI.Fragments.Nav_fragments.NewUserFragment;
import com.xaroinc.primor_users_test.View_UI.Fragments.Nav_fragments.UsersListFragment;
import com.xaroinc.primor_users_test.View_UI.Fragments.NewUser_Fragments.PersonalFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sp;
    private static final String spFile = "spFile_contacts" ;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = getSharedPreferences(spFile,MODE_PRIVATE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //displaySelectedScreen(R.id.nav_newUser);
        displaySelectedScreen(R.id.nav_user_list);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            callExitApp();
        }
    }

   /*
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(int id)
    {
        Fragment fragment = null;
        switch (id){
            case R.id.nav_user_list:
                fragment = new UsersListFragment();
                break;
            case R.id.nav_newUser:
                fragment = new NewUserFragment();
                break;
            case R.id.nav_exit:
                callExitApp();
                break;
        }

        if (fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

    }

    private void callExitApp()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title);
        builder.setMessage(R.string.alert_mesage);
        builder.setIcon(R.drawable.logout);
        builder.setPositiveButton(R.string.alert_yes_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Toast.makeText(MainActivity.this, R.string.logout_success_msg, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.alert_no_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Toast.makeText(MainActivity.this, R.string.delete_cancel_message, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        editor = sp.edit();
        editor.clear().apply();
        //Toast.makeText(this, "onStop(): Data Cleared in SP", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        editor = sp.edit();
        editor.clear().apply();
       // Toast.makeText(this, "Data Cleared in SP", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }*/
}
