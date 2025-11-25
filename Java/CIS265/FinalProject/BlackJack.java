/*
* TODO:
*
*
 */

import java.util.Scanner;
import java.util.ArrayList;

public class BlackJack{

    public static void main(String[] args){
        Deck shoe = new Deck(6);
        Deck discardTray = new Deck(0);
        int runningCount = 0;
        shoe.printShoeSize();
        discardTray.printDeck();
        runningCount = discardTray.getRunningCount();
        System.out.println("Running count: " + runningCount);
        for (int i=0; i<20; i++){
            Card tempCard = shoe.getCard(i);
            shoe.removeCard(i);
            discardTray.addCard(tempCard);
        }
        shoe.printShoeSize();
        discardTray.printDeck();
        runningCount = discardTray.getRunningCount();
        System.out.println("Running count: " + runningCount);
        
    }


}