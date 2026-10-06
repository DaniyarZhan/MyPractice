package com.example.mypractice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> users = new ArrayList<>();
    ArrayList<String> selectionUsers = new ArrayList<>();
    ArrayList<String> searchUsers = new ArrayList<>();

    ArrayAdapter<String> adapter;
    ListView usersList;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Collections.addAll(users, "Дорогин", "Жетибаев", "Игнатьев", "Крупнов", "Свиридов", "Урашева");
        searchUsers.addAll(users);
        usersList = findViewById(R.id.usersList);
        searchText = findViewById(R.id.searchText);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, searchUsers);
        usersList.setAdapter(adapter);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user = adapter.getItem(position);
                if (usersList.isItemChecked(position))
                    selectionUsers.add(user);
                else
                    selectionUsers.remove(user);
            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
        });
    }

    private void filter(String query) {
        searchUsers.clear();

        if (query == null || query.trim().isEmpty()) {
            searchUsers.addAll(users);
        }
        else {
            String lower = query.toLowerCase(Locale.getDefault()).trim();
            for (String u : users) {
                if (u.toLowerCase(Locale.getDefault()).contains(lower)) {
                    searchUsers.add(u);
                }
            }
        }

        usersList.clearChoices();
        selectionUsers.clear();
        adapter.notifyDataSetChanged();
    }

    public void add(View view) {
        EditText userName = findViewById(R.id.userName);
        String user = userName.getText().toString().trim();
        if (!user.isEmpty()) {
            users.add(user);
            searchUsers.add(user);
            userName.setText("");
            adapter.notifyDataSetChanged();
        }
    }

    public void remove(View view) {
        for (int i = 0; i < selectionUsers.size(); i++) {
            users.remove(selectionUsers.get(i));
            searchUsers.remove(selectionUsers.get(i));
        }

        usersList.clearChoices();
        selectionUsers.clear();
        adapter.notifyDataSetChanged();
    }
}