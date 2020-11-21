package com.example.rrcasino;

/*
 * Created by Doug
 * Encompasses all player related info and functionality
 * intended to track current funds and cards held in player hand
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activityGameBlackJack extends AppCompatActivity {
    TextView score;
    private Button hitButton;
    private Button stayButton;
    private DeckHandler.Shoe deck;
    private Dealer dealer;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);

        score = (TextView)findViewById(R.id.score);
        this.hitButton = (Button) findViewById(R.id.hitButton);
        this.stayButton = (Button) findViewById(R.id.stayButton);
        this.hitButton.setOnClickListener(handleClick);
        this.stayButton.setOnClickListener(handleClick);

        this.deck = new DeckHandler.Shoe();
        this.dealer = new Dealer("DEALER", 1000);
        this.player = new Player("Player 1", 100);

    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        int myScore = 1;
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hitButton:
                    //dealer.dealCard(player,deck);
                    /*player.getPlayerHandValue();
                    score.setText(player.getPlayerHandValue());
                    Toast.makeText(activityGameBlackJack.this, player.getPlayerHandValue(), Toast.LENGTH_SHORT).show();
                    */
                    myScore++;
                    score.setText(myScore);
                    Toast.makeText(activityGameBlackJack.this, myScore, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.stayButton:
                    player.returnCards();
                    dealer.returnCards();
                    break;
            }
        }
    };
}