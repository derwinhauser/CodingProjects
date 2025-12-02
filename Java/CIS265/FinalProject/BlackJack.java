import java.io.IOException;
import java.io.FileWriter;
public class BlackJack{
    public static void main(String args[]) throws IOException{
        FileWriter writer = new FileWriter("blackjackResults.csv", false);
        FileWriter handData = new FileWriter("handData.txt", false);
        FileWriter aceData = new FileWriter("aceData.txt", false);
        FileWriter doubleData = new FileWriter("doubleData.txt", false);
        FileWriter splitData = new FileWriter("splitData.txt", false);
        FileWriter payData = new FileWriter("payData.txt", false);
        Table table = new Table(4);// input deck size
        table.playShoe(1000);//input how many shoes to play
        writer.close();
        handData.close();
        aceData.close();
        doubleData.close();
        splitData.close();
        payData.close();
    }
}