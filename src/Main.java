import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Moving moving = new Moving();
        board.emptyBoard();
        Scanner in = new Scanner(System.in);
        boolean play = true;
        board.addNewRandomToBoard();
        board.addNewRandomToBoard();
        board.printBoard();

        do {
            String action = in.nextLine();
            char[] myChar;
            myChar = action.toCharArray();
            System.out.println();
            switch (myChar[0]) {
                case 'j': // j lewo
                    moving.moveLeft(board.getBoard());
                    break;

                case 'k': // k dół
                    moving.moveDown(board.getBoard());
                    break;

                case 'l': // l prawo
                    moving.moveRight(board.getBoard());
                    break;

                case 'i': // i gora
                    moving.moveUp(board.getBoard());
                    break;
            }
            board.addNewRandomToBoard();
            board.printBoard();
        } while(play);


    }
}
