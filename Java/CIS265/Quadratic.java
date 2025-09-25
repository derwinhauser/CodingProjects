import java.util.Scanner;

public class Quadratic{
	
	public static void main(String[] args){
	Scanner sc = new Scanner(System.in);

	System.out.println("Please enter 3 numbers, separate by SPACE (a b c): ");

	double a = sc.nextDouble();
	double b = sc.nextDouble();
	double c = sc.nextDouble();
	boolean valid1 = (Math.pow(b, 2) - (4 * a * c) >= 0 );
	boolean valid2 = (a != 0);

	double x1 = (-b + (Math.sqrt((b * b) - (4 * a * c)))) / (2 * a); 
	double x2 = (-b - (Math.sqrt((b * b) - (4 * a * c)))) / (2 * a);

	if (valid1 && valid2){
		System.out.println("x = " + x1 + " and " + x2);
	}
	else{
		System.out.println("No Solution");
	}
	}
	}