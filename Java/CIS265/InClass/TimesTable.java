/*
Code a times table 
*/



public class TimesTable{
    public static void main(String[] args){
        int max = 9;

        for (int i = 1; i <= max; i++){
            for (int j = 1; j <= max; j++){
                System.out.print(j*i);
                if (i*j < 10){
                    System.out.print("   ");
                }
                else if (i*j<100){
                    System.out.print("  ");                
            }
                else{
                    System.out.print(" ");
                }
            }
            System.out.println(" ");
        }
    }
}