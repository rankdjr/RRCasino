package com.example.rrcasino;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

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
        /*
        int suit, value, discarded;

        //If the card is discarded, discarded is 1 or greater.
        public void create()
        {
            value = (int)Math.random() * (14-1+1)+1;
            suit = (int)Math.random() * (5-1+1)+1;
            discarded = 0;
        }
        */

        // *********** Doug's additions start here *********************
        int suit;
        int rank;
        public Card(int suit, int rank)
        {
            this.suit = suit;
            this.rank = rank;
        }


    }
    public class Deck
    {
        /*
        Card deck[];

        public void create()
        {
            for (int i = 0; i < 52; i++)
            {
                //deck[i] = new Card();
            }
        }
        */

        // *********** Doug's additions start here *********************
        private ArrayList<Card> cards;

        public Deck()
        {
            cards = new ArrayList<>();
            generateDeck();
            shuffle();
        }

        public void generateDeck()
        {
            //while loop iterates 52 times, the iterator is incremented on the innermost for loop
            //generating the deck creates and ordered deck in descending order from ace to 2
            int i = 0;
            while (i < 52) {
                for (int suit = 0; suit < 4; suit++) {
                    for (int rank = 0; rank < 13; rank++) {
                        cards.add(new Card(suit, rank));
                        i++;
                    }
                }
            }
        }

        public void shuffle()
        {
            Collections.shuffle(cards);
        }

        public Card getCard()
        {
            return cards.remove(0);
        }

        /*
        public int getNumberOfCards()
        {
            return cards.size();
        }
        */
    }

    public class Shoe
    {
        private ArrayList<Card> shoe;
        private int numOfDecks;

        public Shoe()
        {
            numOfDecks = 8;
            shoe = new ArrayList<>();
            generateShoe();
        }

        public void generateShoe()
        {
            //while loop iterates 52 times with a repetition based on the number of decks wanted in the shoe
            //the iterator is incremented on the innermost for loop
            //generating the shoe creates an ordered stack of decks in descending order from ace to 2
            int i = 0;
            while (i < 52 * numOfDecks) {
                for (int suit = 0; suit < 4; suit++) {
                    for (int rank = 0; rank < 13; rank++) {
                        shoe.add(new Card(suit, rank));
                        i++;
                    }
                }
            }
        }

        public void shuffle()
        {
            Collections.shuffle(shoe);
        }

        public Card getCard()
        {
            return shoe.remove(0);
        }

        public int getNumOfCards()
        {
            return shoe.size();
        }
    }


}
