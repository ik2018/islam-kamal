package com.example.ss.contact_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Add_Activity extends ActionBarActivity implements View.OnClickListener{
Intent camedintent;
DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    EditText name_text , phone_text , email_text ;
    Button save , cancel ,location;
    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);

        name_text = (EditText)findViewById(R.id.NameADD_text);
        phone_text = (EditText)findViewById(R.id.PhoneADD_text);
        email_text = (EditText)findViewById(R.id.EmailADD_text);


        location = (Button)findViewById(R.id.Location_btn);
        save = (Button)findViewById(R.id.Save_ADD_btn);
        cancel= (Button)findViewById(R.id.Cancel_ADD_Btn);


        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        location.setOnClickListener(this);

camedintent = getIntent();

        name_text.setText(camedintent.getStringExtra("Name"));
        phone_text.setText(camedintent.getStringExtra("Phone"));
        email_text.setText(camedintent.getStringExtra("Email"));





        }



    @Override
    public void onClick(View v) {

        if (v==save)
        {


            contact = new Contact(name_text.getText().toString(), phone_text.getText().toString(), email_text.getText().toString(),camedintent.getDoubleExtra("lng",0.0),camedintent.getDoubleExtra("lat",0.0));


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure That you Want to save That Contact");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {




                    dataBaseHandler.addContact(contact);
                    Intent gotomain =  new Intent(((Dialog) dialog).getContext(), MainActivity.class);
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
        else if (v==location)
        {

            Intent gotomap = new Intent(this,MapContact.class);
            gotomap.putExtra("Name",name_text.getText().toString());
            gotomap.putExtra("Phone",phone_text.getText().toString());
            gotomap.putExtra("Email",email_text.getText().toString());

            startActivity(gotomap);

        }

        else if (v==cancel)
        {
            Intent gotomain = new Intent(this,MainActivity.class);
            startActivity(gotomain);
        }
    }
}
