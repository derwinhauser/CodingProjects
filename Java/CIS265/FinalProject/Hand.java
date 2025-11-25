import java.util.ArrayList;

public class Hand{
    private ArrayList <Card> hand;

    public Hand(){
        hand = new ArrayList <Card>();
    }

    public void printHand(){
        for (int i=0; i<hand.size(); i++){
            Card temp = hand.get(i);            
            temp.printSymbol();
            System.out.print(" ");
        }    
        System.out.println();
    }

    public void printDealerHand(){
        Card temp = hand.get(0);
        temp.printSymbol();
        System.out.print(" ");
    }

    public Card getCard(int i){
        return hand.get(i);
    }

    public void addCard(Card c){
        hand.add(c);
    }
}