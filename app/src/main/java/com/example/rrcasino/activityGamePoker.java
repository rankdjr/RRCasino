package com.example.rrcasino;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
    private Player computer;
    private int currentBet;
    private int buyIn = 50;
    private int minBet = buyIn;
    private int startingFunds = 10000;
    private String cardFaceDown = "b2fv";
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
        playerCardImages = new ImageView[]{pcard, pcardone};
        communityCardImages = new ImageView[]{theflop, theflopone, thefloptwo, theturn, theriver};

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

    }

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

        private void auto () {

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
