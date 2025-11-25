public class Card{
    private String suit;
    private String value;
    private int count;
    private String symbol;
    
    public Card(String s, String n, int c, String k){
        value = s;
        suit = n;
        count = c;
        symbol = k;
    }

    public void printCard(){
        System.out.print(value + " of " + suit);
    }

    public void printSymbol(){
        System.out.print(symbol);
    }

    public int getCardCount(){
        return count;
    }

    public String cardVal(){
        return value;
    }
}