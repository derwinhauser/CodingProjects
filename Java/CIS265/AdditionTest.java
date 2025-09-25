/*
1. Variables:
	name: start with letter or underscore, followed by letters and numbers
	Good name: first_name, age,
	Bad name: var1, var2
	datatype: int, double, String

2. declaration with initialization:
	int number1 = 2;
	String first_name="Superman";

3. input: Scanner
4. Math operators: + - * /
	Math functions: Math class


*/

import java.util.Scanner;

public class AdditionTest{
	//main method
	public static void main(String[] args){
	// step 1: declaration
	int a = 3;
	int b = 5;
	Scanner sc = new Scanner(System.in);

	// step 2: input
	System.out.println("Please enter 2 integers, separate by SPACE: ");
	a = sc.nextInt();
	b = sc.nextInt(); 

	// step 3: calculation
	int c = a + b;
	double d = Math.pow(a,b);
	// step 4: output
	System.out.println("a = " + a);
	System.out.println("b = " + b);
	System.out.println("a + b = " + c);
	System.out.println("a^b = " + d);
	}


}