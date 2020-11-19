package com.example.rrcasino;
/**
 * Created by Doug
 * Encompasses all player related info and functionality
 * intended to track current funds and cards held in player hand
 */
public class Player {
    private int balance;
    private String playerName;
    private Hand hand;

    public Player()
    {
        /*
            initialize all starting values
         */
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

    public int getBalance() {
        return balance;
    }

    public void addCardToHand(DeckHandler.Card card)
    {
        this.hand.addCard(card);
    }

    public int getPlayerHandValue() {
        return this.hand.getHandValue();
    }

    public void returnCards(){
        this.hand.clearHand();
    }
}
