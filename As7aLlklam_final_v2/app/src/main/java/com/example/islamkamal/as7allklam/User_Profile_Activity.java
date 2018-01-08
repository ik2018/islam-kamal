package com.example.islamkamal.as7allklam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import helperclasses.AdaperItems;
import helperclasses.Operations;
import helperclasses.UserProfile;

public class User_Profile_Activity extends AppCompatActivity {
ArrayList<UserProfile>user_list;
    TextView nameTxt,emailTxt;
    ImageView image;
    UserProfile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        profile=new UserProfile();

        user_list=new ArrayList<>();
        Bundle bundle=getIntent().getExtras();
        int user_id=bundle.getInt("user_id");
        String url="http://islamkamal.890m.com/twitter/userProfile.php?user_id="+user_id+"&op=1";
        Log.d("profile test :", url);
        new MyAsyncTack().execute(url);
        nameTxt=(TextView)findViewById(R.id.nametextView);
        nameTxt.setText(profile.getName());
        Log.d("profile test :", profile.getName());
        emailTxt=(TextView)findViewById(R.id.emailtextView);
        emailTxt.setText(profile.getEmail());
        Log.d("profile test :", profile.getEmail());
        image=(ImageView)findViewById(R.id.userimageView);
        Picasso.with(getBaseContext()).load(profile.getPicture()).into(image);
       // image.setImageResource(Integer.parseInt(profile.getPicture()));


    }


    //upload data to server
    public class MyAsyncTack extends AsyncTask<String,String,String> {

        ArrayList<UserProfile>list=new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(String... values) {
            try {
                JSONObject json ;
                json = new JSONObject(values[0]);
                try {
                    if (json.getString("msg") == null) {
                        return;
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if (json.getString("msg").equalsIgnoreCase("has data")) {

                    JSONArray tweets = new JSONArray(json.getString("info"));
                        JSONObject oneTweet = tweets.getJSONObject(0);
                    profile.setName(oneTweet.getString("name"));
                    profile.setEmail(oneTweet.getString("email"));
                    profile.setPicture(oneTweet.getString("picture_path"));



                } else if (json.getString("msg").equalsIgnoreCase("no data")) {
                    Log.d("User Profile","empty query");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String newData;
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(30000);
                //get response data
                try {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    //convert stream to string
                    Operations op = new Operations(getBaseContext());
                    newData = op.convertInputToString(inputStream);
                    // send o display data
                    publishProgress(newData);
                } finally {
                    urlConnection.disconnect();
                }

            } catch (Exception e) {
            }
            return null;
        }


    }

}
