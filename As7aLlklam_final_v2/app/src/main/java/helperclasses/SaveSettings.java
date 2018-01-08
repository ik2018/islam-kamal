package helperclasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.islamkamal.as7allklam.LoginActivity;

/**
 * Created by islam kamal on 12/1/2016.
 */
public class SaveSettings {
    Context context;
    SharedPreferences sharPref;
    public static String userID;
    public SaveSettings(Context context){
        this.context=context;
        sharPref=context.getSharedPreferences("myRef",Context.MODE_PRIVATE);
    }
    public void saveData(String userID){
        Log.d("test","A-1-1");
        SharedPreferences.Editor editor=sharPref.edit();
        editor.putString("userID",userID);
        editor.commit();
        Log.d("test", "A-1-2");
        loadData();
        Log.d("test", "A-1-3");

    }
    public void loadData(){
        Log.d("test","A-1-4");
        userID=sharPref.getString("userID","0");
        Log.d("test","A-1-5");
        if(userID.equals("0")){
            Log.d("test","A-1-6");

            Intent intent=new Intent(context,LoginActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("test", "A-1-7");
            context.startActivity(intent);
            Log.d("test", "A-1-8");

        }

    }
}
