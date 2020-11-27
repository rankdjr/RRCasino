package com.example.rrcasino;

/**
 * Created by Doug
 * Main activity file
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private enum gameResult { BLACKJACK, WIN, LOSE, TIE }
    private boolean isRoundOver = false; // Needed in updateScore method
    private String cardFaceDown = "b2fv";
    private String playerHandValue = "";
    private String dealerHandValue = "";
    private int maxCardsInHand = 5;
    String lastCard = "Last Card Info\n\n";  // var for debug purposes only; displays total value of cards in hand

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);

        // Assign TextView IDs from XML
        tvPlayerScore = findViewById(R.id.playerScore);
        tvDealerScore = findViewById(R.id.dealerScore);
        //tvLastCard = findViewById(R.id.inHand); //debug only

        // Assign ImageView IDs from XML
        this.pCard1 = findViewById(R.id.pCard1);
        this.pCard2 = findViewById(R.id.pCard2);
        this.pCard3 = findViewById(R.id.pCard3);
        this.pCard4 = findViewById(R.id.pCard4);
        this.pCard5 = findViewById(R.id.pCard5);
        playerCardImages = new ImageView[] {pCard1, pCard2, pCard3, pCard4, pCard5};
        this.dCard1 = findViewById(R.id.dCard1);
        this.dCard2 = findViewById(R.id.dCard2);
        this.dCard3 = findViewById(R.id.dCard3);
        this.dCard4 = findViewById(R.id.dCard4);
        this.dCard5 = findViewById(R.id.dCard5);
        dealerCardImages = new ImageView[] {dCard1, dCard2, dCard3, dCard4, dCard5};
        // Assign button IDs from XML and set onclick Listeners
        this.hitButton = findViewById(R.id.hitButton);
        this.stayButton = findViewById(R.id.stayButton);
        this.confirmButton = findViewById(R.id.confirmButton);
        this.hitButton.setOnClickListener(handleClick);
        this.stayButton.setOnClickListener(handleClick);
        this.confirmButton.setOnClickListener(handleClick);


        // Initialize new game
        this.deck = new DeckHandler.Shoe();
        this.dealer = new Dealer("DEALER", 0);
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
                    confirmButton.setEnabled(false); // Disable bet setter after play has started
                    startRound();
                    break;
            }
        }
    };

    private void setImageResource (char participant, int cardNum, String imageSource) {
        /*
         * First parameter takes in a 'p' or 'd' character specifying whether a player or dealer card is
         * being updated. Essentially this specifies the ImageView ID affix pCard or dCard.
         * Second parameter specifies the ImageView ID suffix by card number, eg 1 or 2 for pCard1 or pCard2
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
         * 4. Enable SeekBar to set new bet amount
         * 5. Deal cards, and check for natural win
         */

        // Update usable buttons
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        confirmButton.setEnabled(false);
        // Loop through participant hands and set to all cards to null card
        for (int i = 1; i <= maxCardsInHand; i++) {
            setImageResource('p', i, cardFaceDown);
            playerCardImages[i-1].setAlpha(transparent);
        }
        for (int i = 1; i <= maxCardsInHand; i++) {
            setImageResource('d', i, cardFaceDown);
            dealerCardImages[i-1].setAlpha(transparent);
        }
        // Check that hands are empty
        if (player.getHand().getHandValue()>0)
            player.returnCards();
        if (dealer.getHand().getHandValue()>0)
            dealer.returnCards();

        //TODO setBet: Set bet value for current round (disable bet setter after hit button has been pressed)

        // Deal first cards
        dealer.dealCard(player, deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer, deck);
        setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

        // Deal second Player card
        dealer.dealCard(player,deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        // Check for Player BlackJack
        if (player.getHand().getHandValue() == 21)
            endRound();

        // Deal second Dealer card
        dealer.dealCard(dealer, deck);
        // Check for Dealer BlackJack and show card if true
        if (dealer.getHand().getHandValue() == 21) {
            setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
            endRound();
        } else {
            setImageResource('d', dealer.getHand().getNumOfCardsInHand(), cardFaceDown);
        }

        updateScore();
    }

    private void endRound () {
        //TODO checkWin: Check for player win/loss
        //TODO updateFunds: Update funds from checkWin
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        confirmButton.setEnabled(true);
        updateScore();
        switch (checkWin()) {
            case BLACKJACK:
                Toast.makeText(activityGameBlackJack.this, "Player BlackJack", Toast.LENGTH_SHORT).show();
                break;
            case WIN:
                Toast.makeText(activityGameBlackJack.this, "Player Win", Toast.LENGTH_SHORT).show();
                break;
            case TIE:
                Toast.makeText(activityGameBlackJack.this, "Push", Toast.LENGTH_SHORT).show();
                break;
            case LOSE:
                Toast.makeText(activityGameBlackJack.this, "Dealer Win", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void dealerPlay() {
        /* Dealer flips second card.
         * Dealer hits on soft 17 (Ace and 6 in hand)
         * Dealer hits if hand value is below 17.
         */

        // Reveal second card
        setImageResource('d', 2, dealer.getHand().getCard(1).getImageSource());
        // Hit on soft 17
        if (dealer.getHand().getHandValue() == 17 && dealer.getHand().getAcesInHand() == 1) {
            dealer.dealCard(dealer, deck);
            setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        }
        // Dealer hits if below 16
        while (dealer.getHand().getHandValue() < 17 && dealer.getHand().getNumOfCardsInHand() < maxCardsInHand) {
            dealer.dealCard(dealer, deck);
            setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        }
    }

    public gameResult checkWin() {
        /* Function checks for win conditions on current hand values
         * Checks for Player BlackJack and pays 1.5x bet amount, returns BLACKJACK
         * If player busts, returns LOSE
         * Assuming player did not bust and either 1)Dealer busted or 2)Player has higher hand value, returns WIN
         * Returns TIE in the case of a push
         * Returns LOSE if all above cases are false (eg player hand < dealer hand)
         */

        gameResult result = null;

        int playerHandValue = player.getHand().getHandValue();
        int dealerHandValue = dealer.getHand().getHandValue();

        if (playerHandValue == 21 && dealer.getHand().getNumOfCardsInHand() == 1)
            result = gameResult.BLACKJACK;
        else if (player.getHand().checkBust())
            result = gameResult.LOSE;
        else if (playerHandValue > dealerHandValue || dealer.getHand().checkBust())
            result = gameResult.WIN;
        else if (playerHandValue == dealerHandValue)
            result = gameResult.TIE;
        else
            result = gameResult.LOSE;

        return result;
    }

    public void updateScore() {
        playerHandValue = "Player Score: " + player.getHand().getHandValue();
        tvPlayerScore.setText(playerHandValue);
        if (dealer.getHand().getNumOfCardsInHand() > 2) {
            dealerHandValue = "Dealer Score: " + dealer.getHand().getHandValue();
            tvDealerScore.setText(dealerHandValue);
        }

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