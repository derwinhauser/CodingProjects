import java.util.Scanner;

public class GuessWord{
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        String result ="";
        String letter="";
        String word = "desktop";
        int size = word.length();
        for (int i = 0; i<size; i++){
            result = result + "_ ";
        }
        System.out.println(result);

        System.out.println("Please enter a letter:");
        letter = sc.nextLine();

        System.out.println(letter);
        System.out.println(word.indexOf(letter));
        if (word.contains(letter)){
            System.out.println("yes");
        }
        else {
            System.out.println("no");
        }
    }
}