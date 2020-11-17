package com.example.slangdictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    ArrayList<String> reqEx = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference mRef;
    DatabaseReference mDatabase;
    Words word;
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_words);

        word = new Words();

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



}