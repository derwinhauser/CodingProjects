import java.io.IOException;
import java.io.FileWriter;
public class BlackJack{
    public static void main(String args[]) throws IOException{
        FileWriter writer = new FileWriter("blackjackResults.csv", false);
        Table table = new Table(4);
        table.playShoe(100);
        writer.close();
    }
}