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
        Table table = new Table();
        Player player = new Player();

        double bankroll = player.getBankroll(); // What is the starting bankroll?

        boolean dealerShowsAce;
        boolean playerHasBlackjack;
        boolean dealerHasBlackjack;
        boolean playerTakesEvenMoney;
        boolean playerTakesInsurance;
        boolean isSoftTotal;
        boolean playerHasPair;
        int betSize;
        int runningCount;
        double remainingDecks;
        double trueCount;
        int playerTotal;
        int dealerTotal;
        Deck shoe = new Deck(6); // How many Decks is the shoe?
        Deck discardTray = new Deck(0); // Create discard tray object (must stay 0)
        Hand playerCards = new Hand();
        int numberOfHandsPlayed = 0; // number of hands the player has played in total
        int numberOfShoesPlayed = 0;
        int numberOfPlayerHands; // increases if player splits their hand (max 4 total hands)
        Hand dealerCards = new Hand();
        Hand tableCards = new Hand();
        
        // start of new shoe
        while (shoe.getNumberOfDecks()>1.5){ // shuffle occurs at 1.5 decks remaining in the shoe
            // first step: discard all tabled cards
            playerCards.clearCards();
            dealerCards.clearCards();
            
            table.discardCards();
            discardTray = discardTableCards(tableCards, discardTray);
            tableCards.clearCards();

            numberOfPlayerHands = 1;

            System.out.println("Bankroll: "+bankroll); //debug
            numberOfHandsPlayed= numberOfHandsPlayed + 1;
            System.out.println("Number of Hands: " + numberOfHandsPlayed); //debug
            // determine truecount
            runningCount = tableCards.getTableCount()+discardTray.getRunningCount();
            System.out.println("Running Count: " + runningCount); // debug
            remainingDecks = shoe.getNumberOfDecks();
            System.out.println("Reamaining Decks: " + remainingDecks); //debug
            trueCount = getTrueCount(shoe, discardTray, tableCards);
            System.out.println("True Count: " + trueCount); // debug
            
            // determine betsize
            betSize = getBetSize(trueCount);
            System.out.println("betSize: " + betSize); // debug
            
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

            // Start Debug
            System.out.print("Dealer Cards: "); //debug
            dealerCards.printDealerHand(); //debug
            System.out.print("Player Cards: "); //debug
            playerCards.printHand(); //debug
            System.out.print("Revealed dealer cards: "); // debug
            dealerCards.printHand(); //debug
            playerTotal = playerCards.totalHand();
            System.out.println("Player total: " + playerTotal); //debug
            // End Debug

            // fourth: Determine true count after hands are dealt
            runningCount = discardTray.getRunningCount() + tableCards.getTableCount();
            System.out.println("Running Count: " + runningCount); //debug
            remainingDecks = shoe.getNumberOfDecks(); // debug
            System.out.println("Remaining Decks: " + remainingDecks); // debug
            trueCount = getTrueCount(shoe, discardTray, tableCards);
            System.out.println("True Count: " + trueCount); // debug

            // fifth: check for ace as dealer upcard and ask for insurance
            dealerShowsAce = dealerShowsAce(dealerCards);
            /*debug
            if (dealerShowsAce){
                System.out.println("Dealer is showing Ace.");
               break;
            }*/
            playerHasBlackjack = blackjackCheck(playerCards);
            /*debug
            if (playerHasBlackjack){
                System.out.println("Player has blackjack.");
                break;
            }*/
            dealerHasBlackjack = blackjackCheck(dealerCards);
            /* debug
            if (dealerHasBlackjack){
                System.out.println("dealerHasBlackjack");
                break;
            }*/


            if (dealerShowsAce && !playerHasBlackjack){
                playerTakesInsurance = playerTakesInsurance(trueCount);
                /* System.out.println("Dealer is showing ace and player does not have blackjack");//debug
                break; //debug*/
            }

            else{
                playerTakesInsurance = false;
            }

            if (playerTakesInsurance){ //debug
                System.out.println("Player takes insurance.");
            }

            if (playerTakesInsurance && dealerHasBlackjack){
                System.out.println("Player wins Insurance bet."); ///debug
                continue;
            }

            if (playerTakesInsurance && !dealerHasBlackjack){
                bankroll = bankroll - (0.5*betSize);
                System.out.println("Player lost insurance bet.");//debug
            }

            if (dealerShowsAce && playerHasBlackjack){
                playerTakesEvenMoney = playerTakesEvenMoney(trueCount);
                /* debug
                System.out.println("End of Even money check"); // debug
                break; //debug*/
            }

            else{
                playerTakesEvenMoney = false;
            }
            if(playerTakesEvenMoney){
                // payout player
                bankroll = bankroll+betSize;
                System.out.println("Player Takes even money");//debug
                continue;
            }
            while (true){//runs until player is done playing all hands
                // check for player pair
                playerHasPair = playerCards.pairCheck();
                if (playerHasPair){
                    split
                }
            }
            

            System.out.println();// debug
        } // end of shoe (Deck shuffles)
        numberOfShoesPlayed = numberOfShoesPlayed+1;
        System.out.println("numberOfShoesPlayed: " + numberOfShoesPlayed);//debug
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
        System.out.println("Checking if player takes even money"); //debug
        if (trueCount>=3){
            System.out.println("player takes even money"); //debug
            return true;
        }
        else{
            System.out.println("player does not take even money"); //debug
            return false;
        }
    }

}