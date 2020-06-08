
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class BoardTest {

    Board temp;

    @Before
    public void setUp() {
        temp = new Board();
    }

    @Test
    public void testEmptyBoardMethod() {
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=0;
            }
        }
        temp.emptyBoard();
        assertArrayEquals(boardToCompare, temp.getBoard());
    }

    @Test
    public void testGetPoints() {
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        int sum=1;
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                sum+=kolumna;
            }
        }
        temp.emptyBoard();
        assertEquals(sum, temp.getPoints());
    }

    @Test
    public void testGetBoard() {
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=5;
            }
        }
        for (int[] wiersz : temp.getBoard()) {
            for (int kolumna : wiersz) {
                kolumna=5;
            }
        }
        assertArrayEquals(boardToCompare, temp.getBoard());
    }

    @Test
    public void testGetBoardElement() {
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=5;
            }
        }
        for (int[] wiersz : temp.getBoard()) {
            for (int kolumna1 : wiersz) {
                kolumna1=5;
            }
        }
        assertEquals(boardToCompare[Board.SIZE-1][Board.SIZE-1],temp.getBoardElement(Board.SIZE-1,Board.SIZE-1));
    }

    @Test
    public void testSetBoard() {
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        Random rand = new Random();
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=rand.nextInt(5);
            }
        }
        temp.setBoard(boardToCompare);
        assertArrayEquals(boardToCompare, temp.getBoard());
    }

    @Test
    public void testAddNewRandomToBoard() {
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=0;
            }
        }
        temp.emptyBoard();
        temp.addNewRandomToBoard();
        assertFalse(Arrays.deepEquals(boardToCompare, temp.getBoard()));
    }

    @Test
    public void testRotateBoard() {
        Random rand = new Random();
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=rand.nextInt(8);
            }
        }
        temp.setBoard(boardToCompare);
        temp.rotateBoard(temp.getBoard(), 4);
        assertArrayEquals(boardToCompare, temp.getBoard());
    }

    @Test
    public void testMoveIsPossible() {
        temp.addNewRandomToBoard();
        temp.addNewRandomToBoard();
        temp.addNewRandomToBoard();
        assertTrue(temp.moveIsPossible(temp.getBoard(),false));

    }

    @Test
    public void testWinCheck() {
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=0;
            }
        }
        boardToCompare[1][1] = 2048;
        temp.setBoard(boardToCompare);
        assertTrue(temp.winCheck(temp.getBoard()));
    }

    @Test
    public void testLooseCheck() {
        int i =1;
        int[][] boardToCompare = new int[Board.SIZE][Board.SIZE];
        for (int[] wiersz : boardToCompare) {
            for (int kolumna : wiersz) {
                kolumna=i;
                i++;
            }
        }
        temp.setBoard(boardToCompare);
        assertTrue(temp.looseCheck(temp.getBoard()));
    }

    @After
    public void teardown(){
        temp = null;
    }

}
