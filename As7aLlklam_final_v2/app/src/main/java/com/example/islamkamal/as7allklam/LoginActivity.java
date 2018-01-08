package com.example.islamkamal.as7allklam;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
import java.util.Date;

import helperclasses.Operations;
import helperclasses.SaveSettings;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText nameEd;
    EditText emailEd;
    EditText passwordEd;
    ImageView imageView;
    Button loginBtn;
    final static int REQUEST_CODE_PREMISSION = 123;
    final static int RESULT_LOAD_IMAGE=323;
    FirebaseAuth firebaseAuth;
    public final static String TAG="AnoymousAuth";
    private FirebaseAuth.AuthStateListener authStateListener;
    ProgressDialog progressDialog;
    String name = "";
    String downloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        nameEd = (EditText) findViewById(R.id.nameeditText);
        emailEd = (EditText) findViewById(R.id.emaileditText2);
        passwordEd = (EditText) findViewById(R.id.passwordeditText3);
        imageView = (ImageView) findViewById(R.id.imageView);
        loginBtn = (Button) findViewById(R.id.submitbutton);
        loginBtn.setOnClickListener(this);
        imageView.setOnClickListener(this);


        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user !=null){
                    //  Log.d(TAG, "user is sign in : " + user.getUid());
                }
                else{
                    //   Log.d(TAG,"user is logging out :"+user.getUid());
                }
            }
        };
    }


    @Override
    public void onClick(View v) {
        if (v == imageView) {

            checkPermissions();
        }
        if (v == loginBtn) {
            loginFunc();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        signInAnoymous();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener !=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        hideProgress();
    }
    private void signInAnoymous(){
        firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                Log.d(TAG, "signInAnonymously :" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d(TAG, "signInAnonymously :" + task.getException());
                }
            }

        });
    }
    public void showProgress(){
        if(progressDialog==null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("loading data....");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.show();
    }

    public void hideProgress(){
        if(progressDialog !=null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void checkPermissions() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getBaseContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PREMISSION);
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
                    Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RESULT_LOAD_IMAGE&&resultCode==RESULT_OK&&null !=data){
            Uri selectedImage=data.getData();
            String[]filePath={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(selectedImage, filePath, null, null, null, null);
            cursor.moveToFirst();
            int columnIndex=cursor.getColumnIndex(filePath[0]);
            String imagePath=cursor.getString(columnIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void loginFunc(){
        Log.d("test","1");
        showProgress();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://as7a-llklam.appspot.com");
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
        Log.d("test","2");
        Date dateObj = new Date();
        final String imagePath = df.format(dateObj) + ".jpg";
        StorageReference mountianRef = storageRef.child("images/" + imagePath);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Log.d("test", "3");
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Log.d("test", "3-1");
        Bitmap bitmap = drawable.getBitmap();
        Log.d("test", "3-2");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Log.d("test", "3-3");
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 120, true);
        Log.d("test", "3-4");
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Log.d("test", "4");
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

                try {
                    Log.d("test","5");
                    name = java.net.URLEncoder.encode(nameEd.getText().toString(), "UTF-8");
                    downloadUrl = java.net.URLEncoder.encode(downloadUrl, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("test","6");
                //login & register
                String url="http://islamkamal.890m.com/twitter/register.php?name="+name+"&email="+emailEd.getText().toString()+"&password="+passwordEd.getText().toString()+"&picture_path="+downloadUrl;
                MyAsyncTack task=new MyAsyncTack();
                task.execute(url);
                hideProgress();
                Log.d("test", "7");

            }
        });



    }


    public class MyAsyncTack extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(String... values) {
            JSONObject json ;
            try {
                Log.d("test", "64-1");
                json = new JSONObject(values[0]);
                Log.d("test", "64-2");
                if (json.getString("msg") == null) {
                    return;
                }
                if (json.getString("msg").equalsIgnoreCase("user is added")) {
                    Toast.makeText(getBaseContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                    Log.d("test", "64-3");
                    String url = "http://islamkamal.890m.com/twitter/login.php?email=" + emailEd.getText().toString() + "&password=" + passwordEd.getText().toString();
                    new MyAsyncTack().execute(url);
                    Log.d("test", "64-4");
                }
                if (json.getString("msg").equalsIgnoreCase("pass Login")) {
                    Log.d("test", "64-5");
                    JSONArray userInfo = new JSONArray(json.getString("info"));
                    JSONObject userCr = userInfo.getJSONObject(0);
                    Log.d("test", "64-6");
                    hideProgress();
                    Log.d("test", "64-7");
                    Log.d("test","A-1");
                    SaveSettings saveSettings = new SaveSettings(getApplicationContext());
                    saveSettings.saveData(userCr.getString("user_id"));
                    Log.d("test", "A-1-9");

                    Log.d("test", "64-8");
                    finish();
                    Log.d("test", "64-9");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("test", "61");

            try {
                String newData;
                Log.d("test", "62");
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.d("test", "64");
                urlConnection.setConnectTimeout(30000);
                Log.d("test", "65");
                //get response data
                try {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    Log.d("test", "66");
                    //convert stream to string
                    Operations op = new Operations(getApplicationContext());
                    Log.d("test", "67");
                    newData = op.convertInputToString(inputStream);
                    // send o display data
                    Log.d("test", "68");
                    publishProgress(newData);
                    Log.d("test", "69");
                } finally {
                    Log.d("test", "69-1");
                    urlConnection.disconnect();
                    Log.d("test", "69-2");
                }

            } catch (Exception e) {
            }
            return null;
        }
    }

}
