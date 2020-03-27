package com.ennbou.tp4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ennbou.tp4.data.Contact;
import com.ennbou.tp4.data.ContactDbHelper;
import com.ennbou.tp4.data.ContactManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

public class SecondFragment extends Fragment {

    TextView firstName, lastName, phoneNumber, job, email;
    ContactManager cm;
    Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = this.getArguments();

        cm = ContactManager.get();

        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        job = view.findViewById(R.id.job);
        phoneNumber = view.findViewById(R.id.phone_number);
        email = view.findViewById(R.id.email);


        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        FloatingActionButton fabHome = getActivity().findViewById(R.id.fab_home);

        fabHome.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        if (args != null) {
            int id = args.getInt("id");
            Contact c = cm.select(id);
            firstName.setText(c.getFirstName());
            lastName.setText(c.getLastName());
            job.setText(c.getJob());
            phoneNumber.setText(c.getPhoneNumber());
            email.setText(c.getEmail());
        }


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_add, menu);
        if (args != null) {
            menu.findItem(R.id.action_add).setTitle("UPDATE");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add) {
            Contact c = new Contact(0, firstName.getText().toString(), lastName.getText().toString(), job.getText().toString(), phoneNumber.getText().toString(), email.getText().toString());
            if (args != null) {
                c.setId(args.getInt("id"));
                int r = cm.update(c);
                if (r > 0) {
                    Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                } else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
                }
            } else {
                long r = cm.insert(c);
                if (r > 0) {
                    Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        } else if (id == R.id.action_reset) {
            firstName.setText(null);
            lastName.setText(null);
            job.setText(null);
            phoneNumber.setText(null);
            email.setText(null);
        }

        return super.onOptionsItemSelected(item);
    }
}
