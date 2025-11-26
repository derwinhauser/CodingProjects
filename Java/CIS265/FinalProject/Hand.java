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
        System.out.print("[] ");
        Card temp = hand.get(1);
        temp.printSymbol();
        System.out.print(" ");
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
    
    public int totalHand(){
        int cardSum = 0;
        ArrayList <Card> aces = new ArrayList <Card>();
        for(int i=0; i<hand.size(); i++){
            Card tempCard = hand.get(i);
            int tempCardVal = 0;
            String tempSymbol = tempCard.getSymbol();
            if (tempSymbol.equals("A")){
                aces.add(tempCard);
            }
            else if (tempSymbol.equals("K")){
                tempCardVal = 10;
            }
            else if (tempSymbol.equals("Q")){
                tempCardVal = 10;
            }
            else if (tempSymbol.equals("J")){
                tempCardVal = 10;
            }
            else{
                tempCardVal = Integer.parseInt(tempSymbol);
            }
            cardSum = cardSum+tempCardVal;
            
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

    public void addCard(Card c){
        hand.add(c);
    }
}