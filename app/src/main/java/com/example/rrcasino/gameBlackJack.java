package com.example.rrcasino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class gameBlackJack extends AppCompatActivity {
    Card[] shoe;
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);

        // generate shoe
        int numOfDecks = 1;
        shoe = new Card[52*numOfDecks];
        for (int i = 0; i < numOfDecks; i++) {
            for (int suit = 0; suit < 4; suit++) {
                for (int rank = 0; rank < 13; rank++) {
                    shoe[k] = new Card(suit, rank);
                    k++;
                }
            }
        }
    }
}