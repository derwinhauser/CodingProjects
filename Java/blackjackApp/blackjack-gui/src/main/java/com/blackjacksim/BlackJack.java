package com.blackjacksim;

import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class BlackJack{
    public static void main(String args[]) throws IOException{
        FileWriter writer = new FileWriter("blackjackResults.csv", false);
        FileWriter handData = new FileWriter("handData.txt", false);
        FileWriter aceData = new FileWriter("aceData.txt", false);
        FileWriter doubleData = new FileWriter("doubleData.txt", false);
        FileWriter splitData = new FileWriter("splitData.txt", false);
        FileWriter payData = new FileWriter("payData.txt", false);
        Scanner sc = new Scanner(System.in);
        
        // How many decks per shoe?
        System.out.print("How many decks are in the shoe?(2-8) ");
        int shoeSize = sc.nextInt();
        Table table = new Table(shoeSize);// input deck size

        // How many decks remain before shuffling?
        System.out.println("How many decks remain before shuffle?");
        double shuffleAt = sc.nextDouble();
        table.setShuffleAt(shuffleAt);

        // How many shoes are simulated?
        System.out.print("How many shoes do you want to simulate?(1-10000) ");
        int numberOfShoes = sc.nextInt();

        System.out.println("Running Simulation...");
        table.playShoe(numberOfShoes);//input how many shoes to play
        System.out.println("Simulation Complete.");

        writer.close();
        handData.close();
        aceData.close();
        doubleData.close();
        splitData.close();
        payData.close();
        sc.close();
    }
}