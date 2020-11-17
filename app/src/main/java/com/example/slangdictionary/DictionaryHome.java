package com.example.slangdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class DictionaryHome extends AppCompatActivity {
    DatabaseReference db;
    ListView listview;
    ArrayList<String> arraylist = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_home);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infalter = getMenuInflater();
        infalter.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.rWords:
                Toast.makeText(DictionaryHome.this, "It works", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                Toast.makeText(DictionaryHome.this, "Successfully logged out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DictionaryHome.this, LoginPage.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}