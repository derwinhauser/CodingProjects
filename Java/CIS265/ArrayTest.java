public class ArrayTest{
    public static void main(String[] args){
        int[][] board = new int [3][3];
        System.out.println(board.length);
        System.out.println(board[0].length);
        print_data(board);
        show_board(board);
    }

    public static void print_data(int[][] d){
        for (int i=0; i<d.length; i++){
            /*
            System.out.print(d[i][0]);
            System.out.print("|");
            System.out.print(d[i][1]);
            System.out.print("|");
            System.out.print(d[i][2]);
            System.out.println();
            */
            for (int j=0; j<d[i].length; j++){
                System.out.print(d[i][j]);
                if (j<d[i].length - 1){
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }
    public static void show_board(int[][] b){
        for (int i=0; i<b.length; i++){
            for (int j=0;j<b[0].length; j++){
                System.out.print("|");
                if (b[i][j]==1){
                    System.out.print("O");
                }
                else if (b[i][j]==2){
                    System.out.print("X");
                }
                else if (b[i][j]==0){
                    System.out.print(3*i+j+1);
                }
            }
            System.out.println("|");
        }
    }
}