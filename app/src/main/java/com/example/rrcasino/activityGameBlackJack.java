package com.example.rrcasino;

/*
 * Created by Doug
 *
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class activityGameBlackJack extends AppCompatActivity {
    // TextViews
    private TextView tvScore;
    private TextView tvLastCard; //debug only

    // Buttons
    private Button hitButton;
    private Button stayButton;

    // ImageViews
    private ImageView pcard1;
    private ImageView pcard2;
    private ImageView pcard3;
    private ImageView pcard4;
    private ImageView pcard5;
    private ImageView dcard1;
    private ImageView dcard2;
    private ImageView dcard3;
    private ImageView dcard4;
    private ImageView dcard5;

    // Game variables
    private DeckHandler.Shoe deck;
    private Dealer dealer;
    private Player player;
    String handValue = "";  // var for debug purposes; displays total value of cards in hand
    String lastCard = "Last Card Info\n\n";  // var for debug purposes; displays total value of cards in hand

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);

        // Assign TextView IDs from XML
        tvScore = (TextView)findViewById(R.id.score);
        tvLastCard = (TextView)findViewById(R.id.inHand); //debug only
        // Assign ImageView IDs from XML
        this.pcard1 = (ImageView) findViewById(R.id.pcard1);
        this.pcard2 = (ImageView) findViewById(R.id.pcard2);
        this.pcard3 = (ImageView) findViewById(R.id.pcard3);
        this.pcard4 = (ImageView) findViewById(R.id.pcard4);
        this.pcard5 = (ImageView) findViewById(R.id.pcard5);
        this.dcard1 = (ImageView) findViewById(R.id.dcard1);
        this.dcard2 = (ImageView) findViewById(R.id.dcard2);
        this.dcard3 = (ImageView) findViewById(R.id.dcard3);
        this.dcard4 = (ImageView) findViewById(R.id.dcard4);
        this.dcard5 = (ImageView) findViewById(R.id.dcard5);
        // Assign button IDs from XML and set onclick Listeners
        this.hitButton = (Button) findViewById(R.id.hitButton);
        this.stayButton = (Button) findViewById(R.id.stayButton);
        this.hitButton.setOnClickListener(handleClick);
        this.stayButton.setOnClickListener(handleClick);


        this.deck = new DeckHandler.Shoe();
        this.dealer = new Dealer("DEALER", 1000);
        this.player = new Player("Player 1", 100);

        //startRound();
        //updateScore();

    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hitButton:
                    dealer.dealCard(player,deck);
                    setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard());
                    lastCard = "Last Card Info\n\n"; //debug purposes
                    updateScore();
                    break;
                case R.id.stayButton:
                    endRound();
                    updateScore();
                    break;
            }
        }
    };

    private void setImageResource (char participant, int cardNum, DeckHandler.Card card) {
        String img = card.getImageSource();
        Resources resources = getResources();
        final int resourceId = resources.getIdentifier(card.getImageSource(),"drawable",getPackageName());
        switch (participant) {
            case 'p':
            {
                switch (cardNum) {
                case 1:
                    pcard1.setImageResource(resourceId);
                    break;
                case 2:
                    pcard2.setImageResource(resourceId);
                    break;
                case 3:
                    pcard3.setImageResource(resourceId);
                    break;
                case 4:
                    pcard4.setImageResource(resourceId);
                    break;
                case 5:
                    pcard5.setImageResource(resourceId);
                    break;
                }
            }
            case 'd':

            default:
                break;
        }

    }

    private void startRound () {
        // deal first card
        dealer.dealCard(player, deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard());
        dealer.dealCard(dealer, deck);
        setImageResource('p',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard());

        // deal second card
        dealer.dealCard(player,deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard());
        dealer.dealCard(dealer, deck);
        setImageResource('p',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard());

    }

    private void endRound () {
        // setup face down card object to reset images
        DeckHandler.Card faceDown = new DeckHandler.Card(0,0, "b2fv");
        for (int i = 0; i <= player.getHand().getNumOfCardsInHand(); i++)
            setImageResource('p', i, faceDown);
        for (int i = 0; i <= dealer.getHand().getNumOfCardsInHand(); i++)
            setImageResource('d', i, faceDown);
        player.returnCards();
        dealer.returnCards();
    }

    public void updateScore() {
        handValue = "Player Score: " + player.getPlayerHandValue();
        tvScore.setText(handValue);

        //debug purposes
        lastCard += "Suit: " + dealer.getLastDealtCard().getSuit() + "\n";
        lastCard += "Rank: " + dealer.getLastDealtCard().getRank() + "\n";
        lastCard += "Val: " + dealer.getLastDealtCard().getValue() + "\n";
        lastCard += "Src: " + dealer.getLastDealtCard().getImageSource();
        tvLastCard.setText(lastCard);

    }
}