import java.io.IOException;
import java.io.FileWriter;
public class BlackJack{
    public static void main(String args[]) throws IOException{
        FileWriter writer = new FileWriter("blackjackResults.csv", false);
        FileWriter writer1 = new FileWriter("blackjackData.txt", false);
        FileWriter writer2 = new FileWriter("blackjackData2.txt", false);
        Table table = new Table(4);// input deck size
        table.playShoe(2000);//input how many shoes to play
        writer.close();
        writer1.close();
        writer2.close();
    }
}