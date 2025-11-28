/*
* TODO:
* 
* create function that determines if dealer is showing an ace and returns boolean public static boolean dealerShowsAce(Hand dealerCards){}
* ask player for even money (determine if they should take it based on count)
*  
* subtract money from bankroll for each bet/bet increase
*
* Steps in order:
* DO: determine double bankroll
* DO: numberOfPlayerHands = 1
* Done: Determine Player bet Size
* DO: Subtract player bet size from bankroll
* Done: Deal initial four cards
* Done: boolean dealerShowsAce
* Done: boolean playerHasBlackjack
* Done: boolean dealerHasBlackjack
* Do: If dealerShowsAce = true and playerHasBlackjack = true, determine if player takes even money based on true count
* DO: If dealerShowsAce = true and playerHasBlackjack = false, (playerTakesInsurance = true if trueCount >= 3)
* DO: Check for Dealer blackjack and determine if insurance is payed out; if dealerHasBlackjack = true and playerTakesInsurance = true, return full bet to player and end hand
* DO: if dealerHasBlackjack = true and playerTakesInsurance = false, continue hand as normal and only consider original bet for payout
* 
* DO: check for Splits/doubles
* 
* DO: loop for Hand.winLossTie function that iterates over ArrayList <Hand> playerHands and determines payouts and clears their hands/bet sizes
* DO: clear the following: playerCards, dealerCards, tableCards, playerHands
*
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class BlackJack{

    public static void main(String[] args) throws IOException{
        // initiate variables
        double bankroll = 0; // What is the starting bankroll?
        
        boolean dealerShowsAce;
        boolean playerHasBlackjack;
        boolean dealerHasBlackjack;
        boolean playerTakesEvenMoney;
        boolean playerTakesInsurance;
        int betSize;
        int runningCount;
        double trueCount;
        int playerTotal;
        int dealerTotal;
        double totalBet;
        Deck shoe = new Deck(4); // How many Decks is the shoe?
        Deck discardTray = new Deck(0); // Create discard tray object (must stay 0)
        Hand playerCards = new Hand();
        Hand playerCards2 = new Hand(); // extra hands are used if player splits
        Hand playerCards3 = new Hand();
        Hand playerCards4 = new Hand();
        int numberOfPlayerHands = 1; // increases if player splits their hand (max 4 total hands)
        Hand dealerCards = new Hand();
        Hand tableCards = new Hand();
        ArrayList <Hand> playerHands = new ArrayList <Hand>(playerCards, playerCards2, playerCards3, playerCards4); // useful for determining all hands at end
        
        // start of playing hands
        while (shoe.getNumberOfDecks()>1.5){ // shuffle occurs at 1.5 decks remaining in the shoe
            // determine truecount
            trueCount = getTrueCount(shoe, discardTray, tableCards);
            
            // determine betsize
            betSize = getBetSize(trueCount);
            totalBet = betSize;
            
            // deal initial 4 cards
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
            dealerShowsAce = dealerShowsAce(dealerCards);
            playerHasBlackjack = blackjackCheck(playerCards);
            dealerHasBlackjack = blackjackCheck(dealerCards);
            if (dealerShowsAce && !playerHasBlackjack){
                playerTakesInsurance = playerTakesInsurance(playerCards);
            }
            else{
                playerTakesInsurance = false;
            }
            if (dealerShowsAce && playerHasBlackjack){
                playerTakesEvenMoney = playerTakesEvenMoney(trueCount);
            }
            else{
                playerTakesEvenMoney = false;
            }
            if(playerTakesEvenMoney){
                // payout player
            }
            if (playerTakesInsurance && dealerHasBlackjack){
                // TODO: player takes back total bet and game loop restarts
            }

            /* 
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
            */
           
            // sixth: determine player's cards, then decide action based on dealer's upcard.
            

            


            // final step: discard all tabled cards
            playerCards.clearCards();
            playerCards2.clearCards();
            playerCards3.clearCards();
            playerCards4.clearCards();
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

    public static boolean dealerShowsAce(Hand dealerCards){
        if(dealerCards.dealerUpCard().equals("A")){
            return true;
            }
        else{
            return false;
        }
    }

    public static boolean playerTakesInsurance(double trueCount){
        if (trueCount>=3){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean blackjackCheck(Hand playerCards){
        if (playerCards.totalHand()==21){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean playerTakesEvenMoney(double trueCount){
        if (trueCount>=3){
            return true;
        }
        else{
            return false;
        }
    }

}