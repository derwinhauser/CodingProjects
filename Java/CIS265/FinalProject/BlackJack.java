import java.io.IOException;

public class BlackJack{
    public static void main(String args[]) throws IOException{
        Table table = new Table(4);
        table.playShoe(10000);
    }
}