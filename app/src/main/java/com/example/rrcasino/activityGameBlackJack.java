package com.example.rrcasino;

/**
 * Created by Doug
 *
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
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
    private ImageView pCard1;
    private ImageView pCard2;
    private ImageView pCard3;
    private ImageView pCard4;
    private ImageView pCard5;
    private ImageView dCard1;
    private ImageView dCard2;
    private ImageView dCard3;
    private ImageView dCard4;
    private ImageView dCard5;

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
        this.pCard1 = (ImageView) findViewById(R.id.pCard1);
        this.pCard2 = (ImageView) findViewById(R.id.pCard2);
        this.pCard3 = (ImageView) findViewById(R.id.pCard3);
        this.pCard4 = (ImageView) findViewById(R.id.pCard4);
        this.pCard5 = (ImageView) findViewById(R.id.pCard5);
        this.dCard1 = (ImageView) findViewById(R.id.dCard1);
        this.dCard2 = (ImageView) findViewById(R.id.dCard2);
        this.dCard3 = (ImageView) findViewById(R.id.dCard3);
        this.dCard4 = (ImageView) findViewById(R.id.dCard4);
        this.dCard5 = (ImageView) findViewById(R.id.dCard5);
        // Assign button IDs from XML and set onclick Listeners
        this.hitButton = (Button) findViewById(R.id.hitButton);
        this.stayButton = (Button) findViewById(R.id.stayButton);
        this.hitButton.setOnClickListener(handleClick);
        this.stayButton.setOnClickListener(handleClick);

        // Initialize new game
        this.deck = new DeckHandler.Shoe();
        this.dealer = new Dealer("DEALER", 1000);
        this.player = new Player("Player 1", 100);

        startRound();
    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        /*
         * Function is used to track when hit and stay buttons are pressed.
         *
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hitButton:
                    dealer.dealCard(player,deck);
                    setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
                    updateScore();
                    // /*TODO*/ check for bust and end round if player busts
                    break;
                case R.id.stayButton:
                    // /*TODO*/ add dealer behaviour
                    endRound();
                    updateScore();
                    break;
            }
        }
    };

    private void setImageResource (char participant, int cardNum, String imageSource) {
        /*
         * First parameter takes in a 'p' or 'd' character specifying whether a player or dealer card is
         * being updated. Essentially this specifies the ImageView ID affix pcard or dcard.
         * Second parameter specifies the ImageView ID suffix by card number, eg 1 or 2 for pcard1 or pcard2
         * respectively.
         * Third parameter takes in a Card object (usually the last dealt card) that holds the image source
         * as a string
         */


        Resources resources = getResources();
        final int resourceId = resources.getIdentifier(imageSource,"drawable",getPackageName());
        switch (participant) {
            case 'p': {
                switch (cardNum) {
                    case 1:
                        pCard1.setImageResource(resourceId);
                        break;
                    case 2:
                        pCard2.setImageResource(resourceId);
                        break;
                    case 3:
                        pCard3.setImageResource(resourceId);
                        break;
                    case 4:
                        pCard4.setImageResource(resourceId);
                        break;
                    case 5:
                        pCard5.setImageResource(resourceId);
                        break;
                }
                break;
            }
            case 'd': {
                switch (cardNum) {
                    case 1:
                        dCard1.setImageResource(resourceId);
                        break;
                    case 2:
                        // /*TODO*/ Dealer card should be face down until player stays
                        dCard2.setImageResource(resourceId);
                        break;
                    case 3:
                        dCard3.setImageResource(resourceId);
                        break;
                    case 4:
                        dCard4.setImageResource(resourceId);
                        break;
                    case 5:
                        dCard5.setImageResource(resourceId);
                        break;
                }
                break;
            }
        }
    }

    private void startRound () {
        // /*TODO*/ setBet: Set bet value for current round

        // deal first card
        dealer.dealCard(player, deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer, deck);
        setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        updateScore();

        // deal second card
        dealer.dealCard(player,deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer, deck);
        setImageResource('d',dealer.getHand().getNumOfCardsInHand(), "b2fv");
        updateScore();

        // /*TODO*/ checkNatural: If Dealer's first card is face card, check for BlackJack and endRound if true

    }

    private void endRound () {
        // /*TODO*/ checkWin: Check for player win/loss
        // /*TODO*/ updateFunds: Update funds from checkWin

        // setup Card object with face down image resource to reset images
        DeckHandler.Card nullCard = new DeckHandler.Card(0,0, "b2fv");
        // loop through participant hands and set to all cards to null card
        for (int i = 0; i <= player.getHand().getNumOfCardsInHand(); i++)
            setImageResource('p', i, nullCard.getImageSource());
        for (int i = 0; i <= dealer.getHand().getNumOfCardsInHand(); i++)
            setImageResource('d', i, nullCard.getImageSource());
        // empty participant hands
        player.returnCards();
        dealer.returnCards();

        startRound();
    }

    public void updateScore() {
        handValue = "Player Score: " + player.getPlayerHandValue();
        tvScore.setText(handValue);

        //debug purposes
        lastCard = "Last Card Info\n\n";
        lastCard += "Suit: " + dealer.getLastDealtCard().getSuit() + "\n";
        lastCard += "Rank: " + dealer.getLastDealtCard().getRank() + "\n";
        lastCard += "Val: " + dealer.getLastDealtCard().getValue() + "\n";
        lastCard += "Src: " + dealer.getLastDealtCard().getImageSource();
        tvLastCard.setText(lastCard);
    }
}