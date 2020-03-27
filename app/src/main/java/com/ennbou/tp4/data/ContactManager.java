package com.ennbou.tp4.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactManager implements CRUD {
    private ContactDbHelper dbHelper;
    private SQLiteDatabase dbW;
    private SQLiteDatabase dbR;
    private static ContactManager cm;

    private ContactManager(ContactDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public static void init(ContactDbHelper dbHelper) {
        if (cm == null) {
            cm = new ContactManager(dbHelper);
            cm.dbW = dbHelper.getWritableDatabase();
            cm.dbR = dbHelper.getReadableDatabase();
        }
    }

    public static ContactManager get() {
        return cm;
    }

    public static ContactManager get(ContactDbHelper dbHelper) {
        if (cm == null) {
            init(dbHelper);
        }
        return cm;
    }

    @Override
    public long insert(Contact c) {
        ContentValues values = new ContentValues();
        values.put(ContactStructure.COLUMN_FIRST_NAME, c.getFirstName());
        values.put(ContactStructure.COLUMN_LAST_NAME, c.getLastName());
        values.put(ContactStructure.COLUMN_JOB, c.getJob());
        values.put(ContactStructure.COLUMN_PHONE_NUMBER, c.getPhoneNumber());
        values.put(ContactStructure.COLUMN_EMAIL, c.getEmail());

        return dbW.insert(ContactStructure.TABLE_NAME, null, values);
    }

    @Override
    public int delete(int id) {
        String selection = ContactStructure._ID + " = ?";
        String[] selectionArgs = {id + ""};
        return dbW.delete(ContactStructure.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Contact c) {

        ContentValues values = new ContentValues();
        values.put(ContactStructure.COLUMN_FIRST_NAME, c.getFirstName());
        values.put(ContactStructure.COLUMN_LAST_NAME, c.getLastName());
        values.put(ContactStructure.COLUMN_JOB, c.getJob());
        values.put(ContactStructure.COLUMN_PHONE_NUMBER, c.getPhoneNumber());
        values.put(ContactStructure.COLUMN_EMAIL, c.getEmail());

        return dbW.update(ContactStructure.TABLE_NAME, values, ContactStructure._ID + "=?", new String[]{c.getId() + ""});
    }

    @Override
    public List<Contact> select() {
        String[] projection = {
                ContactStructure._ID,
                ContactStructure.COLUMN_FIRST_NAME,
                ContactStructure.COLUMN_LAST_NAME,
                ContactStructure.COLUMN_JOB,
                ContactStructure.COLUMN_PHONE_NUMBER,
                ContactStructure.COLUMN_EMAIL
        };

        Cursor cursor = dbR.query(ContactStructure.TABLE_NAME, projection, null, null, null, null, null);

        List<Contact> contacts = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ContactStructure._ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_LAST_NAME));
            String job = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_JOB));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_PHONE_NUMBER));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_EMAIL));

            contacts.add(new Contact(id, firstName, lastName, job, phoneNumber, email));
        }

        return contacts;
    }

    @Override
    public Contact select(long id) {

        String[] projection = {
                ContactStructure._ID,
                ContactStructure.COLUMN_FIRST_NAME,
                ContactStructure.COLUMN_LAST_NAME,
                ContactStructure.COLUMN_JOB,
                ContactStructure.COLUMN_PHONE_NUMBER,
                ContactStructure.COLUMN_EMAIL
        };

        Cursor cursor = dbR.query(ContactStructure.TABLE_NAME, projection, ContactStructure._ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        Contact c = null;
        if (cursor.moveToNext()) {
            int id1 = cursor.getInt(cursor.getColumnIndexOrThrow(ContactStructure._ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_LAST_NAME));
            String job = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_JOB));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_PHONE_NUMBER));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(ContactStructure.COLUMN_EMAIL));
            c = new Contact(id1, firstName, lastName, job, phoneNumber, email);
        }


        return c;

    }
}


interface CRUD {
    long insert(Contact c);

    int delete(int id);

    int update(Contact c);

    List<Contact> select();

    Contact select(long id);
}