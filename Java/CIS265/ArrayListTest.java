import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListTest{
    public static void main(String[] args){
        // declare an ArrayList
        ArrayList <Integer> ages = new ArrayList<Integer> ();
        Scanner sc = new Scanner(System.in);

        // enter data
        System.out.println("Enter Ages followed by enter. Type 'break' when all ages are entered");
        while (true){
            if (sc.hasNextInt()){
                int temp = sc.nextInt();
                ages.add(temp);
            }
            else if (sc.hasNext()){
                String str = sc.next();
                if (str.equals("break")){
                    break;
                }
                else{
                    System.out.println("INVALID");
                }
            }
            else{
                System.out.println("INVALID");
                sc.next();
            }
        }
        
        // output
        System.out.println("Ages Size: "+ages.size());
        print_array_list(ages);
        double avg = get_avg(ages);
        System.out.println("Average: "+ avg);
    }

    public static void print_array_list(ArrayList <Integer> a){
        int size = a.size();
        System.out.print("Ages: ");
        for (int i=0; i<=size-1; i++){
            System.out.print(a.get(i) + ", ");
        }
        System.out.println();
    }

    public static double get_avg(ArrayList <Integer> a){
        int size = a.size();
        int temp = 0;
        for (int i=0; i<=size-1; i++){
            temp = temp+a.get(i);
        }
        double sizeD = size;
        double tempD = temp;
        double avg = tempD/(sizeD);
        return avg;
    }
}