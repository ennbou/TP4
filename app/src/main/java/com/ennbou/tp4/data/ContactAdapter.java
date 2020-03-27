package com.ennbou.tp4.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ennbou.tp4.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Contact> contacts;
    private List<Contact> contactsFilter;
    private ValueFilter valueFilter;


    public ContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        contactsFilter = contacts;
    }

    @Override
    public int getCount() {
        return contactsFilter.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        }

        Contact contact = (Contact) getItem(position);

        final TextView firstName, lastName, job, phoneNumber, email;
        firstName = convertView.findViewById(R.id.txt_f_name);
        lastName = convertView.findViewById(R.id.txt_l_name);
        job = convertView.findViewById(R.id.txt_job);
        phoneNumber = convertView.findViewById(R.id.txt_n_phone);
        email = convertView.findViewById(R.id.txt_email);

        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
        job.setText(contact.getJob());
        phoneNumber.setText(contact.getPhoneNumber());
        email.setText(contact.getEmail());

        final String n = contact.getPhoneNumber();

        convertView.findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + n));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Contact> filterList = new ArrayList<Contact>();
                Contact c;
                String key = constraint.toString().toUpperCase();
                for (int i = 0; i < contacts.size(); i++) {
                    c = contacts.get(i);
                    key = key.trim();
                    String[] keys = key.split(" ");
                    String first = c.getFirstName().toUpperCase();
                    String last = c.getLastName().toUpperCase();
                    if (keys.length > 1) {
                        if ((first.contains(keys[0]) && last.contains(keys[1])) || (first.contains(keys[1]) && last.contains(keys[0]))) {
                            filterList.add(new Contact(c.getId(), c.getFirstName(), c.getLastName(), c.getJob(), c.getPhoneNumber(), c.getEmail()));
                        }
                    } else {
                        if ((first).contains(key) || (last).contains(key)) {
                            filterList.add(new Contact(c.getId(), c.getFirstName(), c.getLastName(), c.getJob(), c.getPhoneNumber(), c.getEmail()));
                        }
                    }

                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = contacts.size();
                results.values = contacts;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactsFilter = (List<Contact>) results.values;
            notifyDataSetChanged();
        }
    }
}


