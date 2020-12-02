package com.example.rrcasino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class gameSelection extends AppCompatActivity {
    ImageButton pokerImageButton;
    ImageButton bJackImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        bJackImageButton = (ImageButton) findViewById(R.id.bJackImageButton);
        bJackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadGameBJack = new Intent (gameSelection.this, activityGameBlackJack.class);
                startActivity(intentLoadGameBJack);
            }
        });
        pokerImageButton = (ImageButton) findViewById(R.id.pokerImageButton);
        pokerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadGamePoker = new Intent (gameSelection.this, activityGamePoker.class);
                startActivity(intentLoadGamePoker);
            }
        });
    }
}