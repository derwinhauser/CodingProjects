import java.util.Scanner;
public class CIS265HW4{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Temperature Degree Conversion:");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");

        boolean input = false;
        while (input == false){
        System.out.println("Please enter 1 or 2");
        int choice = sc.nextInt();
            if(choice == 1){
                System.out.println("OK, convert Celsius to Fahrenheit. Please enter an Integer: ");
                int degrees = sc.nextInt();
                double Fahrenheit = CtoF(degrees);
                System.out.println("Result is " + Fahrenheit);
                break;
            }
            else if(choice == 2){
                System.out.println("OK, convert Fahrenheit to Celsius. Please enter an Integer: ");
                int degrees = sc.nextInt();
                double Celsius = FtoC(degrees);
                System.out.println("Result is " + Celsius);
                break;
            }
            else{
                System.out.println("Invalid input. Try again.");
            }
        }
    }
    public static double CtoF(double c){
        double f = 0;
        f = c*9/5+32;
        return f;
        }
    public static double FtoC(double f){
        double c = 0;
        c = (f-32)*5/9;
        return c;
        }

}