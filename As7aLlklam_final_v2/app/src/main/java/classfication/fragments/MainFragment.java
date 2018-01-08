package classfication.fragments;


import android.annotation.TargetApi;
import android.app.Fragment;
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
import android.support.annotation.Nullable;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import com.example.islamkamal.as7allklam.R;
import com.example.islamkamal.as7allklam.User_Profile_Activity;
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

import helperclasses.AdaperItems;
import helperclasses.Operations;
import helperclasses.SaveSettings;
import helperclasses.SearchType;


/**
 * Created by islam kamal on 2/23/2017.
 */
public class MainFragment extends Fragment  implements View.OnClickListener{
Context context;
    ImageView tweet_imageUp;
    ImageView profile_picture;
    String searchQuery;
    ListView listView;
    MyCustomAdapter adapter;
    ArrayList<AdaperItems> listNewsData=new ArrayList<>();
    int startFrom=0;
    int userOperation= SearchType.myfollowing;
    int totalItemsVisible=0;
    LinearLayout channelInfo;
    Button buFollow;
    TextView textnameFollowers;
    int selectUserId=0;
    String name = "";
    String downloadUrl;
    ProgressDialog progressDialog;
    final static int REQUEST_CODE_PREMISSION = 123;
    final static int RESULT_LOAD_IMAGE=323;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView=inflater.inflate(R.layout.main_fragment,container,false);
        context=container.getContext();
//home page

        channelInfo=(LinearLayout)rootView.findViewById(R.id.channelInfo);
        channelInfo.setVisibility(View.GONE);
        textnameFollowers=(TextView)rootView.findViewById(R.id.namePersonTxt);
        buFollow=(Button)rootView.findViewById(R.id.followbutton);
        buFollow.setOnClickListener(this);
        listView=(ListView)rootView.findViewById(R.id.listView);


        SaveSettings saveSettings=new SaveSettings(getActivity());
        saveSettings.loadData();
        adapter=new MyCustomAdapter(context,listNewsData);
        ListView lv=(ListView)rootView.findViewById(R.id.listView);
        lv.setAdapter(adapter);
        loadTweets(0, SearchType.myfollowing);



        return rootView;
    }
    @Override
    public void onClick(View v)
    {
        if(v==buFollow){
            int opertion;
            String follow=buFollow.getText().toString();
            if(follow.equalsIgnoreCase("Follow")){
                opertion=1;
                buFollow.setText("Un Follow");
            }
            else {
                opertion=2;
                buFollow.setText("Follow");
            }
            //there is another data to put
            String url="http://islamkamal.890m.com/twitter/userFollowing.php?user_id="+ SaveSettings.userID+"&follwing_user_id="+selectUserId+"&op="+opertion;
            Log.d("URLS - userFollowing : ", url);

            new MyAsyncTack().execute(url);

        }


    }

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.searchIcon);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchQuery=null;
                try {
                    searchQuery=java.net.URLEncoder.encode(query,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                loadTweets(0,SearchType.searchIn);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

         super.onCreateOptionsMenu(menu, inflater);
    }



    private class MyCustomAdapter extends BaseAdapter {
        Context context;
        ArrayList<AdaperItems>listNewsAdapter;
        public MyCustomAdapter(Context context,ArrayList<AdaperItems> listNewsAdapter){
            this.context=context;
            this.listNewsAdapter=listNewsAdapter;
        }

        @Override
        public int getCount() {
            return listNewsAdapter.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final AdaperItems s=listNewsAdapter.get(position);
            if(s.tweet_date.equals("add")){

                LayoutInflater inflater=getActivity().getLayoutInflater();
                View myView=inflater.inflate(R.layout.addtweet, null);
                final EditText postText=(EditText)myView.findViewById(R.id.editText);
                tweet_imageUp=(ImageView)myView.findViewById(R.id.iv_attach);
                tweet_imageUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //load image
                        loadImage();
                    }
                });
                ImageView iv_post=(ImageView)myView.findViewById(R.id.iv_post);
                iv_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String tweets=null;

                        try {
                            tweets=java.net.URLEncoder.encode(postText.getText().toString(),"UTF-8");
                            downloadUrl=java.net.URLEncoder.encode(downloadUrl,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            tweets=".";
                        }

                        // String url="http://127.0.0.1//twitter/tweetAdd.php?user_id="+SaveSettings.userID+"&tweet_text="+tweets+"&tweet_picture="+downloadUrl;
                        String url="http://islamkamal.890m.com/twitter/tweetAdd.php?user_id="+SaveSettings.userID+"&tweet_text="+tweets+"&tweet_picture="+downloadUrl;
                        Log.d("URLS - tweetAdd : ",url);

                        new MyAsyncTack().execute(url);
                        postText.setText("");

                    }
                });

                return myView;
            }
            else if(s.tweet_date.equals("loading")){
                LayoutInflater inflater=getActivity().getLayoutInflater();
                View myView=inflater.inflate(R.layout.tweetloading,null);
                return myView;
            }
            else if(s.tweet_date.equals("notweet")){
                LayoutInflater inflater=getActivity().getLayoutInflater();
                View myView=inflater.inflate(R.layout.tweet_msg,null);
                return myView;
            }
            else {
                LayoutInflater inflater=getActivity().getLayoutInflater();
                View myView=inflater.inflate(R.layout.tweet_item, null);
                TextView userName=(TextView)myView.findViewById(R.id.nametextView3);
                userName.setText(s.name);
                userName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectUserId = Integer.parseInt(s.user_id);
                        loadTweets(0, SearchType.onePerson);
                        textnameFollowers.setText(s.name);

                        String url = "http://127.0.0.1//twitter/isFollowing.php?user_id=" + SaveSettings.userID + "&follwing_user_id=" + selectUserId;
                        Log.d("URLS - isFollowing : ", url);

                        new MyAsyncTack().execute(url);

                    }
                });
                TextView tweet_text=(TextView)myView.findViewById(R.id.descriptiontextView);
                tweet_text.setText(s.tweet_text);
                TextView tweet_date=(TextView)myView.findViewById(R.id.datetextView2);
                tweet_date.setText(s.tweet_date);

                ImageView tweet_image=(ImageView)myView.findViewById(R.id.tweet_image);
                Picasso.with(context).load(s.tweet_picture).into(tweet_image);
                profile_picture=(ImageView)myView.findViewById(R.id.person_image);
                Picasso.with(context).load(s.picure_path).into(profile_picture);

                profile_picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(context,User_Profile_Activity.class);
                        Bundle bun=new Bundle();
                        bun.putInt("user_id", Integer.parseInt(s.user_id));
                        intent.putExtras(bun);
                        startActivity(intent);

                    }
                });

                return myView;


            }
        }

    }



    //load image
    public void showProgress(){
        if(progressDialog==null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("loading data....");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.show();
    }

    public void hideProgress(){
        //if(progressDialog !=null && progressDialog.isShowing()){
        progressDialog.dismiss();
        // }
    }
    public void checkPermissions() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PREMISSION);
                return;
            }
        }
        loadImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_PREMISSION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadImage();
                }
                else {
                    Toast.makeText(getActivity(), "message", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    public void loadImage(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RESULT_LOAD_IMAGE&&resultCode==getActivity().RESULT_OK&&null !=data){
            Uri selectedImage=data.getData();
            String[]filePath={MediaStore.Images.Media.DATA};
            Cursor cursor=getActivity().getContentResolver().query(selectedImage, filePath, null, null, null, null);
            cursor.moveToFirst();
            int columnIndex=cursor.getColumnIndex(filePath[0]);
            String imagePath=cursor.getString(columnIndex);
            cursor.close();
            uploadImage(BitmapFactory.decodeFile(imagePath));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadImage(Bitmap bitmap){
        showProgress();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://as7a-llklam.appspot.com");
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
        Date dateObj = new Date();
        final String myDownloadUrl =SaveSettings.userID+"_"+ df.format(dateObj) + ".jpg";
        //String myDownloadUrl =df.format(dateObj)+"("+SaveSettings.userID+")" + ".jpeg";
        StorageReference mountianRef = storageRef.child("images/" + myDownloadUrl);
        tweet_imageUp.setDrawingCacheEnabled(true);
        tweet_imageUp.buildDrawingCache();
        BitmapDrawable drawable = (BitmapDrawable) tweet_imageUp.getDrawable();
        Bitmap bitmap1 = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountianRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl = taskSnapshot.getDownloadUrl().toString();
                hideProgress();

            }
        });



    }




    //upload data to server
    public class MyAsyncTack extends AsyncTask<String,String,String> {

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

                if (json.getString("msg").equalsIgnoreCase("tweet is added")) {
                    loadTweets(0, userOperation);
                } else if (json.getString("msg").equalsIgnoreCase("has tweet")) {
                    if (startFrom == 0) {
                        listNewsData.clear();
                        listNewsData.add(new AdaperItems(null, null, null, null, null, null, "add"));

                    } else {
                        listNewsData.remove(listNewsData.size() - 1);
                    }
                    JSONArray tweets = new JSONArray(json.getString("info"));
                    for (int i = 0; i < tweets.length(); i++) {
                        JSONObject oneTweet = tweets.getJSONObject(i);
                        listNewsData.add(new AdaperItems(oneTweet.getString("user_id"), oneTweet.getString("name"),
                                oneTweet.getString("picture_path"), oneTweet.getString("tweet_id"), oneTweet.getString("tweet_text"),
                                oneTweet.getString("tweet_picture"), oneTweet.getString("tweet_date")));

                    }
                    adapter.notifyDataSetChanged();
                } else if (json.getString("msg").equalsIgnoreCase("no tweet")) {
                    if (startFrom == 0) {
                        listNewsData.clear();
                        listNewsData.add(new AdaperItems(null, null, null, null, null, null, "add"));
                    }else {
                       listNewsData.remove(listNewsData.size() - 1);
                    }
                    listNewsData.add(new AdaperItems(null,null,null,null,null,null,"notweet"));
                }
                else if(json.getString("msg").equalsIgnoreCase("is subscriber")){
                    buFollow.setText("Un Follow");

                }
                else if(json.getString("msg").equalsIgnoreCase("is not subscriber")){
                    buFollow.setText("Follow");

                }
            } catch (JSONException e) {
                listNewsData.clear();
                listNewsData.add(new AdaperItems(null, null, null, null, null, null, "add"));
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
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
                    Operations op = new Operations(getActivity());
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

    public void loadTweets(int startFrom , int userOperation){
        this.startFrom=startFrom;
        this.userOperation=userOperation;

        if(startFrom==0){
            listNewsData.add(0, new AdaperItems(null, null, null, null, null, null, "loading"));
        }
        else {
            listNewsData.add(new AdaperItems(null, null, null, null, null, null, "loading"));
        }
        adapter.notifyDataSetChanged();

        String url="http://islamkamal.890m.com/twitter/getUser.php?user_id="+SaveSettings.userID+"&startFrom="+startFrom+"&op="+userOperation;
        Log.d("URLS operation : ",url);

        Toast.makeText(context,SaveSettings.userID,Toast.LENGTH_SHORT).show();
        Log.d("userID",SaveSettings.userID);
        if(userOperation==SearchType.searchIn) {
            url = "http://islamkamal.890m.com/twitter/getUser.php?user_id=" + SaveSettings.userID + "&startFrom=" + startFrom + "&op=" + userOperation + "&query=" + searchQuery;
            Log.d("URLS operation(3) : ",url);

        }
        if(userOperation==SearchType.onePerson){

            url="http://islamkamal.890m.com/twitter/getUser.php?user_id="+selectUserId+"&startFrom="+startFrom+"&op="+userOperation;
            Log.d("URLS operation(2) : ",url);

        }
        Log.d("loadTweets 11", url);

        new MyAsyncTack().execute(url);
        if(userOperation==SearchType.onePerson){
            channelInfo.setVisibility(View.VISIBLE);
        }
        else {
            channelInfo.setVisibility(View.GONE);
        }

    }

}
