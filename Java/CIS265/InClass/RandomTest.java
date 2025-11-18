/*
Math.random(); --> Returns double
*/


public class RandomTest{
    static double random;
    static int randomInt;
    public static void main(String[] args){
        for(int i=0; i<=10; i++){
            random = getRandom();
            System.out.println(random);
        }
        for (int i=0; i<=10; i++){
            randomInt = RandomInt();
            System.out.println(randomInt);
        }
    }

    public static double getRandom(){
        double temp =0;
        temp = Math.random();
        return temp;
    }

    public static int RandomInt(){
        int temp=0;
        temp = (int)(10*Math.random());

        return temp;
    }
}