import java.util.ArrayList;

public class Deck{
    private ArrayList <Card> deck;
    
    public Deck(int numberOfDecks){
    deck = createDeck(numberOfDecks);
    }

    public void printDeck(){
        for (int i=0; i<deck.size(); i++){
            Card temp = deck.get(i);
            System.out.print(i+1 + ". ");
            temp.printCard();
        }
    }

    public void addCard(Card c){
        deck.add(c);
    }

    public Card getCard(int i){
        return deck.get(i);
    }

    public int getRunningCount(){
        int count = 0;
        for (int i=0; i<deck.size(); i++){
            Card tempCard = deck.get(i);
            count = count+tempCard.getCardCount();
        }
        return count;

    }

    public void removeCard(int card){
        deck.remove(card);
    }

    public int getShoeSize(){
        return deck.size();
    }

    public void printShoeSize(){
        double totalCards = deck.size();
        double decks = totalCards/52;
        System.out.println("There are " + decks +  " decks in this shoe.");
    }

    private ArrayList <Card> createDeck(int numberOfDecks){
        ArrayList <Card> deck = new ArrayList<Card>();
        String suit = "";
        String value = "";
        String symbol = "";
        for (int k=0; k<numberOfDecks; k++){
            for (int i=1; i<=4; i++){
                if (i==1){
                    suit="spades";
                }
                else if(i==2){
                    suit="hearts";
                }
                else if (i==3){
                    suit="clubs";
                }
                else{
                    suit="diamonds";
                }
                for (int j=1; j<=13; j++){
                    int count = 0;
                    if (j==1){
                        value="ace";
                        count = -1;
                        symbol = "A";
                    }
                    else if (j==2){
                        value="two";
                        count = 1;
                        symbol = "2";
                    }
                    else if (j==3){
                        value="three";
                        count = 1;
                        symbol = "3";
                    }
                    else if (j==4){
                        value="four";
                        count = 1;
                        symbol = "4";
                    }
                    else if (j==5){
                        value="five";
                        count = 1;
                        symbol = "5";
                    }
                    else if (j==6){
                        value="six";
                        count = 1;
                        symbol = "6";
                    }
                    else if (j==7){
                        value="seven";
                        symbol = "7";
                    }
                    else if (j==8){
                        value="eight";
                        symbol = "8";
                    }
                    else if (j==9){
                        value="nine";
                        symbol = "9";
                    }
                    else if (j==10){
                        value="ten";
                        count = -1;
                        symbol = "10";
                    }
                    else if (j==11){
                        value="jack";
                        count = -1;
                        symbol = "J";
                    }
                    else if (j==12){
                        value="queen";
                        count = -1;
                        symbol = "Q";
                    }
                    else{
                        value="king";
                        count = -1;
                        symbol = "K";
                    }
                    deck.add(new Card(value, suit, count, symbol));
                }
            }
        }
        return deck;
    }
}