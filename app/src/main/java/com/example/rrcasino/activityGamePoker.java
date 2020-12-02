package com.example.rrcasino;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class activityGamePoker extends AppCompatActivity {
    //Custom popup for help button
    Dialog myDialog;
    //Seek Bar
    private SeekBar playerBet;

    //Text
    private TextView betAmount;
    private TextView pot;
    private TextView playerBalance;

    //Buttons
    private Button check;
    private Button call;
    private Button bet;
    private Button fold;
    private Button deal;

    //Image Views for community cards and player cards
    private ImageView theflop;
    private ImageView theflopone;
    private ImageView thefloptwo;
    private ImageView theturn;
    private ImageView theriver;
    private ImageView pcard;
    private ImageView pcardone;
    private float transparent = 0;
    private float opaque = 1;

    //Array of Images for player and the table cards
    private ImageView[] playerCardImages;
    private ImageView[] communityCardImages;

    //Game Variables
    private DeckHandler.Deck deck;
    private PokerDealer dealer;
    private Player player;
    private int currentBet;
    private int buyIn = 50;
    private int minBet = buyIn;
    private int startingFunds = 10000;
    private String cardFaceDown = "b2fv";


    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_poker);
        //Initialize dialog
        this.myDialog = new Dialog(this);


        //Initialize Seekbar
        this.playerBet = findViewById(R.id.playerBet);
        playerBet.setMin(minBet);
        playerBet.setMax(100);
        playerBet.setProgress(minBet);

        //Text initialized
        this.betAmount = findViewById(R.id.betAmount);
        this.pot = findViewById(R.id.pot);
        this.playerBalance = findViewById(R.id.playerBalance);

        //Image views initialized
        this.theflop = findViewById(R.id.theflop);
        this.theflopone = findViewById(R.id.theflopone);
        this.thefloptwo = findViewById(R.id.thefloptwo);
        this.theturn = findViewById(R.id.theturn);
        this.theriver = findViewById(R.id.theriver);
        this.pcard = findViewById(R.id.pcard);
        this.pcardone = findViewById(R.id.pcardone);
        playerCardImages = new ImageView[] {pcard, pcardone};
        communityCardImages = new ImageView[] {theflop, theflopone, thefloptwo, theturn, theriver};

        //Initialize buttons
        this.fold = findViewById(R.id.fold);
        this.check = findViewById(R.id.check);
        this.call = findViewById(R.id.call);
        this.bet = findViewById(R.id.bet);
        this.deal = findViewById(R.id.deal);
        fold.setEnabled(false);
        check.setEnabled(false);
        call.setEnabled(false);
        bet.setEnabled(false);
        betAmount.setEnabled(false);

        //Set button click handlers
        fold.setOnClickListener(clicked);
        check.setOnClickListener(clicked);
        call.setOnClickListener(clicked);
        bet.setOnClickListener(clicked);
        deal.setOnClickListener(clicked);


        //Initialize New Game
        this.deck = new DeckHandler.Deck();
        this.dealer = new PokerDealer("dealer", 0);
        this.player = new Player("player", startingFunds);
        playerBalance.setText("Balance: $" + player.getBalance());
        playerBet.setMax(player.getBalance());


        //Handle All Listeners

        playerBet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                betAmount.setText("$"+progressChangedValue);
                bet.setText("BET");
                if(progressChangedValue == player.getBalance()){
                    bet.setText("ALL IN");
                }

                //System.out.println(progressChangedValue);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentBet = progressChangedValue;

            }
        });

    }
    private View.OnClickListener clicked = new View.OnClickListener() {
        /*
         * Onclick listener for buttons
         *
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.deal:
                    //Disable the deal cards button
                    deal.setEnabled(false);
                    startGame();

                case R.id.bet:
                    betAmount.setVisibility(View.VISIBLE);
                    playerBet.setVisibility(View.VISIBLE);
                    pot.setText("$"+currentBet);
                    dealTheFlop();
                    dealTheTurn();

            }

        }
    };

    //Show Help Popup
    public void ShowPopup(View v) {
        FloatingActionButton closepopup;
        myDialog.setContentView(R.layout.custompopup);
        closepopup = myDialog.findViewById(R.id.closepopup);
        closepopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

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
                        pcard.setImageResource(resourceId);
                        break;
                    case 2:
                        playerCardImages[cardNum-1].setAlpha(opaque);
                        pcardone.setImageResource(resourceId);
                        break;

                }
                break;
            }
            case 'c': {
                switch (cardNum) {
                    case 1:
                        communityCardImages[cardNum-1].setAlpha(opaque);
                        theflop.setImageResource(resourceId);
                        break;
                    case 2:
                        communityCardImages[cardNum-1].setAlpha(opaque);
                        theflopone.setImageResource(resourceId);
                        break;
                    case 3:
                        communityCardImages[cardNum-1].setAlpha(opaque);
                        thefloptwo.setImageResource(resourceId);
                        break;
                    case 4:
                        communityCardImages[cardNum-1].setAlpha(opaque);
                        theturn.setImageResource(resourceId);
                        break;
                    case 5:
                        communityCardImages[cardNum-1].setAlpha(opaque);
                        theriver.setImageResource(resourceId);
                        break;
                }
                break;
            }
        }
    }
    //Start the game
    private void startGame() {
        /* Create clean round
         * 1. Disable/Enable buttons
         * 2. Check that players hands are empty
         * 3. Update card images to face down, and make non-dealt cards transparent
         * 4. Enable SeekBar to set new bet amount
         * 5. Deal cards, and check for natural win
         */

        // Update usable buttons
        bet.setEnabled(true);
        fold.setEnabled(true);
        check.setEnabled(true);
        call.setEnabled(true);
        pot.setVisibility(View.VISIBLE);
        // Loop through participant hands and set to all cards to null card
        for (int i = 1; i <= 2; i++) {
            setImageResource('p', i, cardFaceDown);
            playerCardImages[i-1].setAlpha(transparent);

        }
        //Loop through community table cards and set to NUll
        for(int i = 1; i <= 5; i++) {
            setImageResource('c', i, cardFaceDown);
            communityCardImages[i-1].setAlpha(transparent);
        }
        // Check that hands are empty
        if (player.getHand().getHandValue()>0)
            player.returnCards();
        if (dealer.getHand().getHandValue()>0)
            dealer.returnCards();

        dealPlayerCards();
    }
    //Deal initial player cards
    private void dealPlayerCards() {
        // First Player Card
        dealer.dealCard(player, deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

        // Deal Player Second card
        dealer.dealCard(player,deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

    }

    //Functions to deal out cards. The flop initial deal of three cards, the turn deals one card, and the river deals the last card
    //Total of 5 cards laid out on the table

    private void dealTheFlop() {
        //First Flop Card
        dealer.dealCard(dealer, deck);
        setImageResource('c', 1,dealer.getLastDealtCard().getImageSource());
        //Second Flop Card
        dealer.dealCard(dealer, deck);
        setImageResource('c', 2,dealer.getLastDealtCard().getImageSource());
        //Third Flop Card
        dealer.dealCard(dealer, deck);
        setImageResource('c', 3,dealer.getLastDealtCard().getImageSource());
    }

    private void dealTheTurn() {
        //Deal turn card
        dealer.dealCard(dealer, deck);
        setImageResource('c', 4, dealer.getLastDealtCard().getImageSource());
    }
    /*
    private void dealTheRiver() {
        //Deal the river card
        dealer.dealCard(dealer,deck);
        setImageResource('c', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

    }

    */
    private void checkInitialBuyIn() {
        /*
            *Start with user player for initial buy in
            * if fold - end round(remove player from players array)
            * if check- go to next player for evaluating(will actually be disabled here)
            * if call - update pot with initial buy and also player balance
            * if bet on initial - min bet is inital buy in, update pot and next player call
            * is inital buy in + previousBet
            * once complete - deal the flop cards
            *
            * Next check again
            * then deal the turn once complete
            *
            * Check again
            * and finally deal the river card once complete
            *
            * Finally check for final bets,calls,folds,and checks
            * Reveal the winner by checking the hand
            * with community cards for best hand
            *

         */
        //Used to see if players bought in or not
        int previousBet = 0;
        boolean done = false;
        previousBet = currentBet;

    }
}
