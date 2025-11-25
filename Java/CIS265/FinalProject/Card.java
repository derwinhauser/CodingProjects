public class Card{
    private String suit;
    private String value;
    private int count;
    
    public Card(String s, String n, int c){
        value = s;
        suit = n;
        count = c;
    }

    public void printCard(){
        System.out.println(value + " of " + suit);
    }

    public int getCardCount(){
        return count;
    }

    public String cardVal(){
        return value;
    }
}