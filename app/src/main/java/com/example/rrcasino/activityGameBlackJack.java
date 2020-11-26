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
    private TextView tvPlayerScore;
    private TextView tvDealerScore;
    //private TextView tvLastCard; //debug only

    // Buttons
    private Button hitButton;
    private Button stayButton;
    private Button confirmButton;

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
    private ImageView[] playerCardImages;
    private ImageView[] dealerCardImages;
    private float transparent = 0;
    private float opaque = 1;

    // Game variables
    private DeckHandler.Shoe deck;
    private Dealer dealer;
    private Player player;
    private enum gameResult { WIN, LOSE, TIE }
    private boolean isRoundOver = false; // Needed in updateScore method
    private String playerHandValue = "";
    private String dealerHandValue = "";
    private int maxCardsInHand = 5;
    String lastCard = "Last Card Info\n\n";  // var for debug purposes only; displays total value of cards in hand

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);

        // Assign TextView IDs from XML
        tvPlayerScore = (TextView)findViewById(R.id.playerScore);
        tvDealerScore = (TextView)findViewById(R.id.dealerScore);
        //tvLastCard = (TextView)findViewById(R.id.inHand); //debug only

        // Assign ImageView IDs from XML
        this.pCard1 = (ImageView) findViewById(R.id.pCard1);
        this.pCard2 = (ImageView) findViewById(R.id.pCard2);
        this.pCard3 = (ImageView) findViewById(R.id.pCard3);
        this.pCard4 = (ImageView) findViewById(R.id.pCard4);
        this.pCard5 = (ImageView) findViewById(R.id.pCard5);
        playerCardImages = new ImageView[] {pCard1, pCard2, pCard3, pCard4, pCard5};
        this.dCard1 = (ImageView) findViewById(R.id.dCard1);
        this.dCard2 = (ImageView) findViewById(R.id.dCard2);
        this.dCard3 = (ImageView) findViewById(R.id.dCard3);
        this.dCard4 = (ImageView) findViewById(R.id.dCard4);
        this.dCard5 = (ImageView) findViewById(R.id.dCard5);
        dealerCardImages = new ImageView[] {dCard1, dCard2, dCard3, dCard4, dCard5};
        // Assign button IDs from XML and set onclick Listeners
        this.hitButton = (Button) findViewById(R.id.hitButton);
        this.stayButton = (Button) findViewById(R.id.stayButton);
        this.confirmButton = (Button) findViewById(R.id.confirmButton);
        this.hitButton.setOnClickListener(handleClick);
        this.stayButton.setOnClickListener(handleClick);
        this.confirmButton.setOnClickListener(handleClick);


        // Initialize new game
        this.deck = new DeckHandler.Shoe();
        this.dealer = new Dealer("DEALER", 1000);
        this.player = new Player("Player 1", 100);

        startRound();
    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        /*
         * Onclick listener for buttons
         *
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hitButton:
                    confirmButton.setEnabled(false); // Disable bet setter after play has started
                    dealer.dealCard(player,deck);
                    setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
                    if (player.getHand().getHandValue() > 21 || player.getHand().getNumOfCardsInHand() == maxCardsInHand)
                        endRound();
                    updateScore();
                    break;
                case R.id.stayButton:
                    dealerPlay();
                    endRound();
                    updateScore();
                    break;
                case R.id.confirmButton:
                    startRound();
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
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard1.setImageResource(resourceId);
                        break;
                    case 2:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard2.setImageResource(resourceId);
                        break;
                    case 3:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard3.setImageResource(resourceId);
                        break;
                    case 4:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard4.setImageResource(resourceId);
                        break;
                    case 5:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard5.setImageResource(resourceId);
                        break;
                }
                break;
            }
            case 'd': {
                switch (cardNum) {
                    case 0:
                        dealerCardImages[1].setAlpha(opaque);
                        dCard2.setImageResource(resourceId);
                        break;
                    case 1:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard1.setImageResource(resourceId);
                        break;
                    case 2:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard2.setImageResource(resourceId);
                        break;
                    case 3:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard3.setImageResource(resourceId);
                        break;
                    case 4:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard4.setImageResource(resourceId);
                        break;
                    case 5:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard5.setImageResource(resourceId);
                        break;
                }
                break;
            }
        }
    }

    private void startRound () {
        /* Create clean round
         * 1. Disable/Enable buttons
         * 2. Check that players hands are empty
         * 3. Update card images to face down, and make non-dealt cards transparent
         * 4. Enable functionality to set new bet amount
         * 5. Deal cards, and check for natural win
         */

        // Update usable buttons
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        confirmButton.setEnabled(false);
        // Loop through participant hands and set to all cards to null card
        for (int i = 1; i <= maxCardsInHand; i++) {
            setImageResource('p', i, "b2fv");
            playerCardImages[i-1].setAlpha(transparent);
        }
        for (int i = 1; i <= maxCardsInHand; i++) {
            setImageResource('d', i, "b2fv");
            dealerCardImages[i-1].setAlpha(transparent);
        }
        // Check that hands are empty
        if (player.getHand().getHandValue()>0)
            player.returnCards();
        if (dealer.getHand().getHandValue()>0)
            dealer.returnCards();

        //TODO setBet: Set bet value for current round (disable bet setter after hit button has been pressed)

        // Deal first card
        boolean dealerCheckNatural = false;
        dealer.dealCard(player, deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer, deck);
        setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        if (dealer.getLastDealtCard().getRank() > 9)
            dealerCheckNatural = true;

        // Deal second card
        dealer.dealCard(player,deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer, deck);
        setImageResource('d',0, "b2fv");

        // CheckNatural: If Dealer's first card is face card, check for BlackJack and endRound if true
        // else peek card and continue play
        if (dealerCheckNatural && dealer.getHand().getHandValue() == 21) {
            setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
            endRound();
        } else {
            setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        }
        // Check player natural
        if (player.getHand().getHandValue() == 21)
            endRound();

        updateScore();
    }

    private void endRound () {
        //TODO checkWin: Check for player win/loss
        //TODO updateFunds: Update funds from checkWin
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        confirmButton.setEnabled(true);
        updateScore();
    }

    public void dealerPlay() {
        setImageResource('d', 2, dealer.getHand().getCard(1).getImageSource());
        while (dealer.getHand().getHandValue() < 16 && dealer.getHand().getNumOfCardsInHand() < maxCardsInHand) {
            dealer.dealCard(dealer, deck);
            setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        }
    }

    public void updateScore() {
        dealerHandValue = "Dealer Score: " + dealer.getHand().getHandValue();
        tvDealerScore.setText(dealerHandValue);
        playerHandValue = "Player Score: " + player.getHand().getHandValue();
        tvPlayerScore.setText(playerHandValue);

        /*
        //debug purposes
        lastCard = "Last Card Info\n\n";
        lastCard += "Suit: " + dealer.getLastDealtCard().getSuit() + "\n";
        lastCard += "Rank: " + dealer.getLastDealtCard().getRank() + "\n";
        lastCard += "Val: " + dealer.getLastDealtCard().getValue() + "\n";
        lastCard += "Src: " + dealer.getLastDealtCard().getImageSource();
        tvLastCard.setText(lastCard);
         */
    }
}