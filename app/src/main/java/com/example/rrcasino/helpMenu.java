package com.example.rrcasino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class helpMenu extends AppCompatActivity {
    private ImageButton pokerIMGButton;
    private ImageButton bjackIMGButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);
        this.getSupportActionBar().hide();

        pokerIMGButton = findViewById(R.id.pokerHelp);
        bjackIMGButton = findViewById(R.id.blackjackHelp);
        pokerIMGButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openPokerHelp();
            }
        });

        bjackIMGButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openBJackHelp();
            }
        });
    }

    public void openPokerHelp() {
        Intent openPokerHelp = new Intent(this, pokerHelp.class);
        startActivity(openPokerHelp);
    }
    public void openBJackHelp() {
        Intent openBJackHelp = new Intent(this, bjackHelp.class);
        startActivity(openBJackHelp);
    }

}