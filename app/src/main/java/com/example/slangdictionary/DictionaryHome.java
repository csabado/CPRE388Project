package com.example.slangdictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DictionaryHome extends AppCompatActivity {
    ListView listview;
    ArrayList<String> arraylist = new ArrayList<>();
    ArrayList<String> arrayDef = new ArrayList<>();
    ArrayList<String> arrayEx = new ArrayList<>();
    ArrayList<String> arrayS = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference mRef;
    Words word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_home);

        word = new Words();
        arrayAdapter = new ArrayAdapter<String>(DictionaryHome.this, android.R.layout.simple_list_item_1, arraylist);
        listview = (ListView) findViewById(R.id.lv_Words);
        listview.setAdapter(arrayAdapter);
        mRef = FirebaseDatabase.getInstance().getReference("Words");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    word = ds.getValue(Words.class);
                    arraylist.add(word.getWord());
                    arrayDef.add(word.getDefinition());
                    arrayEx.add(word.getExample());
                    arrayS.add(word.getAudio());
                    
                }
                listview.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), wordDetails.class);
                intent.putExtra("word",arraylist.get(i));
                intent.putExtra("def",arrayDef.get(i));
                intent.putExtra("ex",arrayEx.get(i));
                intent.putExtra("url", arrayS.get(i));
                startActivity(intent);

            }
        });

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