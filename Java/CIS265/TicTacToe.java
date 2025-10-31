import java.util.Scanner;

public class TicTacToe{
    public static void main(String[] args){
        // default is to play again after game over
        Boolean play_again = true;
        
        while (play_again){
            //Create New board
            int[] board = new int [9];

            //Declare Starting player and other variables
            int move = 0;
            int player = 1;
            Boolean win = false;
            Scanner sc = new Scanner(System.in);

            //Main Game Loop
            while (win==false){
                
                // print state
                System.out.println("");
                print_board(board);
                if (player == 1){
                    System.out.print("Player X Choose your spot(1-9): ");
                }
                else{
                    System.out.print("Player O Choose your spot(1-9): ");
                }

                //chose move
                move = move_choice(board, sc);

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
            // play again?
            play_again = play_again(sc);
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
                System.out.print(i+1);
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


    public static int move_choice(int[] board, Scanner sc){
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


    public static Boolean play_again(Scanner sc){
        Boolean play_again = true;
        while(true){
            System.out.print("Would you like to play again? (y/n) ");
            String choice = sc.next();
            if (choice.equals("y")){
                break;
            }
            else if(choice.equals("n")){
                play_again=false;
                break;
            }
            else{
                System.out.println("Invalid input. Try Again.");
            }
        }
        return play_again;
    }


}