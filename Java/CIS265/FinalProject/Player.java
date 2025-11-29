import java.util.ArrayList;

public class Player{
    private ArrayList <Hand> hands;

    public Player(){
        hands = new ArrayList <Hand>();
    }

    public Hand getPlayerHand(int i){
        return hands.get(i);
    }

    public int numberOfPlayerHands(){
        return hands.size();
    }

    public void splitHand(int i){
        hands.add(new Hand());//create new hand
        Hand tempHand = getPlayerHand(i);//curent hand
        Hand newHand = getPlayerHand(i+1);//new hand
        Card tempCard = tempHand.getCard(1);//card being split (removed from current hand and added to new hand)
        tempHand.removeCard(1);//removed card from current hand
        newHand.addCard(tempCard);//add card to new hand


    }
}