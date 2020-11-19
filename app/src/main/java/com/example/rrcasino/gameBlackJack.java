package com.example.rrcasino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Random;

public class gameBlackJack extends AppCompatActivity {
    /*
    Card[] shoe;
    int numOfDecks = 1;
    int k = 0;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);

    }

    /*
    public void createDeck () {
        // generate shoe
        // creates a shoe of decks by looping through array and assigning
        // value of cards in ascending order
        shoe = new Card[52 * numOfDecks];
        for (int i = 0; i < numOfDecks; i++) {
            for (int suit = 0; suit < 4; suit++) {
                for (int rank = 0; rank < 13; rank++) {
                    shoe[k] = new Card(suit, rank);
                    k++;
                }
            }
        }
    }

    public void shuffleDeck () {
        // shuffle 100 times
        // inner loop shuffles cards in array by swapping elements
        // outer loop runs shuffle 100 times
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < k; j++) {
                Random r = new Random();
                int rand = r.nextInt(k + 1);
                Card temp = shoe[j];
                shoe[j] = shoe[rand];
                shoe[rand] = temp;
            }
        }
    }

    void dealCard () {

    }
    */

}