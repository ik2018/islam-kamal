package com.example.islamkamal.as7allklam;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import classfication.fragments.AuthorFragment;
import classfication.fragments.FramerBoxesFragment;
import classfication.fragments.MainFragment;
import classfication.fragments.PolticalFragment;
import classfication.fragments.ProverbsFragment;
import classfication.fragments.ThinkersFragment;
import helperclasses.AdaperItems;
import helperclasses.Operations;
import helperclasses.SaveSettings;
import helperclasses.SearchType;
import writer.Writer_Details_Activity;

public class As7aLlklam_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as7a_llklam_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(getBaseContext(), Writer_Details_Activity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //classifcations of writers
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frag,new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager=getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            fragmentManager.beginTransaction().replace(R.id.content_frag,new MainFragment()).commit();
            restoreActionBar("Home",R.drawable.person);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragmentManager.beginTransaction().replace(R.id.content_frag,new PolticalFragment()).commit();

        } else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction().replace(R.id.content_frag,new ThinkersFragment()).commit();
        } else if (id == R.id.nav_manage) {
            fragmentManager.beginTransaction().replace(R.id.content_frag,new AuthorFragment()).commit();
        } else if (id == R.id.nav_share) {
            fragmentManager.beginTransaction().replace(R.id.content_frag, new ProverbsFragment()).commit();
        } else if (id == R.id.nav_send) {
            fragmentManager.beginTransaction().replace(R.id.content_frag,new FramerBoxesFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //add items to action bar
    public void restoreActionBar(String title,int image )
    {
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        actionBar.setLogo(image);
        actionBar.setTitle(title);
    }
}
