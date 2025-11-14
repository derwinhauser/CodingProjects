import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class FindNumbers{
    public static void main(String[] args) throws IOException{
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);
        FileWriter f = new FileWriter("result.csv");
        ArrayList <String> raw_data = new ArrayList <String>();
        ArrayList <Integer> new_data = new ArrayList <Integer>();
        while(sc.hasNext()){
            String x = sc.next();
            raw_data.add(x);
        }
        new_data = convert_string_to_int(raw_data);
        Collections.sort(new_data);
        print_array_list_int(new_data);
    }

    public static void print_array_list_string(ArrayList <String> raw_data){
        for(int i=0; i<raw_data.size(); i++){
            System.out.print(raw_data.get(i) + ", ");
        }
    }
    public static ArrayList <Integer> convert_string_to_int(ArrayList <String> raw_data){
        ArrayList <Integer> new_list = new ArrayList <Integer>();
        for (int i=0; i<raw_data.size(); i++){
            try{
                new_list.add(Integer.parseInt(raw_data.get(i)));
            }
            catch(NumberFormatException e){
            }
        }
        return new_list;
    }
    public static void print_array_list_int(ArrayList <Integer> raw_data){
        for(int i=0; i<raw_data.size(); i++){
            System.out.print(raw_data.get(i) + ", ");
        }
    }
}