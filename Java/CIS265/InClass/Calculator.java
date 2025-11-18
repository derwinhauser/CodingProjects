/*
1. Variables: 



*/

public class Calculator {

	public static void main(String args[]) {
		int a = 10;
		int d = 25;

		System.out.println("a  = " + (a));
		System.out.println("d  = " + (d));

		System.out.println("a++  = " + (a++) );
		System.out.println("a--  = " + (a--) );

		// Check the difference in d++ and ++d
		System.out.println("d++  = " + (d++) );
		System.out.println("++d  = " + (++d) );

	}
}