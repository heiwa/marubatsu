package game.marubatsu.player;

import game.marubatsu.status.GameStatus;
import game.marubatsu.status.GameStatusArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 僕の考えた最強のAI。
 * （後攻のとき負ける可能性あります。）
 */
public class SaikyoPlayerUsingArray implements Player {
    @Override
    public void play(GameStatus status) {
        String[][] board = (String[][])status.getBoard();
        String mySymbol = getMySymbol(status);
        String otherSymbol = getOtherSymbol(status);
        // 勝ち手
        Spot putSpot = searchLastOneSpot(board, mySymbol);
        // 負けない手
        if (putSpot == null) {
            putSpot = searchLastOneSpot(board, otherSymbol);
        }
        // 優先順位
        if (putSpot == null) {
            putSpot = searchPriorySpot(board, mySymbol);
        }
        board[putSpot.row][putSpot.column] = mySymbol;
    }

    /**
     * 自分のシンボルを取得する。
     * @param status ゲームステータス
     * @return 自分のシンボル
     */
    private String getMySymbol(GameStatus status) {
        if (status.getIsFirstPlayerTurn()) {
            return GameStatus.FIRST_SYMBOL;
        }
        return GameStatus.SECOND_SYMBOL;
    }

    /**
     * 相手のシンボルを取得する。
     * @param status ゲームステータス
     * @return 相手のシンボル
     */
    private String getOtherSymbol(GameStatus status) {
        if (status.getIsFirstPlayerTurn()) {
            return GameStatus.SECOND_SYMBOL;
        }
        return GameStatus.FIRST_SYMBOL;
    }

    /**
     * あと一つで並ぶところを探索する。
     * @param board 盤
     * @param mySymbol 探すシンボル
     * @return あと一つで並ぶ空きの部分(key)
     */
    private Spot searchLastOneSpot(String[][] board, String mySymbol) {
        List<Spot> myPos = new ArrayList<Spot>();
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            for (int j = 0; j < GameStatusArray.BOARD_SIZE; j++) {
                if (mySymbol.equals(board[i][j])) {
                    myPos.add(new Spot(i, j));
                }
            }
        }
        List<List<Spot>> lines = new ArrayList<List<Spot>>();

        lines.addAll(getHorizontalLines());
        lines.addAll(getVerticalLines());
        lines.addAll(getDiagonalLines());

        for (List<Spot> line : lines) {
            line.removeAll(myPos);
            if (line.size() == 1) {
                Spot spot = line.get(0);
                if (board[spot.row][spot.column] == null) {
                    return spot;
                }
            }
        }
        return null;
    }
    private List<List<Spot>> getHorizontalLines() {
        List<List<Spot>> lines = new ArrayList<List<Spot>>();
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            lines.add(new ArrayList<Spot>());
        }
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            for (int j = 0; j < GameStatusArray.BOARD_SIZE; j++) {
                lines.get(i).add(new Spot(i, j));
            }
        }
        return lines;
    }
    private List<List<Spot>> getVerticalLines() {
        List<List<Spot>> lines = new ArrayList<List<Spot>>();
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            lines.add(new ArrayList<Spot>());
        }
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            for (int j = 0; j < GameStatusArray.BOARD_SIZE; j++) {
                lines.get(j).add(new Spot(i, j));
            }
        }
        return lines;
    }
    private List<List<Spot>> getDiagonalLines() {
        List<List<Spot>> lines = new ArrayList<List<Spot>>();
        for (int i = 0; i < 2; i++) {
            lines.add(new ArrayList<Spot>());
        }
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            lines.get(0).add(new Spot(i, i));
            lines.get(1).add(new Spot(i, GameStatusArray.BOARD_SIZE - 1 - i));
        }
        return lines;
    }

    /**
     * 勝つために配置する場所の優先順位を決めて配置する場所を返す。
     * このメソッドで場所を確定する(nullを返すことは許されない）。
     * @param board 盤
     * @param mySymbol 自分のシンボル
     * @return 配置する場所(key)
     */
    private Spot searchPriorySpot(String[][] board, String mySymbol) {
        int middle = (GameStatusArray.BOARD_SIZE - 1) /2;
        if (board[middle][middle] == null) {
            return new Spot(middle, middle);
        }
        String corner1 = board[0][0];
        String corner2 = board[0][GameStatusArray.BOARD_SIZE - 1];
        String corner3 = board[GameStatusArray.BOARD_SIZE - 1][0];
        String corner4 = board[GameStatusArray.BOARD_SIZE - 1][GameStatusArray.BOARD_SIZE - 1];
        if (corner1 == null && corner4 == null) {
            return new Spot(0, 0);
        }
        if (corner2 == null && corner3 == null) {
            return new Spot(0, GameStatusArray.BOARD_SIZE - 1);
        }
        if (corner1 == null) {
            return new Spot(0, 0);
        }
        if (corner2 == null) {
            return new Spot(0, GameStatusArray.BOARD_SIZE - 1);
        }
        if (corner3 == null) {
            return new Spot(GameStatusArray.BOARD_SIZE - 1, 0);
        }
        if (corner4 == null) {
            return new Spot(GameStatusArray.BOARD_SIZE - 1, GameStatusArray.BOARD_SIZE - 1);
        }
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            for (int j = 0; j < GameStatusArray.BOARD_SIZE; j++) {
                if (board[i][j] == null) {
                    return new Spot(i, j);
                }
            }
        }
        return null; // unreachable
    }

    public static class Spot {
        int row;
        int column;

        public Spot(int r, int c) {
            row = r;
            column = c;
        }
        @Override
        public boolean equals(Object other) {
            if (((Spot)other).row == this.row && ((Spot)other).column == this.column) {
                return true;
            }
            return false;
        }
    }
}
