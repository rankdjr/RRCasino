package com.example.rrcasino;
/**
 * Created by Doug
 * Encompasses all player related info and functionality
 * intended to track current funds and cards held in player hand
 */
public class Dealer extends Player {

    public Dealer(String name, Integer funds) {
        super(name, funds);
    }
    public void dealCard(Player player, DeckHandler.Shoe shoe) {
        DeckHandler.Card dealtCard = shoe.getCard();
        player.addCardToHand(dealtCard);
    }

}