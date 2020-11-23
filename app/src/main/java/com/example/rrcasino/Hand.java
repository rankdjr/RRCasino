package com.example.rrcasino;

import java.util.ArrayList;

/**
 * Created by Doug
 * Encompasses all data for cards held in any hand
 * Functions return values of cards held in hand, as well as add and remove cards from hand
 */

public class Hand {
    // *** Class Variables ***
    private ArrayList<DeckHandler.Card> hand;
    private int acesInHand;

    // *** Class Functions ***
    public Hand()
    {
        this.hand = new ArrayList<>();
        acesInHand = 0;
    }

    public ArrayList<DeckHandler.Card> getHand()
    {
        return hand;
    }

    public int getHandValue()
    {
        int total = 0;
        int tempAceCtr = acesInHand;
        for (DeckHandler.Card currCard : this.hand) {
            total += currCard.getValue();
        }

        while (tempAceCtr > 0 && total > 21) {
            total -= 10;
            tempAceCtr--;
        }

        return total;
    }

    public int getNumOfCardsInHand()
    {
        return this.hand.size();
    }

    public void addCard(DeckHandler.Card card)
    {
        this.hand.add(card);
        if (card.getRank() == 1)
            acesInHand++;
    }

    /*
    add function to remove selected card to make class work for poker game river functionality
     */
    public DeckHandler.Card getCard(Integer index)
    {
        return hand.get(index);
    }

    public boolean checkBust()
    {
        if (getHandValue() > 21)
            return true;
        return false;
    }

    public void clearHand()
    {
        hand.clear();
        acesInHand = 0;
    }
}
