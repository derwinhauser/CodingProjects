import java.util.Scanner;

public class TicTacToe{
    public static void main(String[] args){
        //Create New board
        int[] board = new int [9];

        //Declare Starting player and other variables
        int move = 0;
        int player = 1;
        Boolean win = false;

        //Main Game Loop
        while (win==false){
            // print state
            System.out.println("");
            if (player == 1){
                System.out.println("Player X Choose your spot: ");
            }
            else{
                System.out.println("Player O Choose your spot: ");
            }
            print_board(board);

            //chose move
            move = move_choice(board);

            //update state (board[move])
            board[move-1] = player;

            //win check
            win = win_check(board);

            //change players
            if (player == 1){
                player = 2;
            }
            else{
                player = 1;
            }
        }
        // Game over
        print_board(board);
        System.out.println("Game over");
        if (win==true){
            if (player == 2){
                System.out.println("Player X Wins!");
            }
            else {
                System.out.println("Player O Wins!");
            }
        }
        else {
            System.out.println("Nobody Wins");
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
    public static int move_choice(int[] board){
        Scanner sc = new Scanner(System.in);
        int move = 0;
        while(true){
        move = sc.nextInt();
        if (move >= 1){
            if (move <= 9){
                if (board[move-1]==0){
                    break;
                }
            }
        }  
        System.out.println("Invalid move. Try again");
        }
        return move;
    }
    public static boolean win_check(int[] board){
        //check rows
        for (int i = 0; i<=2; i++){
            if (board[3*i] == board[3*i+1]){
                if (board[3*i] == board[3*i+2]){
                    if (board[3*i] != 0){
                        return true;
                    }
                }
            }
        }
        // check collumns
        for (int i = 0; i<=2; i++){
            if (board[i] == board[i+3]){
                if (board[i] == board[i+6]){
                    if (board[i] != 0){
                        return true;
                    }
                }
            }
        }
        //check diagonals
        if (board[0] == board[4]){
            if (board[0] == board[8]){
                if (board[0] != 0){
                    return true;
                }
            }
        }
        if (board[2] == board[4]){
            if (board[2] == board[6]){
                if (board[2] != 0){
                    return true;
                }
            }
        }
        // no win
        return false;
    }
}