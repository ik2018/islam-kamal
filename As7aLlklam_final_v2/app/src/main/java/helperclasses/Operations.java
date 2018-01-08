package helperclasses;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by islam kamal on 12/1/2016.
 */
public class Operations {

    Context context;
    public Operations(Context context){
        this.context=context;
    }
    public static String convertInputToString(InputStream inputStream){
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String lineConact="";
        try {
            while( (line=reader.readLine())!=null){
                lineConact+=line;
            }
            inputStream.close();
        }catch (Exception e){

        }
        return lineConact;
    }
 }
