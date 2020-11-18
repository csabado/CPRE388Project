package com.example.slangdictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class wordDetails extends AppCompatActivity {
    private TextView word;
    private TextView definition;
    private TextView example;
    private ImageButton sound;
    private ImageView image;
    private Button submit;
    private ListView commentLayout;
    private EditText editText;
    Comment comment;
    private String w="";
    private String c="";
    DatabaseReference mRef;
    String soundURL="";

    StorageReference storage = FirebaseStorage.getInstance().getReference();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_details);
        comment = new Comment();
        word = (TextView) findViewById(R.id.tv_w);
        definition = (TextView) findViewById(R.id.definition);
        example = (TextView) findViewById(R.id.tv_ex);
        sound = (ImageButton) findViewById(R.id.ib_sound);
        image = (ImageView) findViewById(R.id.iv_img);
        submit = (Button) findViewById(R.id.submit);
        editText = (EditText) findViewById(R.id.comment);
        commentLayout = (ListView) findViewById(R.id.commentList);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mRef = FirebaseDatabase.getInstance().getReference().child("Comment");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w = word.getText().toString();
                c = editText.getText().toString();
                requestComment(w,c);
            }
        });
        if(bundle != null){
            String w = (String) bundle.get("word");
            String d = (String) bundle.get("def");
            String e = (String) bundle.get("ex");
            String im = (String) bundle.get("img");
           soundURL = (String) bundle.get("url");
            word.setText(w);
            definition.setText(d);
            example.setText(e);

        }

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try{
                    mediaPlayer.setDataSource(soundURL);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(),"Playing audio", Toast.LENGTH_LONG).show();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void requestComment(String w, String c){
        if(w != null && c!= null ){
            comment = new Comment(w, c);
            mRef.child(comment.getWord()).child("Word").setValue(w);
            mRef.child(comment.getWord()).child("Comment").setValue(c);
            Toast.makeText(wordDetails.this,"Submitted", Toast.LENGTH_LONG).show();
        }else if (w == null && c == null){
            Toast.makeText(wordDetails.this, "Missing Information", Toast.LENGTH_LONG).show();
        }

    }
}