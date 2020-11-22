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
    TextView tvScore;
    TextView tvCardsInHand;
    private Button hitButton;
    private Button stayButton;
    private DeckHandler.Shoe deck;
    private Dealer dealer;
    private Player player;
    String inHand = "";  // var for debug purposes; displays cards in hand
    String handValue = "";  // var for debug purposes; displays total value of cards in hand

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);

        // Setup buttons and textviews
        tvScore = (TextView)findViewById(R.id.score);
        tvCardsInHand = (TextView)findViewById(R.id.inHand);
        this.hitButton = (Button) findViewById(R.id.hitButton);
        this.stayButton = (Button) findViewById(R.id.stayButton);
        this.hitButton.setOnClickListener(handleClick);
        this.stayButton.setOnClickListener(handleClick);

        this.deck = new DeckHandler.Shoe();
        this.dealer = new Dealer("DEALER", 1000);
        this.player = new Player("Player 1", 100);

        // temporary debug
        // deal first two cards to player
        // update score and cards in hand
        dealer.dealCard(player,deck);
        dealer.dealCard(player,deck);

        handValue = Integer.toString(player.getPlayerHandValue());
        tvScore.setText(handValue);

        // display curr cards in hand by rank
        Hand temp1h = player.getHand();
        for (DeckHandler.Card card : temp1h.getHand())
            inHand += card.getRank()+" ";
        tvCardsInHand.setText(inHand);

    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hitButton:
                    dealer.dealCard(player,deck);

                    // update cards in hand textview
                    Hand playerHand = player.getHand();
                    int indexLastCard = playerHand.getNumOfCardsInHand()-1;
                    DeckHandler.Card dealtCard = playerHand.getCard(indexLastCard);
                    inHand += dealtCard.getRank()+" ";
                    tvCardsInHand.setText(inHand);

                    // update score textview
                    handValue = Integer.toString(player.getPlayerHandValue());
                    tvScore.setText(handValue);
                    break;
                case R.id.stayButton:
                    player.returnCards();
                    dealer.returnCards();

                    // reset score
                    inHand = "";
                    tvCardsInHand.setText(inHand);
                    // reset cards in hand
                    handValue = Integer.toString(player.getPlayerHandValue());
                    tvScore.setText(handValue);
                    break;
            }
        }
    };
}