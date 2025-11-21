import java.util.ArrayList;

public class Deck{
    private ArrayList <Card> deck;
    
    public Deck(){
    deck = createDeck();
    }

    public void printDeck(){
        for (int i=0; i<deck.size(); i++){
            Card temp = deck.get(i);
            temp.printCard();
            System.out.print(", ");
        }
    }

    private ArrayList <Card> createDeck(){
        ArrayList <Card> deck = new ArrayList<Card>();
        String suit = "";
        String value = "";
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
                if (j==1){
                    value="ace";
                }
                else if (j==2){
                    value="two";
                }
                else if (j==3){
                    value="three";
                }
                else if (j==4){
                    value="four";
                }
                else if (j==5){
                    value="five";
                }
                else if (j==6){
                    value="six";
                }
                else if (j==7){
                    value="seven";
                }
                else if (j==8){
                    value="eight";
                }
                else if (j==9){
                    value="nine";
                }
                else if (j==10){
                    value="ten";
                }
                else if (j==11){
                    value="jack";
                }
                else if (j==12){
                    value="queen";
                }
                else{
                    value="king";
                }
                deck.add(new Card(value, suit));
            }
        }
        return deck;
    }
}