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
        Would this create a random card??
        I could see this causing issues in the scenario that we generate
        a deck and we end up returning duplicates

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
        private int suit;
        private int rank;
        private int value;
        public Card(int suit, int rank)
        {
            this.suit = suit;
            this.rank = rank;
            if (rank < 10)
                value = rank;
            else if (rank == 1)
                value = 11;
            else
                value = 10;
        }

        public int getSuit()
        {
            return suit;
        }

        public int getRank()
        {
            return rank;
        }

        public int getValue()
        {
            return value;
        }
    }
    public class Deck
    {
        /*
        // Commented this out to create a constructor that uses the different version of the card class

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
            // While loop iterates 52 times, creating a new card for each rank of the current suit before moving on to the next
            // suit; The loop iterator(i) is incremented on the innermost for loop
            // The deck is generated with the intention of rank 1 being an Ace and each subsequent rank will follow from 2
            // to K in ascending order
            int i = 0;
            while (i < 52) {
                for (int suit = 1; suit <= 4; suit++) {
                    for (int rank = 1; rank <= 13; rank++) {
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
            // Same as deck generating function but with multiple decks
            // While loop iterates 52 times, creating a new card for each rank of every suit before moving on to the next
            // suit; The loop iterator(i) is incremented on the innermost for loop
            // The deck is generated with the intention of rank 1 being an Ace and each subsequent rank will follow from 2
            // to K in ascending order
            int i = 0;
            while (i < 52 * numOfDecks) {
                for (int suit = 1; suit <= 4; suit++) {
                    for (int rank = 1; rank <= 13; rank++) {
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
