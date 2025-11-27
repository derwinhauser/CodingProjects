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
        Deck shoe = new Deck(4); // How many Decks is the shoe?
        int unitSize = 0; //enter base unit
        int betSize = 0;
        double trueCount = 0;
        Deck discardTray = new Deck(0); // Create discard tray object
        int runningCount = 0;
        Hand playerCards = new Hand();
        Hand dealerCards = new Hand();
        Hand tableCards = new Hand();
        int playerTotal = 0;
        int dealerTotal = 0;
        boolean playerTakesInsurance = false;
        double totalBet = 0.0;
        
        // start of playing hands
        while (shoe.getNumberOfDecks()>1.5){ // shuffle occurs at 1.5 decks remaining in the shoe
            // First: determine truecount
            trueCount = getTrueCount(shoe, discardTray, tableCards);
            
            // Second: determine betsize
            betSize = getBetSize(trueCount);
            totalBet = betSize;
            
            // Third: deal initial 4 cards
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
            System.out.print("Revealed dealer cards: ");
            dealerCards.printHand();
            playerTotal = playerCards.totalHand();
            System.out.println("Player total: " + playerTotal);
            // end debug/display

            // fourth: Determine true count after hands are dealt
            trueCount = getTrueCount(shoe, discardTray, tableCards);
            System.out.println("True Count: " + trueCount); // debug

            // fifth: check for ace as dealer upcard and ask for insurance
            if (dealerCards.dealerUpCard().equals("A")){
                System.out.println("Dealer showing Ace. Take insurance?");// debug
                if (trueCount>=3){
                    System.out.println("---------------*****!!!!!Player takes insurance!!!!!!*****");// debug
                    playerTakesInsurance = true;
                    totalBet = totalBet + (.5*betSize);
                }
                else{
                    System.out.println("Player does not take insurance");// debug
                    playerTakesInsurance = false;
                }
            }
            else{
                System.out.println("No ace being shown");
                playerTakesInsurance = false;
            }
            System.out.println("Total Bet: " + totalBet);// debug
            // sixth: determine player's cards, then decide action based on dealer's upcard.
            

            


            // final step: discard all tabled cards
            playerCards.clearCards();
            dealerCards.clearCards();
            discardTray = discardTableCards(tableCards, discardTray);
            tableCards.clearCards();

            System.out.println();// debug
        }
    }


    public static double getTrueCount(Deck shoe, Deck discardTray, Hand tableCards){
        int runningCount = discardTray.getRunningCount()+tableCards.getTableCount();
        double remainingDecks = shoe.getNumberOfDecks();
        double trueCount = runningCount/remainingDecks;
        // round to two decimals
        trueCount = trueCount*100;
        trueCount = Math.round(trueCount);
        trueCount = trueCount/100;
        return trueCount;
    }

    public static Deck discardTableCards(Hand tableCards, Deck discardTray){
        for (int i=0; i<tableCards.getHandSize(); i++){
            discardTray.addCard(tableCards.getCard(i));
        }
        return discardTray;
    }
    
    public static int getBetSize(double trueCount){
        int betSize = 0;
        if (trueCount<1){
            betSize = 3;
        }
        else if(trueCount<2){
            betSize = 10;
        }
        else if(trueCount<3){
            betSize = 15;
        }
        else if (trueCount<4){
            betSize = 25;
        }
        else{
            betSize = 50;
        }
        return betSize;
    }

}