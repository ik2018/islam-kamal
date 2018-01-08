package com.example.ss.contact_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class NumberOnClick_Activity extends ActionBarActivity implements View.OnClickListener{
    Intent comedintent;
    TextView name_text,phone_text,email_text;
    Button call_btn , email_btn , edit_btn , delete_btn ,msg_btn , close,showmap;
DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
Contact contact ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_on_click_);
        name_text = (TextView)findViewById(R.id.Name_TextView);
        phone_text = (TextView)findViewById(R.id.Phone_textview);
        email_text = (TextView)findViewById(R.id.Email_textview);
        call_btn = (Button)findViewById(R.id.Phone_btn);
        email_btn = (Button)findViewById(R.id.Email_btn);
        edit_btn = (Button)findViewById(R.id.Edit_btn);
        delete_btn = (Button)findViewById(R.id.Delete_btn);
        msg_btn = (Button)findViewById(R.id.Massege_btn);
        close = (Button)findViewById(R.id.Close_btn);
        showmap = (Button)findViewById(R.id.ShowMap_btn);








        call_btn.setOnClickListener(this);
        email_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        msg_btn.setOnClickListener(this);
        close.setOnClickListener(this);
        showmap.setOnClickListener(this);

        comedintent = getIntent();

contact = dataBaseHandler.getcontactbyid(comedintent.getIntExtra("id",0));



        name_text.setText(contact.getName());
        phone_text.setText(contact.getPhone());
        email_text.setText(contact.getEmail());

    }

    @Override
    public void onClick(View v) {
if (v==delete_btn)
{
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Are you sure That you want to Delete This Contact");
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dataBaseHandler.deleteContactByID(contact.getId());
            Intent gotomain = new Intent(((Dialog)dialog).getContext(),MainActivity.class);
            startActivity(gotomain);
        }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
    AlertDialog alertDialog = builder.create();
    alertDialog.show();



}
else if (v==showmap)
{
   Intent gotomap = new Intent(this,Showonmap.class);
    gotomap.putExtra("position", comedintent.getIntExtra("position",0));
startActivity(gotomap);

}
        else if (v==call_btn)
{
    Intent phoneintent = new Intent(Intent.ACTION_CALL);
    phoneintent.setData(Uri.parse("tel:"+contact.getPhone()));
    startActivity(phoneintent);
}

else if (v==msg_btn)
{
    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
    sendIntent.putExtra("sms_body","");
    sendIntent.setType("vnd.android-dir/mms-sms");
    sendIntent.putExtra("address",contact.getPhone());
    startActivity(sendIntent);
}
        else if (v==email_btn)
{
    Intent sendemail = new Intent(Intent.ACTION_SEND);
    sendemail.setData(Uri.parse("mailto:"));
    sendemail.setType("text/plain");

    sendemail.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getEmail()});

    startActivity(Intent.createChooser(sendemail,"Please choose The Messanger "));
}
        else if (v==edit_btn)
{
Intent gotoedit = new Intent(this,Edit_Activity.class);
   gotoedit.putExtra("id",comedintent.getIntExtra("id",0));
    startActivity(gotoedit);
}
else if (v==close)     {

    Intent gotomain = new Intent(this,MainActivity.class);
    startActivity(gotomain);
}
    }
}
