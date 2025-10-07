public class WhileLoop{
    public static void main(String[] args){
        int sum = 0;
        int i = 1;

        while (sum<5000){
            sum = sum+i;
            i = i+1;


        }
        int N = i-2;
        sum = sum - (i - 1);
        System.out.println(N);
        System.out.println(sum);

        //for loop
        int sum2=0;
        for (int k=1; sum2<=5000; k++){
            sum2=sum2+k;
            if (sum2>5000){
                N = k-1;
                sum2 = sum2-k;
                break;
            }
        }
        System.out.println(N);
        System.out.println(sum2);

    }
}