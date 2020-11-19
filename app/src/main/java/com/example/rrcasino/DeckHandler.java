package com.example.rrcasino;
//Created By Kanayo Emenike
/*Description: This file will handle all card based inquiries.
  This includes:
    - Creating the cards and decks.
    - Shuffling the cards.
    - Maintaining the used cards in the decks.
 */
public class DeckHandler
{
    public class Card
    {
        int suit, value, discarded;
        //If the card is discarded, discarded is 1 or greater.
        public void create()
        {
            value = (int)Math.random() * (14-1+1)+1;
            suit = (int)Math.random() * (5-1+1)+1;
            discarded = 0;
        }
    }
    public class Deck
    {
        Card deck[];

        public void create()
        {
            for (int i = 0; i < 52; i++)
            {
                deck[i] = new Card();
            }
        }


    }
    public class Shoe
    {

    }


}
