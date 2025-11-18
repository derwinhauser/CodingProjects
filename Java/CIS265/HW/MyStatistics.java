import java.util.Scanner;

public class MyStatistics {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Decide how long the for loop will run
        System.out.print("How many integers will you enter (at least 5)? ");
        int n = sc.nextInt();


        // If user chooses less than 5 ints then program closes
        if (n < 5) {
            System.out.println("You must enter at least 5 integers.");
            return;
        }
        // Declare sum_val variable
        int sum_val = 0;

        System.out.println("Please enter " + n + " integers:");

        // Use a for loop to read numbers and add each number to the sum
        for (int i = 0; i < n; i++) {
            int num = sc.nextInt();
            sum_val += num;
        }

        // Find the mean
        double mean_val = (double) sum_val / n;

        // Final Output
        System.out.println("Your input has " + n + " numbers");
        System.out.println("The sum is : " + sum_val);
        System.out.println("The mean is: " + mean_val);

    }
}
