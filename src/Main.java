import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Board board = new Board();
            board.emptyBoard();
            Scanner in = new Scanner(System.in);
            board.addNewRandomToBoard();
            board.addNewRandomToBoard();
            //board.printBoard();
            char[] myChar;
            int rotated=0;

            do {
                board.setBoard(board.rotateBoard(board.getBoard(),4-rotated));
                rotated=0;
                board.printBoard();
                String action = in.nextLine();
                myChar = action.toCharArray();
                System.out.println();

                switch (myChar[0]) {
                    case 'k': // k dół
                        rotated=1;
                        break;
                    case 'l': // l prawo
                        rotated=2;
                        break;
                    case 'i': // i gora
                        rotated=3;
                        break;
                    case 'j': // j lewo
                        break;
                    case 's': // zapis
                        try {
                            board.saveBoard(board);
                        } catch (FileNotFoundException e) {
                            System.out.println("Brak zapisanej gry.");
                            continue;
                        } catch (IOException e) {
                           System.out.println("cos poszło nie tak ");
                            continue;
                        }
                        continue;

                    case 'w': // wczytanie
                        try {
                            board = board.loadBoard();
                        } catch (FileNotFoundException e) {
                            System.out.println("Brak zapisanej gry.");
                            continue;
                        }
                        continue;
                    default:
                        continue;
                }
                board.setBoard(board.rotateBoard(board.getBoard(), rotated));
                if (!board.move(board.getBoard(), true)) {
                    System.out.println("Ruch niemożliwy.");
                    continue;
                }
                if (board.winCheck(board.getBoard())) {
                    System.out.println("Gratuluje wygranej!");
                    break;
                }
                board.addNewRandomToBoard();

                if (board.looseCheck(board.getBoard())) {
                    System.out.println("Niestety przegrałeś ;( ");
                    break;
                }

            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
