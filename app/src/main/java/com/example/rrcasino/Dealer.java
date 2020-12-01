package com.example.rrcasino;

/*
 * Created by Doug
 * Encompasses all player related info and functionality
 * intended to track current funds and cards held in player hand
 */

public class Dealer extends Player {
    private DeckHandler.Card lastDealtCard;
    private Hand hand;

    public Dealer(String name, Integer funds) {
        super(name, funds);
        this.hand = new Hand();
    }

    public void dealCard(Player player, DeckHandler.Shoe shoe) {
        lastDealtCard = shoe.getCard();
        player.addCardToHand(lastDealtCard);
    }

    public DeckHandler.Card getLastDealtCard() { return lastDealtCard; }

}
