
package com.example.ss.contact_project;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ObjectInputValidation;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener,View.OnClickListener,AdapterView.OnItemClickListener{
    Button add ;
    String Searchkey ;
EditText contactsearch ;
ListView contactview ;
    int positionlong;
    ArrayList<Contact> allcontact = new ArrayList<Contact>();
    DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsearch = (EditText)findViewById(R.id.SearchPar_text);
        contactview = (ListView)findViewById(R.id.ContactsView_listview);

        add = (Button)findViewById(R.id.Add_btn);
        contactsearch.setText("");
        contactsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                allcontact = dataBaseHandler.selectAllContactsByName(s.toString());
                listview();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        animator();



listview();

    }

    @Override
    public void onClick(View v) {
if (v==add)
{
Intent gotoadd = new Intent(this,Add_Activity.class);
    startActivity(gotoadd);


}

    }
    public void listview ()
    {
        if(contactsearch.getText().toString().equals(""))
        {  allcontact =   dataBaseHandler.getallcontacts();

        }


        ArrayList<String > names = new ArrayList<>();



        for (Contact h : allcontact)
        {
          names.add(h.getName());
        }
        add.setOnClickListener(this);

        ArrayAdapter<String > listArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        contactview.setAdapter(listArrayAdapter);

contactview.setOnItemClickListener(this);
        contactview.setOnItemLongClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
  Intent GotoNumber = new Intent(this,NumberOnClick_Activity.class);


        GotoNumber.putExtra("id",allcontact.get(position).getId());

        startActivity(GotoNumber);
    }





    public void animator()
    {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(contactview, "backgroundColor", /*Red*/0xFFFF8080, /*Blue*/0xFF8080FF);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, 1, 0, "Call");//groupId, itemId, order, title
        menu.add(0, 2, 0, "Delete");
        menu.add(0, 3, 0, "Edit");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==1){
            Intent phoneintent = new Intent(Intent.ACTION_CALL);
            phoneintent.setData(Uri.parse("tel:"+allcontact.get(positionlong).getPhone()));
            startActivity(phoneintent);
        }
        else if(item.getItemId()==2){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure That you want to Delete This Contact");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dataBaseHandler.deleteContactByID(allcontact.get(positionlong).getId());
                    contactsearch.setText("");
                    listview();
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
        else if (item.getItemId()==3)
        {
            Intent gotoedit = new Intent(this,Edit_Activity.class);
           gotoedit.putExtra("id",allcontact.get(positionlong).getId());
            startActivity(gotoedit);
        }
        else{
            return false;
        }
        return true;
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        positionlong = position;
        registerForContextMenu(contactview);

        return false;
    }
}
