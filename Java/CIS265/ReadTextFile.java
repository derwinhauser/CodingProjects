import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReadTextFile{
    
    public static void main(String[] args) throws IOException{
        
        Scanner sc1 = new Scanner(System.in);

        System.out.print("Please enter your file name: ");
        String file_name = sc1.next();
        File my_file = new File(file_name);
        Scanner sc = new Scanner(my_file);
        int row_count=0;
        while (sc.hasNextLine()){
            System.out.println(sc.nextLine());
            row_count++;
        }
        System.out.println("There are " + row_count + " rows.");

    }
}