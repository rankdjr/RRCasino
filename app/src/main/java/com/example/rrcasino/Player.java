package com.example.rrcasino;

/*
 * Created by Doug
 * Encompasses all player related info and functionality
 * intended to track current funds and cards held in player hand
 */

import java.util.ArrayList;

public class Player {
    private int balance;
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

    /** Daniel's addition**/
                              //0  1  2  3  4  5  6  7  8  9
    private int condition [] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public int highestHand() {
        int result = 0;
        for(int i = 0; i<10; i++)
        {
            if(condition[i] == 1)
            {
                result = 1;
                return result;
            }
            System.out.println(i);
            System.out.println("result is");
            System.out.println(result);

        }
        return result;
    }
    public int getcondition (int index) {return condition[index];}

    public void setcondition (int index) {condition[index] = 1;}
    /**end daniel's addition**/

}

