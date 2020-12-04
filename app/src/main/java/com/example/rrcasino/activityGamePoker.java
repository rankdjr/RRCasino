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

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
   // private Button check;
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
    private ImageView compCard;
    private ImageView compCardOne;
    private float transparent = 0;
    private float opaque = 1;

    //Array of Images for player and the table cards
    private ImageView[] playerCardImages;
    private ImageView[] computerCardImages;
    private ImageView[] communityCardImages;

    //Game Variables
    private DeckHandler.Deck deck;
    private PokerDealer dealer;
    private Player player;
    private Player computer;
    private int currentBet;
    private int buyIn = 50;
    private int minBet = buyIn;
    private int computerBet;
    private int potAmount;
    private int round = 0;
    private int startingFunds = 10000;
    private String cardFaceDown = "b2fv";
    private boolean playerInitialBuyin = false;
    private boolean playerBetted = false;
    private boolean computerBetted = false;
    private boolean computerFolds = false;
    private boolean playerFold = false;
    private boolean cardsDealt = false;
    private Hand hand;
    private Hand temp;
    private enum gameResult { WIN, LOSE, TIE }

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
        this.compCard = findViewById(R.id.ccard);
        this.compCardOne = findViewById(R.id.ccard1);
        playerCardImages = new ImageView[] {pcard, pcardone};
        computerCardImages = new ImageView[] {compCard, compCardOne};
        communityCardImages = new ImageView[] {theflop, theflopone, thefloptwo, theturn, theriver};

        //Initialize buttons
        this.fold = findViewById(R.id.fold);
        //this.check = findViewById(R.id.check);
        this.call = findViewById(R.id.call);
        this.bet = findViewById(R.id.bet);
        this.deal = findViewById(R.id.deal);
        fold.setEnabled(false);
       // check.setEnabled(false);
        call.setEnabled(false);
        bet.setEnabled(false);
        betAmount.setEnabled(false);

        //Set button click handlers
        fold.setOnClickListener(clicked);
        //check.setOnClickListener(clicked);
        call.setOnClickListener(clicked);
        bet.setOnClickListener(clicked);
        deal.setOnClickListener(clicked);


        //Initialize New Game
        this.deck = new DeckHandler.Deck();
        this.dealer = new PokerDealer("dealer", 0);
        this.player = new Player("player", startingFunds);
        this.computer = new Player("computer", 10000);
        playerBalance.setText("Balance: $" + player.getBalance());
        playerBet.setMax((int)player.getBalance());


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
                    cardsDealt = true;
                    playerBet.setVisibility(View.VISIBLE);
                    betAmount.setVisibility(View.VISIBLE);
                    startGame();
                    //System.out.println(Check_win());
                    System.out.println("DEAL was pressed");
                    break;

                case R.id.bet:
                    playerInitialBuyin = true;
                    playerBetted = true;
                    potAmount += currentBet;
                    pot.setText("$"+potAmount);
                    System.out.println("BET was pressed");
                    System.out.println("In BET player buy in: "+playerInitialBuyin);
                    //checkRounds();
                    break;
                case R.id.fold:
                    playerFold = true;
                    System.out.println("FOLD was pressed");
                    endRound();
                    break;

                case R.id.call:
                    System.out.println("CALL was pressed");
                    checkRounds();
                    //dealTheFlop();
                    break;

                   // endFold();
                    //end the round and update player balance if folded anywhere
                    //after the first buy in



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
                        computerCardImages[cardNum-1].setAlpha(opaque);
                        compCard.setImageResource(resourceId);
                        break;
                    case 2:
                        computerCardImages[cardNum-1].setAlpha(opaque);
                        compCardOne.setImageResource(resourceId);
                        break;

                }
                break;
            }

            case 'd': {
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
        //check.setEnabled(true);
        round++;
        call.setEnabled(true);
        pot.setVisibility(View.VISIBLE);
        // Loop through participant hands and set to all cards to null card
        for (int i = 1; i <= 2; i++) {
            setImageResource('p', i, cardFaceDown);
            playerCardImages[i-1].setAlpha(transparent);

            setImageResource('c', i, cardFaceDown);
            computerCardImages[i-1].setAlpha(transparent);

        }
        //Loop through community table cards and set to NUll
        for(int i = 1; i <= 5; i++) {
            setImageResource('d', i, cardFaceDown);
            communityCardImages[i-1].setAlpha(transparent);
        }
        // Check that hands are empty
        if (player.getHand().getHandValue()>0)
            player.returnCards();
        if (dealer.getHand().getHandValue()>0)
            dealer.returnCards();
        if (computer.getHand().getHandValue()>0)
            computer.returnCards();

        dealPlayerCards();


    }
    //Deal initial player cards
    private void dealPlayerCards() {
        // Deal First Player Card
        dealer.dealCard(player, deck);
        setImageResource('p',player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

        // Deal Computer First card
        dealer.dealCard(computer,deck);
        setImageResource('c', computer.getHand().getNumOfCardsInHand(), cardFaceDown);

        //Deal Second player card
        dealer.dealCard(player,deck);
        setImageResource('p', player.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

        //Deal Second computer card
        dealer.dealCard(computer, deck);
        setImageResource('c', computer.getHand().getNumOfCardsInHand(), cardFaceDown);

        //Dealing all community cards to test CheckWin() function
        dealer.dealCard(dealer,deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer,deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer,deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer,deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
        dealer.dealCard(dealer,deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

    }

    //Functions to deal out cards. The flop initial deal of three cards, the turn deals one card, and the river deals the last card
    //Total of 5 cards laid out on the table

    private void dealTheFlop() {
        //First Flop Card
        dealer.dealCard(dealer, deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(),dealer.getLastDealtCard().getImageSource());
        //Second Flop Card
        dealer.dealCard(dealer, deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(),dealer.getLastDealtCard().getImageSource());
        //Third Flop Card
        dealer.dealCard(dealer, deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(),dealer.getLastDealtCard().getImageSource());
    }

    private void dealTheTurn() {
        //Deal turn card
        dealer.dealCard(dealer, deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());
    }

    private void dealTheRiver() {
        //Deal the river card
        dealer.dealCard(dealer,deck);
        setImageResource('d', dealer.getHand().getNumOfCardsInHand(), dealer.getLastDealtCard().getImageSource());

    }
    private void checkRounds() {

        System.out.println("Entering 'checkRounds'..........");
        System.out.println("Start Game player initial: " + playerInitialBuyin);
        System.out.println("Start Game player bet: " + playerBetted);
        //Check the bettings for the initial round the deal the first three cards
        if(playerBetted && round == 1) {
            System.out.println("About to deal the Flop");
            auto(computer);
            System.out.println("Computer Betted");
            dealTheFlop();
            System.out.println("Flop was dealt");
        }
        //Check the bettings for the flop then deal a card
        else if (playerBetted && round == 2) {
            System.out.println("About to deal the Turn");
            auto(computer);
            dealTheTurn();
        }
        //Check the bettings for the turn then deal a card
        else if (playerBetted && round == 3) {
            System.out.println("About to deal the River");
            auto(computer);
            dealTheRiver();
        }
        //Check the Final bettings
        else if (playerBetted && round == 4)
            auto(computer);

        System.out.println("Current round: "+round);
        System.out.println("Start Game end player bet: " + playerBetted);
        System.out.println("Start Game player buy in: " + playerInitialBuyin);
        //endRound();
        round++;
        playerBetted = false;
        System.out.println("Round amount: "+round);
        if(round == 5) {
            endRound();
        }
    }


    private void endRound () {
        bet.setEnabled(false);
        fold.setEnabled(false);
        call.setEnabled(false);
        //check.setEnabled(false);
        deal.setEnabled(true);
        float cash = 0;
        round = 0;
        System.out.println("In endRound function");
        if(playerFold && !playerInitialBuyin) {
            //Player doesnt lose cash
            player.addToBalance(cash);
            //
        } else {


            switch (Check_win()) {
                case WIN:
                    cash += potAmount;
                    player.addToBalance(cash);
                    break;
                case TIE:
                    break;
                case LOSE:
                    cash -= currentBet;
                    player.addToBalance(cash);
                    break;
            }
            System.out.println(Check_win());
        }

        // Set mew maximum bet; Player cannot bet more than available funds
        //reset booleans
        playerInitialBuyin = false;
        computerFolds = false;
        playerFold = false;
        potAmount = 0;
        System.out.println("end round player buy: " + playerInitialBuyin);
        playerBet.setMax((int)player.getBalance());
        playerBalance.setText("Balance: $"+player.getBalance());
        // Update player balance TextView
    }


    /**Daniel's addition**/
    public void  Check_Hand (Player player) {
        // load hand combining hand
        this.hand = new Hand();
        // load hand for unique value for straight check
        this.temp = new Hand();
        // Flush flag
        int suit [] = {0, 0, 0, 0};
        // pairs flag
        int pairs [] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
        // add players hand to temp hand
        for (int i = 0; i < player.getHand().getNumOfCardsInHand(); i++) {
        hand.addCard(player.getHand().getCard(i));
        }
        // add dealer hand to temp hand
        for (int i = 0; i < dealer.getHand().getNumOfCardsInHand(); i++) {
        hand.addCard(dealer.getHand().getCard(i));
        }
        //sort temp array use quick sort array is small
        insertion_sort(hand);

        //the 3 combine of 5 cards will be check for if flush 0123456
        for(int i=0 ;i<7 ;i++) {
        //   if royal flush
        if (hand.getSuit(i) == 1 ) {
        suit[0]+=1;
        }
        else if (hand.getSuit(i) == 1 ) {
        suit[1]+=1;
        }
        else if (hand.getSuit(i) == 1 ) {
        suit[2]+=1;
        }
        else if (hand.getSuit(i) == 1 ) {
        suit[3]+=1;
        }
        }
        int count = 0;
        temp.addCard(hand.getCard(count));
        if (temp.getCard(0).getRank() == 1){

        }
        for(int i=1;i<7;i++) {
        //if match update array
        if (hand.getCard(i).getValue() == temp.getCard(count).getValue()) {
        if(player.getcondition(8) == 1 && (temp.getCard(count-1).getValue() != temp.getCard(count-2).getValue()))
        {
        player.setcondition(7);
        }
        pairs[hand.getCard(i).getValue()] += 1;
        player.setcondition(8);
        } //if card value != temp value +1 replace temp
        else if(hand.getCard(i).getValue() != temp.getCard(count).getValue() + 1) {
        temp.getHand().set(count, hand.getCard(i));
        }else{
        temp.addCard(hand.getCard(i));
        count++;
        }
        }
        //check flags
        for (int i=0;i<4;i++)
        {
        if (suit[i] >= 5) {
        player.setcondition(4);
        }
        }
        if (temp.getNumOfCardsInHand() >= 5) {
        player.setcondition(5);
        }
        //   if (royal flush)
        if (temp.getAcesInHand() == 1 && temp.getCard(1).getRank() == 10 && temp.getCard(2).getRank() == 11 && temp.getCard(3).getRank() == 12 && temp.getCard(4).getRank() == 13 )
        {
        if (player.getcondition(4) == 1) {
        player.setcondition(0);
        }
        }
        //   if (straight Flush )
        if (player.getcondition(4) == 1 && player.getcondition(5) == 1)
        {
        player.setcondition(1);
        }
        //if (four of a kind)
        this.check_pairs(pairs);

        //else if (Flush all suits are the same )
        if(player.getcondition(4) == 1 && player.getcondition(5) != 1)
        {
        player.setcondition(4);
        }
        //   else if (straight)
        if(player.getcondition(4) != 1 && player.getcondition(5) == 1)
        {
        player.setcondition(5);
        }
        }

        private void check_pairs(int[] pairs) {
        for(int i=12;i>0;i--)
        {
        if (pairs[i] == 3) {
        player.setcondition(2);
        } else if (pairs[i] == 2) {
        player.setcondition(6);
        } else if (pairs[i] == 1) {
        player.setcondition(8);
        }
        }
        if (player.getcondition(6) == 1 && player.getcondition(8) == 1 ) {
        player.setcondition(3);
        }

        }
        private void insertion_sort(Hand hand)
        {
        int n = player.getHand().getNumOfCardsInHand();
        for(int i=1;i<n-1;i++)
        {
        DeckHandler.Card v = hand.getCard(i);
        int j = i - 1;
        while (j>=0 & hand.getCard(j).getValue() > v.getValue())
        {
        hand.getHand().set(j+1, hand.getHand().get(j));
        j = j -1;
        hand.getHand().set(j+1, v);
        }

        }
        }

        private void auto (Player player) {
            this.Check_Hand(player);
            if (player.highestHand() >= 5) {
                // raise
                //just bet the min amount
                computerBet = minBet;
                potAmount += computerBet;
                computerBetted = true;
            } else if (player.highestHand() < 5 && player.highestHand() > 8) {
                //call
                //if player betted
                //set the bet to that amount
                if(playerBetted) {
                    computerBet = currentBet;
                    potAmount += computerBet;
                    computerBetted = true;
                }

            }else{
                //fold
                //Just end the round
                //endround() as player wins
                computerFolds = true;
                endRound();
            }

            playerBetted = false;

        }

private gameResult Check_win () {
        gameResult result = activityGamePoker.gameResult.WIN;
        //2. compare highest win condition
        //player wins
        if (player.highestHand() < computer.highestHand()) {
        result = activityGamePoker.gameResult.WIN;
        return result;
        //player loses
        } else if (player.highestHand() > computer.highestHand()) {
        result = activityGamePoker.gameResult.LOSE;
        return result;
        // player ties
        } else if (player.highestHand() == computer.highestHand()) {
        result = activityGamePoker.gameResult.TIE;
        //check strong hand if tie
        if (player.getHand().getHandValue() > computer.getHand().getHandValue()) {
        result = activityGamePoker.gameResult.WIN;
        return result;
        //player loses
        } else if (player.getHand().getHandValue() < computer.getHand().getHandValue()) {
        result = activityGamePoker.gameResult.LOSE;
        return result;
        // player ties
        } else if (player.getHand().getHandValue() == computer.getHand().getHandValue()) {
        result = activityGamePoker.gameResult.TIE;
        //check kicker
        if (player.getHand().getCard(0).getValue() > (computer.getHand().getCard(0).getValue() | computer.getHand().getCard(1).getValue())) {
        result = activityGamePoker.gameResult.WIN;
        return result;
        //player loses
        } else if (player.getHand().getCard(1).getValue() > (computer.getHand().getCard(0).getValue() | computer.getHand().getCard(1).getValue())) {
        result = activityGamePoker.gameResult.WIN;
        return result;
        // player ties
        } else if (player.getHand().getHandValue() == computer.getHand().getHandValue()) {
        result = activityGamePoker.gameResult.TIE;
        return result;
        } else {
        result = activityGamePoker.gameResult.LOSE;
        }
        }
        }
        return result;
        }
        }
