package com.example.slangdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText name;
    private Button login;
    private Button register;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.btn_signin);
        register = (Button) findViewById(R.id.btn_register);
        mFirebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String e = email.getText().toString();
                String p = password.getText().toString();
                if(e == null){
                    Toast.makeText(MainActivity.this,"Need an email", Toast.LENGTH_LONG).show();
                }else if(p == null){
                    Toast.makeText(MainActivity.this,"Need a password", Toast.LENGTH_LONG).show();
                }else if(n == null){
                    Toast.makeText(MainActivity.this,"Need a name ", Toast.LENGTH_LONG).show();
                }else if( e != null && p != null && n != null){
                    mFirebaseAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
//                                String s = "Sign up Failed" + task.getException();
//                                Toast.makeText(MainActivity.this, s,
//                                        Toast.LENGTH_SHORT).show();
//                                System.out.println(s);
                                Toast.makeText(MainActivity.this, "Sign Up Unsuccessful", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, DictionaryHome.class));
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (MainActivity.this, LoginPage.class));
            }
        });




    }
}