public class MethodTest{
    // main method
    public static void main(String[] args){
        System.out.println("Hello World");
        //call a method
        print_star_pattern(3);
        double c = f_to_c(100.0);
        System.out.println(c);
    }

    // other method
    public static void print_star_pattern(int n){
        for (int i=1; i<=n; i++){
            for (int j=1; j<=i; j++){
                System.out.print("*");
            }
            System.out.println("");
        }
    }

    public static int calculate_sum(int n){
        int sum = 0;
        for (int i=1; i<=n; i++){
            sum = sum + 1;

        }
        return sum;
    }
    public static double f_to_c(double f){
         double c = (f-32)*5/9;
         return c;

    }
}