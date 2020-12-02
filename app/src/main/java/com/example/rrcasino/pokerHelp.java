package com.example.rrcasino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pokerHelp extends AppCompatActivity {

    private Button overview;
    private Button actions;
    private Button howtowin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker_help);
        this.getSupportActionBar().hide();

        overview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

    }
}