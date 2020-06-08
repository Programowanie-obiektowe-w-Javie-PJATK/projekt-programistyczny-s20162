import java.io.*;
import java.util.Random;

/**
 * Obiekt <code>Board</code> jest plansza do gry w gre 2048
 * @author Adam Lichy
 * @version 1.0
 */

public class Board implements Serializable {

    public static final int SIZE = 4;
    private int[][] board = new int[SIZE][SIZE];

    public Board() {}

    /**
     * Metoda <code>emptyBoard</code> przypisuje wzszystkim wartoscia w tablicy board wartosc 0
     */

    public void emptyBoard() {
        for (int i=0 ; i<Board.SIZE ; i++) {
            for (int j=0 ; j<Board.SIZE ; j++) {
                this.board[i][j]=0;
            }
        }
    }

    /**
     * Metoda <code>getPoints</code> zlicza wartosc wszystkich elementow tablicy board
     * @return zwraca sume tych elementow
     */

    public int getPoints() {
        int sum=1;
        for (int i=0 ; i<Board.SIZE ; i++) {
            for (int j=0 ; j<Board.SIZE ; j++) {
                sum+= this.board[i][j];
            }
        }
        return sum;
    }

    /**
     * Metoda <code>getBoard</code> pobiera tablice board
     * @return zwraca tablice board
     */

    public int[][] getBoard() {
        return this.board;
    }

    /**
     * Metoda <code>getBoardElement</code> pobiera wartosc jednego elementu tablicy
     * @return zwraca wartosc elementu tablicy
     */

    public int getBoardElement(int i, int j) {
        return this.board[i][j];
    }

    /**
     * Metoda <code>setBoard</code> przypisuje nowa tablice do pola board
     */

    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * Metoda <code>addNewRandomToBoard</code> dodaje do tablicy w wolne miejsce liczbe 2 lub 4
     */

    public void addNewRandomToBoard() {
        Random rand= new Random();
        int i,j,chance;

        do {
            i = rand.nextInt(Board.SIZE);
            j = rand.nextInt(Board.SIZE);
        } while (this.board[i][j]!=0);
        chance= rand.nextInt(100);
        if (chance<70) {
            this.board[i][j]=2;
        } else {
            this.board[i][j]=4;
        }
    }

    /**
     * Metoda <code>rotateBoard</code> obraca tablice wzgledem srodka
     * @return zwraca tablice w formie obroconej
     */

    public int[][] rotateBoard(int[][] board, int howMany) {
        int[][] newBoard = new int[Board.SIZE][Board.SIZE];
        switch (howMany%4) {
            case 0:
                return board;
            case 1:
                for (int i=0 ; i<Board.SIZE ; i++) {
                    for (int j = 0; j < Board.SIZE; j++) {
                        newBoard[i][j] = board[Board.SIZE - 1 - j][i];
                    }
                }
                break;
            case 2:
                for (int i=0 ; i<Board.SIZE ; i++) {
                    for (int j = 0; j < Board.SIZE; j++) {
                        newBoard[i][j] = board[Board.SIZE-1-i][Board.SIZE-1-j];
                    }
                }
                break;
            case 3:
                for (int i=0 ; i<Board.SIZE ; i++) {
                    for (int j = 0; j < Board.SIZE; j++) {
                        newBoard[i][j] = board[j][Board.SIZE-1-i];
                    }
                }
                break;
        }
        return newBoard;
    }

    /**
     * Metoda <code>mergeSwap</code> gowny mechanizm gry, metoda sprawdza czy przesunac i poaczyc elementy tablicy
     * @return zwraca tablice w formie po ruchu
     */

    public boolean mergeSwap(int[] verse, boolean execute) {
        boolean moved = false;
        for (int i = 0; i < verse.length; i++) {
            for (int j = i + 1; j < verse.length; j++) {
                if (verse[i] == 0) {
                    if (verse[j] != 0) {
                        if (execute) {
                            moved = true;
                            verse[i] = verse[j];
                            verse[j] = 0;
                        } else {
                            return true;
                        }
                    }
                }else {
                    if (verse[i] == verse[j]) {
                        if (execute) {
                            moved = true;
                            verse[i] = 2 * verse[i];
                            verse[j] = 0;
                        } else {
                            return true;
                        }
                    } else {
                        if (verse[j]!=0)
                            break;
                    }
                }
            }
        }
        return moved;
    }

    /**
     * Metoda <code>moveIsPossible</code> sprawdza czy w tablicy jest mozliwy ruch
     * @return zwraca wartosc logiczna ,true jesli ruch mozliwy false jesli nie
     */

    public boolean moveIsPossible(int[][] board, boolean execute) {
        boolean moved = false;
        for(int i =0 ; i<Board.SIZE ; i++) {
            if (mergeSwap(board[i],execute)) {
                moved =true;
            }
        }
        return moved;
    }

    /**
     * Metoda <code>winCheck</code> sprawdza czy nastapila wygrana
     * @return zwraca wartosc logiczna
     */

    public boolean winCheck(int[][] board) {

        for (int i=0; i<Board.SIZE ; i++) {
            for (int j=0 ; j<Board.SIZE ; j++) {
                if (board[i][j]== 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metoda <code>looseCheck</code> sprawdza czy nastapila przegrana
     * @return zwraca wartosc logiczna
     */

    public boolean looseCheck(int[][] board) {
        return !moveIsPossible(board, false) && !moveIsPossible(rotateBoard(board, 1), false)
                && !moveIsPossible(rotateBoard(board, 2), false) &&
                !moveIsPossible(rotateBoard(board, 3), false);
    }

    /**
     * Metoda <code>saveBoard</code> zapisuje tablice board do pliku
     * @throws IOException gdy problemy z zapisem
     */

    public void saveBoard(Board board) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("savedGame.dat");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(board);
        objectOutputStream.close();
    }

    /**
     * Metoda <code>loadBoard</code> wczytuje tablice board z pliku
     * @throws IOException gdy problemy z otwarciem pliku
     * @throws ClassNotFoundException gdy problemy z zaladowaniem klasy
     * @return zwraca wczytana tablice
     */

    public Board loadBoard() throws IOException, ClassNotFoundException {
        Board playingBoard;
        FileInputStream fileInputStream = new FileInputStream("savedGame.dat");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        playingBoard = (Board) objectInputStream.readObject();
        objectInputStream.close();
        return playingBoard;
    }
}