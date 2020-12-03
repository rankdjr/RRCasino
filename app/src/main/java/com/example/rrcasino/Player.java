package com.example.rrcasino;

/*
 * Created by Doug
 * Encompasses all player related info and functionality
 * intended to track current funds and cards held in player hand
 */

import java.util.ArrayList;

public class Player {
    private double balance;
    private String playerName;
    private Hand hand;
    private ArrayList<Hand> splitHands;
    private int numOfHandsInPlay;

    public Player(String name, Integer funds)
    {
        playerName = name;
        this.balance = funds;
        this.hand = new Hand();
        splitHands = new ArrayList<>();
        numOfHandsInPlay = 1;
    }

    public void setPlayerName(String name)
    {
        playerName = name;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand newHand) {
        this.hand = newHand;
    }

    public int getNumOfHandsInPlay() { return numOfHandsInPlay; }

    public void setNumOfHandsInPlay() { numOfHandsInPlay--; }

    public Hand getNextHand() {
        numOfHandsInPlay--;
        return splitHands.remove(0);
    }

    public DeckHandler.Card splitHand() {
        splitHands.add(new Hand());
        DeckHandler.Card splitCard = this.hand.getHand().remove(1);
        splitHands.get(0).addCard(splitCard);
        numOfHandsInPlay++;
        return splitCard;
    }

    public double getBalance() {
        return balance;
    }

    public void addToBalance(double cash) {
        balance += cash;
    }

    public void addCardToHand(DeckHandler.Card card)
    {
        this.hand.addCard(card);
    }

    public void returnCards(){
        this.hand.clearHand();
    }

}
