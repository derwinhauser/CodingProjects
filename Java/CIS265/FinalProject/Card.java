public class Card{
    private String suit;
    private String value;
    
    public Card(String s, String n){
        value = s;
        suit = n;
    }

    public void printCard(){
        System.out.print(value + " of " + suit);
    }
}