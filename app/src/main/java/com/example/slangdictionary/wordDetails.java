package com.example.slangdictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class wordDetails extends AppCompatActivity {
    private TextView word;
    private TextView definition;
    List<String> arr = new ArrayList<>();
    ArrayList<String> reqDef = new ArrayList<>();
    private TextView example;
    ArrayAdapter<String> arrayAdapter;
    private ImageButton sound;
    private ImageView image;
    private Button chat;
    private ListView commentLayout;
    private EditText editText;
    Comment comment;
    private String w="";
    private String c="";
    DatabaseReference mRef;
    String soundURL="";
    String user;
    String im;

    FirebaseStorage mStorage = FirebaseStorage.getInstance();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_details);


        word = (TextView) findViewById(R.id.tv_w);
        definition = (TextView) findViewById(R.id.definition);
        example = (TextView) findViewById(R.id.tv_ex);
        sound = (ImageButton) findViewById(R.id.ib_sound);
        image = (ImageView) findViewById(R.id.iv_img);
        chat = (Button) findViewById(R.id.Chat);




//        chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                w = word.getText().toString();
//                c = editText.getText().toString();
//                requestComment(w,c);
//                Intent intent = new Intent(wordDetails.this, ChatRoom.class);
//                startActivity(intent);
//
//            }
//        });

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle != null){
            String w = (String) bundle.get("word");
            String d = (String) bundle.get("def");
            String e = (String) bundle.get("ex");
            im = (String) bundle.get("img");
           soundURL = (String) bundle.get("url");
           user = (String) bundle.get("user");
            word.setText("Word: " + w);
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

        //Image shit
        try{
            StorageReference storage = mStorage.getReferenceFromUrl("gs://slangdictionary-aa18f.appspot.com").child(im);
            final File file = File.createTempFile("image","jpg");
            storage.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }


//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds: snapshot.getChildren()){
//                    comment = ds.getValue(Comment.class);
//                    arr.add(comment.getWord());
//                    reqDef.add(comment.getComment());
//                }
//                commentLayout.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//
//        commentLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), wordDetails.class);
//                 intent.putExtra("word",arr.get(i));
//
//                intent.putExtra("comment",reqDef.get(i));
//
//                startActivity(intent);
//            }
//        });
    }


//    private void requestComment(String w, String c){
//        if(w != null && c!= null ){
//            comment = new Comment(w, c);
//            mRef.child(comment.getWord()).child("Word").setValue(w);
//            mRef.child(comment.getWord()).child("Comment").setValue(c);
//            Toast.makeText(wordDetails.this,"Submitted", Toast.LENGTH_LONG).show();
//        }else if (w == null && c == null){
//            Toast.makeText(wordDetails.this, "Missing Information", Toast.LENGTH_LONG).show();
//        }
//    }

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
                Intent intent = new Intent(wordDetails.this, DictionaryHome.class);
                intent.putExtra("user", user);
                startActivity(intent);
                //startActivity(new Intent(RequestedWords.this, DictionaryHome.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}