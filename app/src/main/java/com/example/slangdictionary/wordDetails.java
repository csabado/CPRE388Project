package com.example.slangdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class wordDetails extends AppCompatActivity {
    private TextView word;
    private TextView definition;
    private TextView example;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_details);

        word = (TextView) findViewById(R.id.tv_w);
        definition = (TextView) findViewById(R.id.definition);
        example = (TextView) findViewById(R.id.tv_ex);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        if(bundle != null){
            String w = (String) bundle.get("word");
            String d = (String) bundle.get("def");
            String e = (String) bundle.get("ex");
            word.setText(w);
            definition.setText(d);
            example.setText(e);
        }
    }
}