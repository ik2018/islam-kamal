package com.example.ss.contact_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Ezz on 8/12/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(Context context) {
        super(context,"Amit",null,4);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String  CREATE_CONTACTS_TABLE= "CREATE TABLE Contacts ( " +
       "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "Name TEXT, "+ "Phone TEXT, " +
                "Email TEXT, "    +   "Lng TEXT, "+
        "Lat TEXT )";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }
    public void addContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name",contact.getName());
        values.put("Phone", contact.getPhone());
        values.put("Email",contact.getEmail());
        values.put("Lng",String.valueOf(contact.getLng()) );
        values.put("Lat",String.valueOf(contact.getLat()));
        db.insert("Contacts", null, values);
        db.close();
    }
public Contact getcontactbyid(int ID)
{
    SQLiteDatabase database = this.getReadableDatabase();
    Cursor cursor = database.query("Contacts", new String[]{"Id","Name", "Phone", "Email"}, "Id=?", new String[]{String.valueOf(ID)}, null, null, null, null);
   if (cursor!=null)
       cursor.moveToFirst();

    Contact contact = new Contact(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
    return contact;
}
    public ArrayList<Contact> getallcontacts()
    {ArrayList<Contact> contactlist = new ArrayList<Contact>();
        String selectquery ="Select * from Contacts";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectquery, null);
        if (cursor.moveToFirst())
        {
            do {
               Contact contact = new Contact(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),Double.parseDouble(cursor.getString(4)),Double.parseDouble(cursor.getString(5)));
contactlist.add(contact);
            }while (cursor.moveToNext());

        }

        return contactlist;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS Contacts");

        // create fresh books table
        this.onCreate(db);
    }
    public int updateContanctbyID(Contact contact)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name",contact.getName());
        values.put("Lng",contact.getLng());
        values.put("Phone", contact.getPhone());
        values.put("Email", contact.getEmail());
        int updateItems = db.update("Contacts", values,"ID = ?", new String[] {String.valueOf(contact.getId())} );
        db.close();
        return updateItems;

    }
    public ArrayList<Contact> selectAllContactsByName(String name)
    {ArrayList<Contact> contacts = new ArrayList<>();
        String selectQuery = " select * from Contacts where Name like '"+name+"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                Contact contact = new Contact(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                contacts.add(contact);
            }while (cursor.moveToNext());

        }

        return contacts;

    }
    public void deleteContactByID(int snapshotID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Contacts", "ID = ?", new String[]{String.valueOf(snapshotID)});
        db.close();
    }
}
