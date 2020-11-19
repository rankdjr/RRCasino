package com.example.rrcasino;
/**
 * Created by Doug
 * Encompasses all player related info and functionality
 * intended to track current funds and cards held in player hand
 */
public class Player {
    private Hand hand;
    private int funds;

    public Player(Integer funds)
    {
        this.funds = funds;
    }

    public Hand getHand() {
        return hand;
    }

    public int getFunds() {
        return funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
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
