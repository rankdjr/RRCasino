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
    private Hand splitHand;

    public Player(String name, Integer funds)
    {
        playerName = name;
        this.hand = new Hand();
        this.balance = funds;
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

    public Hand getSplitHand() { return splitHand; }

    public int getBalance() {
        return balance;
    }

    public void addToBalance(float cash) {
        balance += cash;
    }

    public void addCardToHand(DeckHandler.Card card)
    {
        this.hand.addCard(card);
    }

    public void returnCards(){
        this.hand.clearHand();
    }

    public void splitHand(Hand hand) {
        //DeckHandler.Card nullCard = new DeckHandler.Card(0,0,"b2fv");
        DeckHandler.Card splitCard = hand.getHand().get(0);
        splitHand = new Hand();
        splitHand.addCard(splitCard);
        hand.getHand().remove(1);
    }


/** Daniel's addition**/
                          //0  1  2  3  4  5  6  7  8  9
private int condition [] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int highestHand() {
        int result = 9;
        for(int i=0;i<10;i++)
        {
            if(this.getcondition(i) == 1)
            {
                result = i;
                break;
            }
        }
        return result;
    }
    public int getcondition (int index) {return condition[index];}

    public void setcondition (int index) {condition[index] = 1;}

/**end daniel's addition**/
}
