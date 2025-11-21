import java.util.ArrayList;
public class Shoe{
    private ArrayList <Deck> shoe;
    

    public Shoe(int size){
        shoe = createShoe(size);
    }

    private ArrayList <Deck> createShoe(int size){
        ArrayList <Deck> shoe = new ArrayList <Deck>();
        for (int i=0; i<size; i++){
            shoe.add(new Deck());
        }
        return shoe;
    }
    public void printShoeSize(){
        System.out.println("There are " + shoe.size() + " total decks in this shoe.");
    }
}