import java.util.Random;

public class Board {
    private int[][] board = new int[4][4];

    public Board() {}

    public void emptyBoard() {
        for (int i=0 ; i<4 ; i++) {
            for (int j=0 ; j<4 ; j++) {
                this.board[i][j]=0;
            }
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void printBoard() {
        for (int i=0 ; i<4 ; i++) {
            for (int j=0 ; j<4 ; j++) {
                if (j==3) {
                    System.out.print(this.board[i][j]);
                } else {
                    System.out.print(this.board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void addNewRandomToBoard() {
        Random rand= new Random();

        int i,j;
        do {
            i = rand.nextInt(4);
            j = rand.nextInt(4);
        } while (this.board[i][j]!=0);
        this.board[i][j]=(rand.nextInt(2)+1)*2;

    }
}
