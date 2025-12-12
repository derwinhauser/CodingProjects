package com.blackjacksim;

import java.util.ArrayList;

public class Hand{
    private ArrayList <Card> hand;
    private boolean handIsInPlay;
    private boolean betWasDoubled;
    private String result;
    public Hand(){
        hand = new ArrayList <Card>();
        handIsInPlay = true;
        betWasDoubled = false;
        result = "";
    }

    public void printHand(){
        for (int i=0; i<hand.size(); i++){
            Card temp = hand.get(i);            
            temp.printSymbol();
            System.out.print(" ");
        }    
    }

    public void setBetWasDoubled(boolean b){
        betWasDoubled = b;
    }

    public boolean getBetWasDoubled(){
        return betWasDoubled;
    }

    public String getResult(){
        return result;
    }

    public void setResult(String s){
        result = s;
    }

    public void setHandIsInPlay(boolean b){
        handIsInPlay = b;
    }

    public boolean getHandIsInPlay(){
        return handIsInPlay;
    }

    public String getHand(){
        String s = "";
        int handSize = hand.size();
        for(int i=0; i<handSize;i++){
            Card tempCard = hand.get(i);
            String symbol = tempCard.getSymbol();
            s = s+symbol+" ";
        }
        return s;
    }

    public String getDealerHand(){
        String s = "";
        Card temp = hand.get(1);
        String symbol = temp.getSymbol();
        s = "[] "+symbol;
        return s;
    }

    public void printDealerHand(){
        System.out.print("[] ");
        Card temp = hand.get(1);
        temp.printSymbol();
        System.out.println();
    }

    public boolean isSoftTotal(){
        int cardSum = 0;
        ArrayList <Card> aces = new ArrayList <Card>();
        for(int i=0; i<hand.size(); i++){
            Card tempCard = hand.get(i);
            int tempCardValue = 0;
            String tempSymbol = tempCard.getSymbol();
            if (tempSymbol.equals("A")){
                aces.add(tempCard);
                tempCardValue = 1;
            }
            else if (tempSymbol.equals("K")){
                tempCardValue = 10;
            }
            else if (tempSymbol.equals("Q")){
                tempCardValue = 10;
            }
            else if (tempSymbol.equals("J")){
                tempCardValue = 10;
            }
            else if (tempSymbol.equals("T") || tempSymbol.equals("10")){
                tempCardValue = 10;
            }
            else{
                tempCardValue = Integer.parseInt(tempSymbol);
            }
            cardSum = cardSum+tempCardValue;    
        }
        if (aces.size()==0){
            return false;
        }
        else{
            if(cardSum>11){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public String dealerUpCard(){
        Card tempcard = hand.get(1);
        String symbol = tempcard.getSymbol();
        return symbol;
    }

    public int getTableCount(){
        int count = 0;
        for (int i=0; i<hand.size(); i++){
            Card tempCard = hand.get(i);
            if (i!=1){
                count = count+tempCard.getCardCount();
            }
        }
        return count;
    }
    
    public boolean pairCheck(){
        if(hand.size()!=2){
            return false;
        }
        Card tempCard1 = getCard(0);
        Card tempCard2 = getCard(1);
        String tempCard1Val = tempCard1.getCardValue();
        String tempCard2Val = tempCard2.getCardValue();
        if (tempCard1Val.equals(tempCard2Val)){
            return true;
        }
        else{
            return false;
        }
    }

    public int totalHand(){
        int cardSum = 0;
        ArrayList <Card> aces = new ArrayList <Card>();
        for(int i=0; i<hand.size(); i++){
            Card tempCard = hand.get(i);
            int tempCardValue = 0;
            String tempSymbol = tempCard.getSymbol();
            if (tempSymbol.equals("A")){
                aces.add(tempCard);
            }
            else if (tempSymbol.equals("K")){
                tempCardValue = 10;
            }
            else if (tempSymbol.equals("Q")){
                tempCardValue = 10;
            }
            else if (tempSymbol.equals("J")){
                tempCardValue = 10;
            }
            else if (tempSymbol.equals("T") || tempSymbol.equals("10")){
                tempCardValue = 10;
            }
            else{
                tempCardValue = Integer.parseInt(tempSymbol);
            }
            cardSum = cardSum+tempCardValue;
            
        }
        for (int i=0; i<aces.size(); i++){
            if (cardSum>=11){
                cardSum = cardSum+1;
            }
            else{
                cardSum = cardSum+11;
            }
        }
        return cardSum;
    }

    public Card getCard(int i){
        return hand.get(i);
    }
    
    public int getSize(){
        return hand.size();
    }

    public void clearCards(){
        hand.clear();
    }

    public void addCard(Card c){
        hand.add(c);
    }

    public void removeCard(int i){
        hand.remove(i);
    }
}