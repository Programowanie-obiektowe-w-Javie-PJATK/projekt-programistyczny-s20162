package sample;

import java.io.*;
import java.util.Random;

public class Board implements Serializable {
    public static final int SIZE = 4;
    private int[][] board = new int[SIZE][SIZE];

    public Board() {}

    public void emptyBoard() {
        for (int i=0 ; i<Board.SIZE ; i++) {
            for (int j=0 ; j<Board.SIZE ; j++) {
                this.board[i][j]=0;
            }
        }
    }

    public int getPoints() {
        int sum=1;
        for (int i=0 ; i<Board.SIZE ; i++) {
            for (int j=0 ; j<Board.SIZE ; j++) {
                sum+= (this.board[i][j]+1);
            }
        }
        return sum;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getBoardElement(int i, int j) {
        return this.board[i][j];
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

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

    public boolean move(int[][] board, boolean execute) {
        boolean moved = false;
        for(int i =0 ; i<Board.SIZE ; i++) {
            if (mergeSwap(board[i],execute)) {
                moved =true;
            }
        }
        return moved;
    }

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

    public boolean looseCheck(int[][] board) {
        return !move(board, false) && !move(rotateBoard(board, 1), false)
                && !move(rotateBoard(board, 2), false) &&
                !move(rotateBoard(board, 3), false);
    }

    public void saveBoard(Board board) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("savedGame.dat");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(board);
        objectOutputStream.close();
    }

    public Board loadBoard() throws IOException, ClassNotFoundException {
        Board playingBoard;
        FileInputStream fileInputStream = new FileInputStream("savedGame.dat");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        playingBoard = (Board) objectInputStream.readObject();
        objectInputStream.close();
        return playingBoard;
    }
}