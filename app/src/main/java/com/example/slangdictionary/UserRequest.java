package com.example.slangdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class UserRequest extends AppCompatActivity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle != null){
            user = (String) bundle.get("user");
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
                Intent intent = new Intent(UserRequest.this, DictionaryHome.class);
                intent.putExtra("user", user);
                startActivity(intent);
                //startActivity(new Intent(UserRequest.this, DictionaryHome.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}