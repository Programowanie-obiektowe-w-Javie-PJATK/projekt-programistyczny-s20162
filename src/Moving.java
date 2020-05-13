
public class Moving {
    public void moveUp(int[][] board) {
        int i=1;
        partialMovinUp(board,i);
        i=2;
        for (int l=0 ; l<2 ; l++, i--) {
            partialMovinUp(board, i);
        }
        i=3;
        for (int l=0 ; l<3 ; l++, i--) {
            partialMovinUp(board, i);
        }

    }

    private void partialMovinUp(int[][] board, int i) {
        for (int j = 0; j < 4; j++) {
            if (board[i][j] != 0) {
                if (board[i - 1][j] == 0) {
                    board[i - 1][j] = board[i][j];
                    board[i][j] = 0;
                } else if (board[i - 1][j] == board[i][j]) {
                    board[i - 1][j] = 2 * board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }

    public void moveDown(int[][] board) {
        int i=2;
        partialMovinDown(board,i);
        i=1;
        for (int l=0 ; l<2 ; l++, i++) {
            partialMovinDown(board, i);
        }
        i=0;
        for (int l=0 ; l<3 ; l++, i++) {
            partialMovinDown(board, i);
        }

    }

    private void partialMovinDown(int[][] board, int i) {
        for (int j = 0; j < 4; j++) {
            if (board[i][j] != 0) {
                if (board[i + 1][j] == 0) {
                    board[i + 1][j] = board[i][j];
                    board[i][j] = 0;
                } else if (board[i + 1][j] == board[i][j]) {
                    board[i + 1][j] = 2 * board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }

    public void moveLeft(int[][] board) {
        int j=1;
        partialMovinLeft(board, j);
        j=2;
        for (int l=0 ; l<2 ; l++, j--) {
            partialMovinLeft(board, j);
        }
        j=3;
        for (int l=0; l<3 ; l++,j--) {
            partialMovinLeft(board,j);
        }
    }

    private void partialMovinLeft(int[][] board, int j) {
        for (int i = 0; i < 4; i++) {
            if (board[i][j] != 0) {
                if (board[i][j - 1] == 0) {
                    board[i][j - 1] = board[i][j];
                    board[i][j] = 0;
                } else if (board[i][j - 1] == board[i][j]) {
                    board[i][j - 1] = 2*board[i][j];
                    board[i][j]=0;
                }
            }
        }
    }

    public void moveRight(int[][] board) {
        int j=2;
        partialMovinRight(board, j);
        j=1;
        for (int l=0 ; l<2 ; l++, j++) {
            partialMovinRight(board, j);
        }
        j=0;
        for (int l=0; l<3 ; l++,j++) {
            partialMovinRight(board,j);
        }
    }

    private void partialMovinRight(int[][] board,int j) {
        for (int i = 0; i < 4; i++) {
            if (board[i][j] != 0) {
                if (board[i][j + 1] == 0) {
                    board[i][j + 1] = board[i][j];
                    board[i][j] = 0;
                } else if (board[i][j + 1] == board[i][j]) {
                    board[i][j + 1] = 2*board[i][j];
                    board[i][j]=0;
                }
            }
        }

    }






}
