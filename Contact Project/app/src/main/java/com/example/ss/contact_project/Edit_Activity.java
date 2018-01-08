package com.example.ss.contact_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Edit_Activity extends Activity implements View.OnClickListener{
    EditText name_text , phone_text , email_text ;
    Button save , cancel,clear ;
    Intent camedintent;
    DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
   Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);

         camedintent = getIntent();

        name_text = (EditText)findViewById(R.id.NameEdit_text);
        phone_text = (EditText)findViewById(R.id.PhoneEdite_text);
        email_text = (EditText)findViewById(R.id.EmailEdite_text);

        save = (Button)findViewById(R.id.Save_btn);
        cancel= (Button)findViewById(R.id.Cancel_btn);
        clear= (Button)findViewById(R.id.Clear_btn);

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        clear.setOnClickListener(this);

     contact = dataBaseHandler.getcontactbyid(camedintent.getIntExtra("id",0));

        name_text.setText(   contact.getName());
        phone_text.setText(   contact.getPhone());
        email_text.setText(   contact.getEmail());




    }


    @Override
    public void onClick(View v) {
if (v == save)
{

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Are you sure That you Want to save That Contact");
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {


            Contact contact = new Contact(   camedintent.getIntExtra("id",0), name_text.getText().toString(), phone_text.getText().toString(), email_text.getText().toString());
            dataBaseHandler.updateContanctbyID(contact);

            Intent GotoNumber = new Intent(getBaseContext(), NumberOnClick_Activity.class);
         GotoNumber.putExtra("id", camedintent.getIntExtra("id",0));
            startActivity(GotoNumber);
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
else if (v==cancel)
{
    Intent GotoNumber = new Intent(this , NumberOnClick_Activity.class);
    GotoNumber.putExtra("id", camedintent.getIntExtra("id",0));

    startActivity(GotoNumber);
}
        else if (v==clear)
{
    name_text.setText("");
    phone_text.setText("");
    email_text.setText("");




}

    }
}
