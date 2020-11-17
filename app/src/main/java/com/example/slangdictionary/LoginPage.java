package com.example.slangdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btn_signIn);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginPage.this, "Welcome Back!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginPage.this, DictionaryHome.class));
                }else{
                    Toast.makeText(LoginPage.this, "Log in failed", Toast.LENGTH_LONG).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = email.getText().toString();
                String p = password.getText().toString();
                if(e == null){
                    Toast.makeText(LoginPage.this,"Need an email", Toast.LENGTH_LONG).show();
                }else if(p == null){
                    Toast.makeText(LoginPage.this,"Need a password", Toast.LENGTH_LONG).show();
                }else if( e != null && p != null ){
                    mFirebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginPage.this, "Incorrect email/password", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(LoginPage.this, "Welcome back!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginPage.this, DictionaryHome.class));
                            }
                        }
                    });
                }
            }
        });
    }
}