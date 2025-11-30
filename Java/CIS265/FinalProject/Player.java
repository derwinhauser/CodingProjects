import java.util.ArrayList;

public class Player{
    private ArrayList <Hand> hands;
    private double bankroll;
    public Player(){
        hands = new ArrayList <Hand>();
        bankroll = 1000;
    }

    public Hand getHand(int i){
        return hands.get(i);
    }

    public int getNumberOfHands(){
        return hands.size();
    }

    public void setHand(Hand hand){
        hands.add(hand);
    }

    public double getBankroll(){
        return bankroll;
    }

    public void clearHand(){
        hands.clear();
    }
    
    public boolean blackjackCheck(){
        Hand playerHand = getHand(0);
        if (playerHand.totalHand()==21){
            return true;
        }
        else{
            return false;
        }
    }

    public void splitHand(int i){
        hands.add(new Hand());//create new hand
        Hand currentHand = getHand(i);//curent hand
        Hand newHand = getHand(i+1);//new hand
        Card tempCard = currentHand.getCard(1);//card being split (removed from current hand and added to new hand)
        currentHand.removeCard(1);//removed card from current hand
        newHand.addCard(tempCard);//add card to new hand
    }
}