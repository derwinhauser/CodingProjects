/*
* TODO:
*
*
 */

import java.util.Scanner;
import java.util.ArrayList;

public class BlackJack{

    public static void main(String[] args){
        // initiate variables
        Deck shoe = new Deck(2);
        Deck discardTray = new Deck(0);
        int runningCount = 0;
        Hand playerCards = new Hand();
        Hand dealerCards = new Hand();
        
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
            // preemtively add card to discard tray for running count purposes
            discardTray.addCard(tempCard);
        }
        System.out.print("Dealer Cards: ");
        dealerCards.printDealerHand();
        System.out.print("Player Cards: ");
        playerCards.printHand();
        runningCount = discardTray.getRunningCount();
        System.out.println("Running Count: " + runningCount);
    }


}