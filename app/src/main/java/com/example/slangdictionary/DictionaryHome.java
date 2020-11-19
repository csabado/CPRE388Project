package com.example.slangdictionary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    ArrayList<String> arrayI = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference mRef;
    Words word;
    String user;
    AdminAuth admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_home);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle != null){
            user = (String) bundle.get("user");
        }
        admin = new AdminAuth(user,false);
        admin.adminCheck();

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
                    arrayI.add(word.getImage());
                    
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
                intent.putExtra("img", arrayI.get(i));
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
            //Requested Words list
            case R.id.rWords:

                if(admin.isAdmin){
                    startActivity(new Intent(DictionaryHome.this, RequestedWords.class));
                    return true;
                }else{
                    startActivity(new Intent(DictionaryHome.this, UserRequest.class));
                    return true;
                }

            case R.id.logout:
                Toast.makeText(DictionaryHome.this, "Successfully logged out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DictionaryHome.this, LoginPage.class));
                return true;
            case R.id.request:
                Toast.makeText(DictionaryHome.this, "Let's Request", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DictionaryHome.this, RequestForm.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}