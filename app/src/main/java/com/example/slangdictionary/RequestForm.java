package com.example.slangdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle != null){
            currentUser = (String) bundle.get("user");
        }
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

    private void requestWord(String w, String d, String u){
        if (w.isEmpty() || d.isEmpty() || u.isEmpty()){
            Toast.makeText(RequestForm.this, "Missing Information", Toast.LENGTH_LONG).show();
        }else if(w != "" && d != "" && u != ""){
            request = new Request(w,d,u);
            mDatabase.child(u).child("Word").setValue(w);
            mDatabase.child(u).child("Definition").setValue(d);
            Toast.makeText(RequestForm.this,"Submitted", Toast.LENGTH_LONG).show();
            word.setText("");
            definition.setText("");
            user.setText("");
        }
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
                Intent intent = new Intent(RequestForm.this, DictionaryHome.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
                //startActivity(new Intent(RequestForm.this, DictionaryHome.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}