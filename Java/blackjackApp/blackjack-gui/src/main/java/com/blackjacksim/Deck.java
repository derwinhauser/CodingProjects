package com.blackjacksim;

import java.util.ArrayList;

public class Deck{
    private ArrayList <Card> deck;
    
    public Deck(int numberOfDecks){
    deck = createDeck(numberOfDecks);
    }

    public void printDeck(){
        for (int i=0; i<deck.size(); i++){
            Card temp = deck.get(i);
            System.out.print(i+1 + ". ");
            temp.printCard();
        }
    }

    public void addCard(Card c){
        deck.add(c);
    }

    public Card getCard(int i){
        return deck.get(i);
    }

    public int getRunningCount(){
        int count = 0;
        for (int i=0; i<deck.size(); i++){
            Card tempCard = deck.get(i);
            count = count+tempCard.getCardCount();
        }
        return count;
    }

    public double getNumberOfDecks(){
        double remainingDecks = (double)deck.size()/52;
        // round to two decimals
        remainingDecks = remainingDecks*100;
        remainingDecks = Math.round(remainingDecks);
        remainingDecks = remainingDecks/100;
        return remainingDecks;
    }

    public void clearCards(){
        deck.clear();
    }

    public void removeCard(int card){
        deck.remove(card);
    }

    public int getShoeSize(){
        return deck.size();
    }

    private ArrayList <Card> createDeck(int numberOfDecks){
        ArrayList <Card> deck = new ArrayList<Card>();
        String suit = "";
        String value = "";
        String symbol = "";
        for (int k=0; k<numberOfDecks; k++){
            for (int i=1; i<=4; i++){
                if (i==1){
                    suit="spades";
                }
                else if(i==2){
                    suit="hearts";
                }
                else if (i==3){
                    suit="clubs";
                }
                else{
                    suit="diamonds";
                }
                for (int j=1; j<=13; j++){
                    int count = 0;
                    if (j==1){
                        value="1";
                        count = -1;
                        symbol = "A";
                    }
                    else if (j==2){
                        value="2";
                        count = 1;
                        symbol = "2";
                    }
                    else if (j==3){
                        value="3";
                        count = 1;
                        symbol = "3";
                    }
                    else if (j==4){
                        value="4";
                        count = 1;
                        symbol = "4";
                    }
                    else if (j==5){
                        value="5";
                        count = 1;
                        symbol = "5";
                    }
                    else if (j==6){
                        value="6";
                        count = 1;
                        symbol = "6";
                    }
                    else if (j==7){
                        value="7";
                        symbol = "7";
                    }
                    else if (j==8){
                        value="8";
                        symbol = "8";
                    }
                    else if (j==9){
                        value="9";
                        symbol = "9";
                    }
                    else if (j==10){
                        value="10";
                        count = -1;
                        symbol = "T";
                    }
                    else if (j==11){
                        value="10";
                        count = -1;
                        symbol = "J";
                    }
                    else if (j==12){
                        value="10";
                        count = -1;
                        symbol = "Q";
                    }
                    else{
                        value="10";
                        count = -1;
                        symbol = "K";
                    }
                    deck.add(new Card(value, suit, count, symbol));
                }
            }
        }
        return deck;
    }
}