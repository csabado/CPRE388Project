package com.example.slangdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestForm extends AppCompatActivity {

    private EditText word;
    private EditText definition;
    private EditText user;
    private Button submit;
    private DatabaseReference mDatabase;
    private String w="";
    private String d="";
    private String u="";
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        word = (EditText) findViewById(R.id.req_word);
        definition = (EditText) findViewById(R.id.req_definition);
        user = (EditText) findViewById(R.id.req_user);
        submit = (Button) findViewById(R.id.btn_submit);
        //request = new Request();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Request");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w = word.getText().toString();
                d = definition.getText().toString();
                u = user.getText().toString();
                requestWord(w,d,u);
            }
        });

    }

    private void requestWord(String w, String d, String user){
        if(w != null && d != null && user != null){
            request = new Request(w,d);
            mDatabase.child(user).child("Word").setValue(w);
            mDatabase.child(user).child("Definition").setValue(d);
            Toast.makeText(RequestForm.this,"Submitted", Toast.LENGTH_LONG).show();
        }else if (w == null && d == null && user == null){
            Toast.makeText(RequestForm.this, "Missing Information", Toast.LENGTH_LONG).show();
        }

    }
}