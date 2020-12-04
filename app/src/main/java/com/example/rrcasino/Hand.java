package com.example.rrcasino;

import java.util.ArrayList;

/*
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

    public int getHandPokerValue()
    {
        int total = 0;
        for (DeckHandler.Card currCard : this.hand) {
            total += currCard.getValue();
        }

        return total;
    }

    public int getHandValue()
    {
        /*Calculates values of cards in hand:
         * Sums up values of all cards and reduces values
         * of aces in hand if the total is greater than 21
         */
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

   /*dq*/ public int getSuit (int index) { return this.getCard(index).getSuit();}

    public int getNumOfCardsInHand()
    {
        return this.hand.size();
    }

    public int getAcesInHand()
    {
        return acesInHand;
    }

    public void addCard(DeckHandler.Card card)
    {
        this.hand.add(card);
        if (card.getRank() == 1)
            acesInHand++;
    }



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
