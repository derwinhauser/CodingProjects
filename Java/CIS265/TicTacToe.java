import java.util.Scanner;

public class TicTacToe{
    public static void main(String[] args){
        //New board
        int[] board = new int [9];
        Scanner sc = new Scanner(System.in);
        print_data(board);
        //Main Game Loop
        while (true){
            print_board(board);
            int move = sc.nextInt();
            for (int i =0; i<=8; i++){

            }
        }
        
    }
    public static void print_data(int[] data){
        for (int i = 0; i<=8; i++){
            if (i%3==0){
                if (i != 0){
                    System.out.println("|");
                }
            }
            System.out.print("|" + data[i]);
        }
        System.out.println("|");
    }
    public static void print_board(int[] data){
        for (int i = 0; i<=8; i++){
            if (i%3==0){
                if (i != 0){
                    System.out.println("|");
                }
            }
            System.out.print("|");
            if (data[i] == 0){
                System.out.print(i + 1);
            }
            if (data[i] == 1){
                System.out.print("X");
            }
            if (data[i] == 2){
                System.out.print("O");
            }
        }
        System.out.println("|");
    }
}