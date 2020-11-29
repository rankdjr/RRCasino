package com.example.rrcasino;

/**
 * Created by Doug
 *
 */

public class gameBlackJack {
    private DeckHandler.Shoe shoe;
    private Dealer dealer;
    private Player player;

    public gameBlackJack(DeckHandler.Shoe shoe)
    {
        this.shoe = shoe;
        this.dealer = new Dealer("DEALER", 0);
        this.player = new Player("Player 1", 0);

    }




}
