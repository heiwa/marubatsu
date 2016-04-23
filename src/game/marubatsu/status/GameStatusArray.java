package game.marubatsu.status;

import sun.font.FontRunIterator;

/**
 * 盤を配列で表現したゲームステータス
 */
public class GameStatusArray extends GameStatus {
    private String[][] board;
    public static final int BOARD_SIZE = 3;

    @Override
    public void initBoard() {
        board = new String[BOARD_SIZE][BOARD_SIZE];
    }

    @Override
    public String getBoardString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                builder.append(getSpot(board[i][j]));
                if (j == BOARD_SIZE - 1) {
                    builder.append(System.getProperty("line.separator"));
                } else {
                    builder.append(" | ");
                }
            }
            if (i != BOARD_SIZE - 1) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    builder.append("---" );
                }
                builder.append(System.getProperty("line.separator"));
            }
        }
        return builder.toString();
    }
    private String getSpot(String spot) {
        if (spot == null) {
            spot = " ";
        }
        return spot;
    }

    @Override
    public boolean isEndGame() {
        if (getCanPutCount() <= 0) {
            return true;
        }

        String[] verticalChecker = new String[BOARD_SIZE];
        for (int j = 0; j < BOARD_SIZE; j++) {
            verticalChecker[j] = board[0][j];
        }
        String diagonalChecker1 = board[0][0];
        String diagonalChecker2 = board[0][BOARD_SIZE - 1];
        for (int i = 0; i < BOARD_SIZE; i++) {
            String horizontalChecker = board[i][0];
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (horizontalChecker != null) {
                    if (!horizontalChecker.equals(board[i][j])) {
                        horizontalChecker = null;
                    }
                }
                if (verticalChecker[j] != null) {
                    if (!verticalChecker[j].equals(board[i][j])) {
                        verticalChecker[j] = null;
                    }
                }
                if (i == j) {
                    if (diagonalChecker1 != null) {
                        if (!diagonalChecker1.equals(board[i][j])) {
                            diagonalChecker1 = null;
                        }
                    }
                }
                if (i + j == BOARD_SIZE - 1) {
                    if (diagonalChecker2 != null) {
                        if (!diagonalChecker2.equals(board[i][j])) {
                            diagonalChecker2 = null;
                        }
                    }
                }
            }
            if (horizontalChecker != null) {
                return true;
            }
        }
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (verticalChecker[j] != null) {
                return true;
            }
        }
        if (diagonalChecker1 != null || diagonalChecker2 != null) {
            return true;
        }

        return false;
    }



    @Override
    public int getCanPutCount() {
        int count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == null) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Object getBoard() {
        return board;
    }

    @Override
    public int whichWinner() {
        String[] verticalChecker = new String[BOARD_SIZE];
        for (int j = 0; j < BOARD_SIZE; j++) {
            verticalChecker[j] = board[0][j];
        }
        String diagonalChecker1 = board[0][0];
        String diagonalChecker2 = board[0][BOARD_SIZE - 1];
        for (int i = 0; i < BOARD_SIZE; i++) {
            String horizontalChecker = board[i][0];
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (horizontalChecker != null) {
                    if (!horizontalChecker.equals(board[i][j])) {
                        horizontalChecker = null;
                    }
                }
                if (verticalChecker[j] != null) {
                    if (!verticalChecker[j].equals(board[i][j])) {
                        verticalChecker[j] = null;
                    }
                }
                if (i == j) {
                    if (diagonalChecker1 != null) {
                        if (!diagonalChecker1.equals(board[i][j])) {
                            diagonalChecker1 = null;
                        }
                    }
                }
                if (i + j == BOARD_SIZE - 1) {
                    if (diagonalChecker2 != null) {
                        if (!diagonalChecker2.equals(board[i][j])) {
                            diagonalChecker2 = null;
                        }
                    }
                }
            }
            if (horizontalChecker != null) {
                return convertWinner(horizontalChecker);
            }
        }
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (verticalChecker[j] != null) {
                return convertWinner(verticalChecker[j]);
            }
        }
        if (diagonalChecker1 != null) {
            return convertWinner(diagonalChecker1);
        }
        if (diagonalChecker2 != null) {
            return convertWinner(diagonalChecker2);
        }

        return 0;
    }
    private int convertWinner(String winnerSymbol) {
        if (winnerSymbol == null) {
            return 0;
        }
        if (winnerSymbol.equals(FIRST_SYMBOL) && getIsFirstPlayerTurn()) {
            return 1;
        } else if (winnerSymbol.equals(SECOND_SYMBOL) && !getIsFirstPlayerTurn()) {
            return 1;
        } else {
            return -1;
        }
    }
}
