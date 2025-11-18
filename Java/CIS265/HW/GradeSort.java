import java.util.Scanner;
import java.util.Arrays;

public class GradeSort{
    public static void main(String[] args){
        // declare variables
        Scanner sc = new Scanner(System.in);
        int[] grades = new int[10];

        // prompt user to input 10 grades
        System.out.println("Please enter 10 grades as integers 0-100. Press enter after each grade.");
        for (int i=0; i<=9; i++){
            while(true){
                grades[i] = sc.nextInt();
                if (0 <= grades[i]){
                    if (grades[i]<=100){
                        break;
                    }
                }
                System.out.println("Invalid input. Try again.");
            }
        }

        // sort grades in ascending order
        Arrays.sort(grades);

        // prints grades in ascending order
        System.out.println("Grades in ascending order:");
        for (int i = 0; i<=9; i++){
            System.out.print(grades[i] + " ");
        }
    }
}