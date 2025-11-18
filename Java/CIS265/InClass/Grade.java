import java.util.Scanner;

public class Grade{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);           
        String grade = " ";
        System.out.println("Please enter your percent grade");
        double percent = sc.nextDouble();

        if (percent > 90){
           grade = "A";
        }
        else if (percent > 80){
            grade = "B";
        }
        else if (percent > 70){
            grade = "C";
        }
        else if (percent > 60){
            grade = "D";
        }
        else{
            grade = "F";
        }

        System.out.println("Your grade is " + grade);
    }
}