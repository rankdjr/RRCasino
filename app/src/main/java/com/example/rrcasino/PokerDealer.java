package com.example.rrcasino;

public class PokerDealer extends Player {

        private DeckHandler.Card lastDealtCard;
        private Hand hand;

        public PokerDealer(String name, Integer funds) {
            super(name, funds);
            this.hand = new Hand();
        }

        public void dealCard(Player player, DeckHandler.Deck deck) {
            lastDealtCard = deck.getCard();
            player.addCardToHand(lastDealtCard);
        }

        public DeckHandler.Card getLastDealtCard() { return lastDealtCard; }



}
