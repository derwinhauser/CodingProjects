import java.util.ArrayList;

public class Deck{
    private ArrayList <Card> deck;
    
    public Deck(){
        deck = createDeck();
    }

    public Deck createDeck(){
        String suit = "";
        String value = "";
        for (int i=1; i<=4; i++){
            if (i==1){
                suit.equals("spades");
            }
            else if(i==2){
                suit.equals("hearts");
            }
            else if (i==3){
                suit.equals("clubs");
            }
            else{
                suit.equals("diamonds");
            }
            for (int j=1; j<=13; j++){
                if (j==1){
                    value.equals("ace");
                }
                else if (j==2){
                    value.equals("two");
                }
                else if (j==3){
                    value.equals("three");
                }
                else if (j==4){
                    value.equals("four");
                }
                else if (j==5){
                    value.equals("five");
                }
                else if (j==6){
                    value.equals("six");
                }
                else if (j==7){
                    value.equals("seven");
                }
                else if (j==8){
                    value.equals("eight");
                }
                else if (j==9){
                    value.equals("nine");
                }
                else if (j==10){
                    value.equals("ten");
                }
                else if (j==11){
                    value.equals("jack");
                }
                else if (j==12){
                    value.equals("queen");
                }
                else{
                    value.equals("king");
                }
                deck.add(new Card(value, suit));
            }
        }
    }
}