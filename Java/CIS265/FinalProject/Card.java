public class Card{
    private String suit;
    private String value;
    
    public Card(String s, String n){
        value = s;
        suit = n;
    }

    public void printCard(){
        System.out.println(value + " of " + suit);
    }

    public String cardVal(){
        return value;
    }
}