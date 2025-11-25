/*
* TODO:
*
*
 */

import java.util.Scanner;
import java.util.ArrayList;

public class BlackJack{

    public static void main(String[] args){
        Deck shoe = new Deck(1);
        Deck discardTray = new Deck(0);
        int runningCount = 0;
        shoe.printShoeSize();
        System.out.println("Shoe: ");
        shoe.printDeck();
        System.out.println("Discard Tray:");
        discardTray.printDeck();
        runningCount = discardTray.getRunningCount();
        System.out.println("Running count: " + runningCount);
        for (int i=0; i<20; i++){
            Card tempCard = shoe.getCard(0);
            shoe.removeCard(0);
            discardTray.addCard(tempCard);
        }
        shoe.printShoeSize();
        System.out.println("Shoe: ");
        shoe.printDeck();
        System.out.println("Discard Tray:");
        discardTray.printDeck();
        runningCount = discardTray.getRunningCount();
        System.out.println("Running count: " + runningCount);
        
    }


}