import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class WriteTextFile{

    public static void main(String[] args) throws IOException{

        File f1 = new File("students.csv");
        FileWriter f2 = new FileWriter("student_result.csv",false);
        Scanner sc = new Scanner(f1);
        int rows = 0;
        ArrayList <String> names = new ArrayList <String>();
        ArrayList <Integer> ages = new ArrayList <Integer>();
        String temp = "";

        while (sc.hasNextLine()){
            temp = sc.nextLine();
            f2.write(temp);
            f2.write("\n");
            rows++;
            names.add(temp.split(",")[0]);
            ages.add(Integer.parseInt(temp.split(",")[1]));
        }

        f2.write("There are " + (rows+1) + " rows in this file");
        f2.close();
        sc.close();
        System.out.println("There are " + (rows+1) + " rows in this file");
        System.out.println(names);
        System.out.println(ages);
    }


}