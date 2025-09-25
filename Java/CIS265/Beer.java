import java.util.Scanner;

public class Beer{
    
    public static void main(String[] args){
    Scanner sc = new Scanner(System.in);

    System.out.println("Please enter your age");

    int age = sc.nextInt();
    
    if (age >= 21){
        System.out.println("You are allowed to drink");
    }

    else{
        System.out.println("You are NOT allowed to drink");

    }

    }
}
