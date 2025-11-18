/*
 * Pattern.java
*/


public class Pattern{
    public static void main(String[] args){
        
        String pattern = "*";
        
        //pattern works with any # of lines
        int lines = 9;
        /*
         * Print first pattern with for loop
         */
        
        //first half of pyramid
         for (int x = 1; x <= (lines/2)+1; x++){
            for (int z = 1; z <= (lines/2) - (x-1); z++){
                System.out.print(" ");
            }
            for (int y = 1; y <= 2*x-1; y++){
                System.out.print(pattern);
            }
            System.out.println(" ");
        }
        //second half of pyramid
        for (int a = 1; a <= (lines/2) ; a++){
            for (int b = 1; b <= a; b++){
                System.out.print(" ");
            }
            for (int c = 1; c <= (lines - (2*a)); c++){
                System.out.print(pattern);
            }
            System.out.println(" ");
        }        
        
        /*
         * Print second pattern with for loop
         */

        for (int i = 1; i <= lines; i++){
            System.out.print(i);
            for (int j = 1; j<=i; j++){
                System.out.print(pattern);
                }
            System.out.println(""); 
        }

    }
}