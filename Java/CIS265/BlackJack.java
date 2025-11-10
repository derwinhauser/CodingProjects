/*
TODO:
Code a simple 4 deck blackjack game
Add bankroll and bet sizings
Add split/double down
Add running and true count for counting cards
Add agent that corrects incorrect moves by the player

 */

import java.util.Scanner;
import java.util.ArrayList;

public class BlackJack{

    public static void main(String[] args){
        // Create Deck of Cards
        ArrayList <Integer> deck = create_deck();
        
        // Deal cards
        ArrayList <Integer> dealer_cards = deal_cards(deck);
        ArrayList <Integer> player_cards = deal_cards(deck);
        System.out.println("Dealer cards: " + dealer_cards);
        System.out.println("Player cards: " + player_cards);
        // sum two cards for player
        sum_cards(dealer_cards);
        sum_cards(player_cards);
        // User inputs (playing the game)

        // Determine win/loss

        
    }

    public static ArrayList <Integer> create_deck(){
        // declare variables
        ArrayList <Integer> deck = new ArrayList <Integer> ();

        // One quarter deck creation
        for(int j=1; j<=13; j++){
            if(j>=10){
                deck.add(10);
            }
            else{
                deck.add(j);
            }
        }
        
        return deck;
    }

    public static ArrayList <Integer> deal_cards(ArrayList <Integer> deck){
        ArrayList <Integer> cards = new ArrayList <Integer> ();
        int size = deck.size();
        for (int i=1; i<=2; i++){
            double temp = (size)*Math.random();
            int card = (int) temp;
            cards.add(deck.get(card));
        }
        return cards;
    }

    public static void sum_cards(ArrayList <Integer> cards){
        int temp=0;
        for (int i = 0; i<cards.size(); i++){
            temp = temp+cards.get(i);
        } 
        System.out.println("Total: " + temp);
    }

}