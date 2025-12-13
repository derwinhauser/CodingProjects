package com.blackjacksim;

import java.io.IOException;
import java.util.ArrayList;

public class Player{
    private ArrayList <Hand> hands;
    private double bankroll;
    private int betSize;
    private int handNumber;
    public Player(){
        hands = new ArrayList <Hand>();
        handNumber = 0;
        bankroll = 200000;
    }

    public void addHand(){
        Hand newHand = new Hand();
        hands.add(newHand);
    }

    public void addHandNumber(){
        handNumber++;
    }

    public int getHandNumber(){
        return handNumber;
    }

    public Hand getHand(int i){
        return hands.get(i);
    }

    public void setBetSize(int i){
        betSize = i;
    }
    public int getBetSize(){
        return betSize;
    }

    public int getNumberOfHands(){
        return hands.size();
    }

    public void setHand(Hand hand, int i){
        hands.set(i, hand);
    }

    public double getBankroll(){
        return bankroll;
    }

    public void setBankroll(double d){
        bankroll = d;
    }

    public void clearHand(){
        hands.clear();
    }
    
    public boolean blackjackCheck(){
        Hand playerHand = getHand(0);
        if (playerHand.getSize()!=2){
            return false;
        }
        if (playerHand.totalHand()==21){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean handCanSplit(int i){
        Hand hand = getHand(i);
        if (hands.size()>3){
            return false;
        }
        if (hand.getSize() != 2){
            return false;
        }
        if (hand.getCard(0).getCardValue()==hand.getCard(1).getCardValue()){
            return true;
        }
        else{
            return false;
        }
    }

    public void splitHand(int i)throws IOException{
        int newHandNumber = hands.size();//new hand
        addHand();//create new hand
        Hand currentHand = getHand(i);//curent hand
        Hand newHand = hands.get(newHandNumber);
        Card tempCard = currentHand.getCard(1);//card being split (removed from current hand and added to new hand)
        currentHand.removeCard(1);//removed card from current hand
        newHand.addCard(tempCard);//add card to new hand
        setHand(currentHand, i);
        setHand(newHand, newHandNumber);
    }
}