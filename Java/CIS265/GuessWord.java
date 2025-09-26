import java.util.Scanner;

public class GuessWord{
    public static void main(String[] args){
        
        // s = "abcde"
        // s.substring(start, end)
        //         (1,  2) returns "b"
        //         (1) returns "bcde"

        Scanner sc = new Scanner(System.in);
        String result ="";
        String letter="";
        String word = "desktop";
        int position = 0;
        String p1="", p2="";
        int size = word.length();
        boolean win_game=false;


        for (int i = 0; i<size; i++){
            result = result + "-";
        }
        System.out.println(result);
        for(int i = 0; i<10; i++){
    
            

            System.out.println("Please enter a letter:");
            letter = sc.nextLine();

            // System.out.println(letter); (not needed)
            position = word.indexOf(letter);

            if (position>=0){
                p1 = result.substring(0, position);
                p2 = result.substring(position+1);
                result = p1+letter+p2;
            }

            if(result.equals(word)){
                win_game = true;
            }

            System.out.println(result);
            
            if (win_game == true){
                break;
            }
            
    }

    if (win_game == true){
        System.out.println("Congrats, You win!");
    }
    else{
        System.out.println("YOU LOSE");
    }
        /* 
        if (word.contains(letter)){
            System.out.println("yes");
        }
        else {
            System.out.println("no");
        }
        */
    }
}