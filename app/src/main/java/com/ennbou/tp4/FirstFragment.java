package com.ennbou.tp4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ennbou.tp4.data.Contact;
import com.ennbou.tp4.data.ContactAdapter;
import com.ennbou.tp4.data.ContactManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private ContactManager cm;
    private List<Contact> contacts = new ArrayList<>();
    private ListView list;
    private AlertDialog.Builder builder;
    public static ContactAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        FloatingActionButton fabHome = getActivity().findViewById(R.id.fab_home);
        builder = new AlertDialog.Builder(getContext());

        fabHome.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        list = view.findViewById(R.id.list_contacts);

        cm = ContactManager.get();
        contacts = cm.select();
        adapter = new ContactAdapter(getContext(), contacts);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putInt("id", contacts.get(position).getId());
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, args);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getContext(), "on Long Item click List", Toast.LENGTH_LONG).show();
                Contact c = contacts.get(position);
                final int i = c.getId();
                final View v = view;

                builder.setTitle("Delete Contact");
                builder.setMessage("Do you want to delete this Contact " + c.getFirstName() + " " + c.getLastName() + " , " + c.getPhoneNumber());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (cm.delete(i) > 0) {
//                            v.setVisibility(View.INVISIBLE);
                            contacts.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "done", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

    }
}
