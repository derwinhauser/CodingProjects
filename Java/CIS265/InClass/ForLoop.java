public class ForLoop{
    
    public static void main(String[] args){
        
        int sum = 0;
       
        for (int i = 1; i<=100; i++){
            sum = sum + i;
        }
        
        System.out.println("1 + 2 + 3 + ... + 100 = " + sum);
        
        sum = 0;
        
        for (int i = 1; i<=50; i++){
            sum = sum + 2 * i;
        }

        System.out.println("2 + 4 + 6 + ... + 100 = " + sum);

        sum = 0;

        for (int i = 1; i <= 50; i++){
            sum = (sum + (2 * i - 1));
        }
        
        System.out.println("1 + 3 + 5 + ... + 99 = " + sum);

    }
}

