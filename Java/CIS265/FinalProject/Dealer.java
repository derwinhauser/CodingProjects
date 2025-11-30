public class Dealer{
    Hand hand;
    public Dealer(){
        hand = new Hand();
    }

    public void clearHand(){
        hand.clearCards();
    }

    public int getHandSize(){
        return hand.getSize();
    }

    public void setHand(Hand h){
        hand = h;
    }

    public Hand getHand(){
        return hand;
    }

    public Card getUpCard(){
        Card upCard = hand.getCard(1);
        return upCard;
    }

    public boolean blackjackCheck(){
        Hand dealerHand = getHand();
        if (dealerHand.totalHand()==21){
            return true;
        }
        else{
            return false;
        }
    }

}