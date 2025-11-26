/*
* TODO:
* bankroll
* establish bet size
*
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class BlackJack{

    public static void main(String[] args) throws IOException{
        // initiate variables
        int bankroll = 0; // What is the starting bankroll?
        Deck shoe = new Deck(1); // How many Decks is the shoe?
        int betSize = 0;
        double trueCount = 0;
        Deck discardTray = new Deck(0); // Create discard tray object
        int runningCount = 0;
        Hand playerCards = new Hand();
        Hand dealerCards = new Hand();
        Hand tableCards = new Hand();
        int playerTotal = 0;
        int dealerTotal = 0;
        
        // initial deal 4 cards
        for (int i=0; i<4; i++){
            // variables
            int shoeSize = shoe.getShoeSize();
            int randInt = (int) (Math.random()*shoeSize);
            Card tempCard = shoe.getCard(randInt);
            
            // remove card from shoe
            shoe.removeCard(randInt);
            
            // give removed card to player or dealer 
            if(i%2==0){
                playerCards.addCard(tempCard);
            }
            else{
                dealerCards.addCard(tempCard);
            }
            //add cards to tableCards variable to track running count without interference from dealer's down card
            tableCards.addCard(tempCard);
        }

        // debug/display
        System.out.print("Dealer Cards: ");
        dealerCards.printDealerHand();
        System.out.print("Player Cards: ");
        playerCards.printHand();
        runningCount = discardTray.getRunningCount() + tableCards.getTableCount();
        System.out.println("Running Count: " + runningCount);
        System.out.println("Revealed dealer cards: ");
        dealerCards.printHand();
        playerTotal = playerCards.totalHand();
        System.out.println("Player total: " + playerTotal);

        // player plays hand according to basic strategy
        trueCount = getTrueCount(shoe, discardTray, tableCards);
        System.out.println("True Count: " + trueCount);
    }


    public static double getTrueCount(Deck shoe, Deck discardTray, Hand tableCards){
        int runningCount = discardTray.getRunningCount()+tableCards.getTableCount();
        double remainingDecks = shoe.getRemainingDecks();
        double trueCount = runningCount/remainingDecks;
        // round to two decimals
        trueCount = trueCount*100;
        trueCount = Math.round(trueCount);
        trueCount = trueCount/100;
        return trueCount;
    }

}