package com.example.rrcasino;

/*
 * Created by Doug
 * Main activity file
 */

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activityGameBlackJack extends AppCompatActivity {
    // Game Variables
    enum gameResult {BLACKJACK, WIN, LOSE, TIE}
    DeckHandler.Shoe deck;
    Dealer dealer;
    Player player;
    ArrayList<roundData> roundDataArrayList;
    roundData currRoundData;
    boolean dealerPlayed = false;
    boolean playingSplit = false;
    int currentBet;
    final int minBet = 10;
    final String cardFaceDown = "b2fv";
    final int maxCardsInHand = 11;

    static class roundData {
        private Integer handTotal;
        private Integer bet;
        private boolean playerNatural;
        private boolean dealerNatural;

        // constructor
        public roundData(Integer bet, Integer handTotal) {
            this.bet = bet;
            this.handTotal = handTotal;
            this.dealerNatural = false;
            this.playerNatural = false;
        }

        // getter
        public int getBet() { return bet; }
        public int getHandTotal() { return handTotal; }
        public boolean isPlayerNatural() { return playerNatural; }
        public boolean isDealerNatural() { return dealerNatural; }
        // setter
        public void setBet(int bet) { this.bet = bet; }
        public void setHandTotal(int handTotal) { this.handTotal = handTotal; }
        public void setPlayerNatural() { this.playerNatural = true; }
        public void resetPlayerNatural() { this.playerNatural = false; }
        public void setDealerNatural() { this.dealerNatural = true; }
        public void resetDealerNatural() { this.dealerNatural = false; }
    }

    // TextViews
    private TextView tvPlayerScore;
    private TextView tvPlayerSplitScore;
    private TextView tvDealerScore;
    private TextView tvBet;
    private TextView tvBalance;
    //private TextView tvLastCard; //debug only

    // SeekBar
    private SeekBar sbBet;

    // Buttons
    private Button hitButton;
    private Button stayButton;
    private Button confirmButton;
    private Button doubleButton;
    private Button splitButton;

    // ImageViews
    private ImageView pCard1;
    private ImageView pCard2;
    private ImageView pCard3;
    private ImageView pCard4;
    private ImageView pCard5;
    private ImageView pCard6;
    private ImageView pCard7;
    private ImageView pCard8;
    private ImageView pCard9;
    private ImageView pCard10;
    private ImageView pCard11;
    private ImageView sCard1;
    private ImageView sCard2;
    private ImageView sCard3;
    private ImageView sCard4;
    private ImageView sCard5;
    private ImageView sCard6;
    private ImageView sCard7;
    private ImageView sCard8;
    private ImageView sCard9;
    private ImageView sCard10;
    private ImageView sCard11;
    private ImageView dCard1;
    private ImageView dCard2;
    private ImageView dCard3;
    private ImageView dCard4;
    private ImageView dCard5;
    private ImageView dCard6;
    private ImageView dCard7;
    private ImageView dCard8;
    private ImageView dCard9;
    private ImageView dCard10;
    private ImageView dCard11;
    private ImageView[] playerCardImages;
    private ImageView[] playerSplitCardImages;
    private ImageView[] dealerCardImages;
    private final float transparent = 0;
    private final float opaque = 1;
    private LinearLayout pCardsLayout;
    private LinearLayout dCardsLayout;
    private LinearLayout sCardsLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_black_jack);
        this.getSupportActionBar().hide();

        // Assign TextView IDs from XML
        tvPlayerScore = findViewById(R.id.tvPlayerScore);
        tvPlayerSplitScore = findViewById(R.id.tvPlayerSplitScore);
        tvDealerScore = findViewById(R.id.tvDealerScore);
        tvBet = findViewById(R.id.tvBet);
        tvBalance = findViewById(R.id.tvBalance);
        //tvLastCard = findViewById(R.id.inHand); //debug only

        // Initialize ImageView IDs
        this.pCard1 = findViewById(R.id.pCard1);
        this.pCard2 = findViewById(R.id.pCard2);
        this.pCard3 = findViewById(R.id.pCard3);
        this.pCard4 = findViewById(R.id.pCard4);
        this.pCard5 = findViewById(R.id.pCard5);
        this.pCard6 = findViewById(R.id.pCard6);
        this.pCard7 = findViewById(R.id.pCard7);
        this.pCard8 = findViewById(R.id.pCard8);
        this.pCard9 = findViewById(R.id.pCard9);
        this.pCard10 = findViewById(R.id.pCard10);
        this.pCard11 = findViewById(R.id.pCard11);
        playerCardImages = new ImageView[] { pCard1, pCard2, pCard3, pCard4, pCard5, pCard6,
                pCard7, pCard8, pCard9, pCard10, pCard11 };
        this.pCardsLayout = findViewById(R.id.pCardLayout);
        this.sCard1 = findViewById(R.id.sCard1);
        this.sCard2 = findViewById(R.id.sCard2);
        this.sCard3 = findViewById(R.id.sCard3);
        this.sCard4 = findViewById(R.id.sCard4);
        this.sCard5 = findViewById(R.id.sCard5);
        this.sCard6 = findViewById(R.id.sCard6);
        this.sCard7 = findViewById(R.id.sCard7);
        this.sCard8 = findViewById(R.id.sCard8);
        this.sCard9 = findViewById(R.id.sCard9);
        this.sCard10 = findViewById(R.id.sCard10);
        this.sCard11 = findViewById(R.id.sCard11);
        playerSplitCardImages = new ImageView[] {sCard1, sCard2, sCard3, sCard4, sCard5, sCard6,
                sCard7, sCard8, sCard9, sCard10, sCard11 };
        this.sCardsLayout = findViewById(R.id.sCardLayout);
        this.dCard1 = findViewById(R.id.dCard1);
        this.dCard2 = findViewById(R.id.dCard2);
        this.dCard3 = findViewById(R.id.dCard3);
        this.dCard4 = findViewById(R.id.dCard4);
        this.dCard5 = findViewById(R.id.dCard5);
        this.dCard6 = findViewById(R.id.dCard6);
        this.dCard7 = findViewById(R.id.dCard7);
        this.dCard8 = findViewById(R.id.dCard8);
        this.dCard9 = findViewById(R.id.dCard9);
        this.dCard10 = findViewById(R.id.dCard10);
        this.dCard11 = findViewById(R.id.dCard11);
        dealerCardImages = new ImageView[] {dCard1, dCard2, dCard3, dCard4, dCard5, dCard6,
                dCard7, dCard8, dCard9, dCard10, dCard11 };
        this.dCardsLayout = findViewById(R.id.dCardLayout);
        // Initialize button IDs and set onclick Listeners
        this.hitButton = findViewById(R.id.hitButton);
        this.stayButton = findViewById(R.id.stayButton);
        this.confirmButton = findViewById(R.id.confirmButton);
        this.doubleButton = findViewById(R.id.doubleButton);
        this.splitButton = findViewById(R.id.splitButton);
        this.hitButton.setOnClickListener(handleClick);
        this.stayButton.setOnClickListener(handleClick);
        this.confirmButton.setOnClickListener(handleClick);
        this.doubleButton.setOnClickListener(handleClick);
        this.splitButton.setOnClickListener(handleClick);
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        confirmButton.setEnabled(false);
        doubleButton.setEnabled(false);
        splitButton.setEnabled(false);

        // Initialize SeekBar
        this.sbBet = findViewById(R.id.sbBet);
        sbBet.setMin(minBet); //$10
        sbBet.setMax(100); // Place holder until player has initiated starting funds
        sbBet.setProgress(minBet); //Default starting value of SeekBar to minimum bet
        sbBet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                tvBet.setText("$"+progressChangedValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentBet = progressChangedValue;
            }
        });

        // Initialize new game
        this.deck = new DeckHandler.Shoe();
        this.dealer = new Dealer("DEALER", 0);
        this.player = new Player("Player 1", 500);
        this.roundDataArrayList = new ArrayList<>();
        tvBalance.setText("Balance: $"+player.getBalance());
        currentBet = sbBet.getProgress(); //Set starting bet to default SeekBar value ($10)
        tvBet.setText("$"+currentBet);
        confirmButton.setEnabled(true);
    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        /*
         * Onclick listener for buttons
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.hitButton: {
                    // Update usable buttons
                    splitButton.setEnabled(false); // Disable split button while playing
                    doubleButton.setEnabled(false); // Disable split button while playing
                    hit(); // Deal card, update game display and current round data

                    // Check for bust or autostay after verifying number of hands in play
                    if (player.getNumOfHandsInPlay() == 1) {
                        if (player.getHand().getHandValue() >= 21) {
                            saveRound();
                            endRound();
                        }
                    } else {
                        if (player.getHand().getHandValue() >= 21) {
                            // save round data, retrieve next hand, and deal first card to next hand
                            saveRound();
                            player.getHand().clearHand();
                            player.setHand(player.getNextHand());
                            playingSplit = true;
                            hit();
                            if (player.getHand().getHandValue() == 21) {
                                currRoundData.setPlayerNatural();
                                endRound();
                            }
                            if (player.getHand().getHandValue() < 12)
                                doubleButton.setEnabled(true);
                        }
                    }
                    break;
                }
                case R.id.stayButton:
                    // Update usable buttons
                    splitButton.setEnabled(false); // Disable if player does not split
                    saveRound();
                    if (player.getNumOfHandsInPlay() == 1) {
                        // Player stayed with on final in hand in round --> pass game logic to dealer and end round
                        dealerPlay();
                        endRound();
                    } else {
                        // Player has more than one hand in play --> retrieve next hand
                        player.getHand().clearHand();
                        player.setHand(player.getNextHand());
                        playingSplit = true;
                        hit();
                        if (player.getHand().getHandValue() == 21) {
                            currRoundData.setPlayerNatural();
                            endRound();
                        }
                    }
                    break;
                case R.id.confirmButton:
                    // Deal new round
                    confirmButton.setEnabled(false); // Disable after initial click
                    sbBet.setEnabled(false); // Disable changes to betting after deal
                    currentBet = sbBet.getProgress(); // save current bet
                    startRound();
                    break;
                case R.id.doubleButton:
                    // Double bet amount and save current bet to round data
                    doubleButton.setEnabled(false); // Disable after initial click
                    sbBet.setEnabled(false); // Disable changes to betting after deal
                    currentBet = currentBet*2;
                    tvBet.setText("$"+currentBet);
                    currRoundData.setBet(currentBet);
                    hit();
                    if (player.getNumOfHandsInPlay() == 1) {
                        // Player busted and this is the only hand in play --> end round
                        saveRound();
                        dealerPlay();
                        endRound();
                    } else {
                        // Player bet double but hand is split --> save round data, retrieve next hand, and deal first card to next hand
                        saveRound();
                        player.getHand().clearHand();
                        player.setHand(player.getNextHand());
                        playingSplit = true;
                        hit();
                        if (player.getHand().getHandValue() < 12)
                            doubleButton.setEnabled(true);
                    }
                    break;
                case R.id.splitButton:
                    splitButton.setEnabled(false); // Disable after initial click
                    splitHand();
                    if (player.getHand().getHandValue() == 21) {
                        currRoundData.setPlayerNatural();
                        saveRound();
                        player.getHand().clearHand();
                        player.setHand(player.getNextHand());
                        playingSplit = true;
                        hit();
                    }
                    if (player.getHand().getHandValue() > 11)
                        doubleButton.setEnabled(false);
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
        // Display metrics to adjust left margin of first card; Used to center hand in screen
        DisplayMetrics dm = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int leftMargin = 0;
        // Card Image resources
        Resources resources = getResources();
        final int resourceId = resources.getIdentifier(imageSource,"drawable",getPackageName());
        switch (participant) {
            case 'p': {
                for (int i = 0; i < cardNum; i++)
                    leftMargin -= 50;
                param.setMargins(leftMargin, 0, 0, 0);
                pCardsLayout.setLayoutParams(param);
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
                    case 6:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard6.setImageResource(resourceId);
                        break;
                    case 7:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard7.setImageResource(resourceId);
                        break;
                    case 8:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard8.setImageResource(resourceId);
                        break;
                    case 9:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard9.setImageResource(resourceId);
                        break;
                    case 10:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard10.setImageResource(resourceId);
                        break;
                    case 11:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pCard11.setImageResource(resourceId);
                        break;
                }
                break;
            }
            case 'd': {
                for (int i = 0; i < cardNum; i++)
                    leftMargin -= 50;
                param.setMargins(leftMargin, 0, 0, 0);
                dCardsLayout.setLayoutParams(param);
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
                    case 6:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard6.setImageResource(resourceId);
                        break;
                    case 7:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard7.setImageResource(resourceId);
                        break;
                    case 8:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard8.setImageResource(resourceId);
                        break;
                    case 9:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard9.setImageResource(resourceId);
                        break;
                    case 10:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard10.setImageResource(resourceId);
                        break;
                    case 11:
                        dealerCardImages[cardNum-1].setAlpha(opaque);
                        dCard11.setImageResource(resourceId);
                        break;
                }
                break;
            }
            case 's': {
                for (int i = 0; i < cardNum; i++)
                    leftMargin -= 50;
                param.setMargins(leftMargin, 0, 0, 0);
                sCardsLayout.setLayoutParams(param);
                switch (cardNum) {
                    case 1:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard1.setImageResource(resourceId);
                        break;
                    case 2:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard2.setImageResource(resourceId);
                        break;
                    case 3:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard3.setImageResource(resourceId);
                        break;
                    case 4:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard4.setImageResource(resourceId);
                        break;
                    case 5:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard5.setImageResource(resourceId);
                        break;
                    case 6:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard6.setImageResource(resourceId);
                        break;
                    case 7:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard7.setImageResource(resourceId);
                        break;
                    case 8:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard8.setImageResource(resourceId);
                        break;
                    case 9:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard9.setImageResource(resourceId);
                        break;
                    case 10:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard10.setImageResource(resourceId);
                        break;
                    case 11:
                        playerSplitCardImages[cardNum-1].setAlpha(opaque);
                        sCard11.setImageResource(resourceId);
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
        // Update usable buttons and booleans
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        confirmButton.setEnabled(false);
        doubleButton.setEnabled(false);
        splitButton.setEnabled(false);
        tvPlayerSplitScore.setAlpha(transparent);
        dealerPlayed = false;
        // Loop through participant hands and set to all cards to null card
        for (int i = 1; i <= maxCardsInHand; i++) {
            setImageResource('p', i, cardFaceDown);
            playerCardImages[i-1].setAlpha(transparent);
            setImageResource('d', i, cardFaceDown);
            dealerCardImages[i - 1].setAlpha(transparent);
            setImageResource('s', i, cardFaceDown);
            playerSplitCardImages[i - 1].setAlpha(transparent);
        }
        // Check that hands are empty
        if (player.getHand().getHandValue()>0)
            player.returnCards();
        if (dealer.getHand().getHandValue()>0)
            dealer.returnCards();

        // Deal cards, update score, and create instance for current round data
        currRoundData = new roundData(currentBet, player.getHand().getHandValue());
        newDeal();
        updateScore();
    }

    private void newDeal() {
        // Deal first cards
        dealer.dealCard(player, deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer, deck);
        setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

        // Deal second cards
        dealer.dealCard(player,deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        if (player.getHand().getHandValue() == 21)
            currRoundData.setPlayerNatural();
        dealer.dealCard(dealer, deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), cardFaceDown);
        if (dealer.getHand().getHandValue() == 21) {
            currRoundData.setDealerNatural();
            setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        }

        // Check for Dealer BlackJack and show card if true
        if (player.getHand().getHandValue() == 21 || dealer.getHand().getHandValue() == 21) {
            saveRound();
            endRound();
        } else {
            //debugSplitHand();
            // Enable split button if player has cards of same rank
            if (player.getHand().getCard(0).getRank() == player.getHand().getCard(1).getRank())
                splitButton.setEnabled(true);
            // Enable double button if player score <= 11
            if (player.getHand().getHandValue() < 12 && player.getBalance() > currentBet * 2)
                doubleButton.setEnabled(true);
        }
    }

    private void debugSplitHand() {
        //Remove below code after done debugging split hand functionality
        DeckHandler.Card card1 = new DeckHandler.Card(1,2, "c1_2");
        DeckHandler.Card card2 = new DeckHandler.Card(3,2, "c3_2");
        player.getHand().clearHand();
        player.getHand().addCard(card1);
        player.getHand().addCard(card2);
        setImageResource('p', 1, card1.getImageSource());
        setImageResource('p', 2, card2.getImageSource());
        //End of debug
    }

    private void endRound () {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        doubleButton.setEnabled(false);
        splitButton.setEnabled(false);
        confirmButton.setEnabled(true);
        playingSplit = false;

        // TODO: Set messages to display in center of screen
        String toastText = "HELLO";
        Toast gameMsg;
        roundData thisRound;
        gameResult roundResult;
        while (roundDataArrayList.size() > 0) {
            float cash = 0;
            thisRound = roundDataArrayList.remove(0);
            roundResult = checkWin(thisRound);
            switch (roundResult) {
                case BLACKJACK:
                    cash = (float) (thisRound.getBet() * 1.5);
                    player.addToBalance(cash);
                    gameMsg = Toast.makeText(activityGameBlackJack.this, "Player BlackJack", Toast.LENGTH_SHORT);
                    gameMsg.setGravity(Gravity.CENTER, 0, 0);
                    gameMsg.show();
                    break;
                case WIN:
                    cash = thisRound.getBet();
                    player.addToBalance(cash);
                    gameMsg = Toast.makeText(activityGameBlackJack.this, "Player Win", Toast.LENGTH_SHORT);
                    gameMsg.setGravity(Gravity.CENTER, 0, 0);
                    gameMsg.show();
                    break;
                case TIE:
                    gameMsg = Toast.makeText(activityGameBlackJack.this, "Tie", Toast.LENGTH_SHORT);
                    gameMsg.setGravity(Gravity.CENTER, 0, 0);
                    gameMsg.show();
                    break;
                case LOSE:
                    cash = thisRound.getBet()*-1;
                    player.addToBalance(cash);
                    gameMsg = Toast.makeText(activityGameBlackJack.this, "Dealer Win", Toast.LENGTH_SHORT);
                    gameMsg.setGravity(Gravity.CENTER, 0, 0);
                    gameMsg.show();
                    break;
            }
            sbBet.setMax((int) player.getBalance());
            tvBalance.setText("Balance: $"+player.getBalance()); // Update player balance TextView
        }

        sbBet.setEnabled(true);
        //updateScore();

        if (player.getBalance() < minBet)
            gameOver();
    }

    private void saveRound() {
        currRoundData.setHandTotal(player.getHand().getHandValue());
        currRoundData.setBet(currentBet);
        roundDataArrayList.add(currRoundData);
    }

    private void dealerPlay() {
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
        while (dealer.getHand().getHandValue() < 17) {
            dealer.dealCard(dealer, deck);
            setImageResource('d',dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        }
        dealerPlayed = true;
        updateScore();
    }

    private void hit() {
        // Deal card
        dealer.dealCard(player, deck);
        updateScore();
        if (!playingSplit)
            setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        else
            setImageResource('s',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
    }

    private void splitHand() {
        /* Splits current hand:
         * Calls player.splitHand() which removes the second card in the player current hand
         * and adds it to a new hand, and updates game display.
         * Note that player.splitHand() returns the card that was split
         * A new card is dealt to the current hand at the close of the function
         */
        DeckHandler.Card splitCard;
        splitCard = player.splitHand();
        playerCardImages[1].setAlpha(transparent);
        setImageResource('s',1, splitCard.getImageSource());
        // Deal to current hand after split
        hit();
    }

    private gameResult checkWin(roundData thisRound) {
        /* Function checks for win conditions on current hand values
         * Checks for Player BlackJack and pays 1.5x bet amount, returns BLACKJACK
         * If player busts, returns LOSE
         * Assuming player did not bust and either 1)Dealer busted or 2)Player has higher hand value, returns WIN
         * Returns TIE in the case of a push
         * Returns LOSE if all above cases are false (eg player hand < dealer hand)
         */

        gameResult result;

        int playerHandValue = thisRound.getHandTotal();
        int dealerHandValue = dealer.getHand().getHandValue();
        boolean playerNatural = thisRound.isPlayerNatural();
        boolean dealerNatural = thisRound.isDealerNatural();
        if (playerNatural && !dealerNatural)
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

    private void updateScore() {
        String playerHandValue = "";
        String dealerHandValue = "";
        // Update player Score
        playerHandValue = "Player Score: " + player.getHand().getHandValue();
        if (!playingSplit) {
            tvPlayerScore.setText(playerHandValue);
        } else {
            tvPlayerSplitScore.setAlpha(opaque);
            tvPlayerSplitScore.setText(playerHandValue);
        }
        // Update dealer score
        if (dealerPlayed || currRoundData.isDealerNatural())
            dealerHandValue = "Dealer Score: " + dealer.getHand().getHandValue();
        else
            dealerHandValue = "Dealer Score: " + dealer.getHand().getCard(0).getValue();
        tvDealerScore.setText(dealerHandValue);
    }

    private void gameOver() {
        Toast gameMsg;
        gameMsg = Toast.makeText(activityGameBlackJack.this, "GAME OVER", Toast.LENGTH_LONG);
        gameMsg.setGravity(Gravity.TOP,0,0);
        gameMsg.show();

        String gameOverMsg = "Insufficient Funds";
        tvPlayerScore.setText(gameOverMsg);

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        doubleButton.setEnabled(false);
        splitButton.setEnabled(false);
        confirmButton.setEnabled(false);
    }

}
