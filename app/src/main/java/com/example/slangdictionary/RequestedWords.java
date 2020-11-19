package com.example.slangdictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class RequestedWords extends AppCompatActivity {
    ListView listview;
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<String> reqDef = new ArrayList<>();
    ArrayList<String> arrUser = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference mRef;
    DatabaseReference mDatabase;
    Words word;
    Request request;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_words);

        word = new Words();
        request = new Request();
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle != null){
            user = (String) bundle.get("user");
        }

        arrayAdapter = new ArrayAdapter<String>(RequestedWords.this, android.R.layout.simple_list_item_1, arr);
        listview = (ListView) findViewById(R.id.requestList);
        listview.setAdapter(arrayAdapter);
        mRef = FirebaseDatabase.getInstance().getReference("Request");

       mDatabase = FirebaseDatabase.getInstance().getReference().child("Words");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    request = ds.getValue(Request.class);
                    arrUser.add(ds.getKey());
                    arr.add(request.getWord());
                    reqDef.add(request.getDefinition());
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
                showDescriptionDialogBox(i);
            }
        });

    }

    public void showDescriptionDialogBox(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage("The definition of this word is " + reqDef.get(position));
        dialog.setTitle("Do you approve of this word?");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                if(arr != null && reqDef != null){
                    word = new Words(arr.get(position), reqDef.get(position), ".", ".", ".");
                    createWord(arr.get(position),reqDef.get(position), arrUser.get(position));
                    Toast.makeText(RequestedWords.this, "Submitted", Toast.LENGTH_LONG);
                }
                message.cancel();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });
        dialog.show();
    }


    private void createWord(String w, String d, String id){
        if(w != null && d != null){
            word = new Words(w,d, ".", ".", ".");
            mDatabase.child(w).child("Word").setValue(w);
            mDatabase.child(w).child("Definition").setValue(d);
            mDatabase.child(w).child("Example").setValue(".");
            mDatabase.child(w).child("Audio").setValue(".");
            mDatabase.child(w).child("Image").setValue("lol.jpg");
            //mDatabase.child(w).child("Comments").child("Dummy").child("messageText").setValue("Make comments user friendly please");
            //mDatabase.child(w).child("Comments").child("Dummy").child("messageTime").setValue(12345);
            deleteWord(id);
        }
    }

    private void deleteWord(String id){
        mRef.child(id).removeValue();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.requestmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            //Requested Words list
            case R.id.home:
                Intent intent = new Intent(RequestedWords.this, DictionaryHome.class);
                intent.putExtra("user", user);
                startActivity(intent);
                //startActivity(new Intent(RequestedWords.this, DictionaryHome.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}