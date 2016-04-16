package game.marubatsu.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 盤の状態をMapで管理するゲームステータス。
 * Mapですることでクッソ見辛いコードになってる。
 */
public class GameStatusMap extends GameStatus {
    private Map<String, String> board;
    public final static String[] KEYS = {"A", "B", "C",
                                         "D", "E", "F",
                                         "G", "H", "I"};
    public final static int BOARD_SIZE = 3;

    public GameStatusMap() {
        init();
    }

    @Override
    public void initBoard() {
        board = new HashMap<String, String>();
    }

    @Override
    public String getBoardString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= BOARD_SIZE * BOARD_SIZE; i++) {
            builder.append(getSpot(KEYS[i-1]));
            if (i % BOARD_SIZE == 0) {
                builder.append(System.getProperty("line.separator"));
                if (i != BOARD_SIZE * BOARD_SIZE) {
                    for (int j = 0; j < BOARD_SIZE; j++) {
                        builder.append("---");
                    }
                    builder.append(System.getProperty("line.separator"));
                }
            } else {
                builder.append(" | ");
            }
        }
        return builder.toString();
    }

    @Override
    public boolean isEndGame() {
        if (board.size() >= BOARD_SIZE * BOARD_SIZE) {
            return true;
        }
        if (checkHorizontal() != null || checkVertical() != null || checkDiagonal() != null) {
            return true;
        }
        return false;
    }

    /**
     * 横方向で並んでるかをチェックする。
     * 並んでいない場合はnullを返す。
     * @return 並んでいるシンボル("○" or "×")
     */
    private String checkHorizontal() {
        String checker = null;
        for (int i = 1; i <= BOARD_SIZE * BOARD_SIZE; i++) {
            String value = board.get(KEYS[i-1]);
            if ((i-1) % BOARD_SIZE == 0) {
                checker = value;
                continue;
            }

            if (value == null) {
                checker = null;
            } else {
                if (!value.equals(checker)) {
                    checker = null;
                }
            }
            if (i % BOARD_SIZE == 0 && checker != null) {
                return checker;
            }
        }
        return null;
    }

    /**
     * 縦方向で並んでるかをチェックする。
     * 並んでいない場合はnullを返す。
     * @return 並んでいるシンボル("○" or "×")
     */
    private String checkVertical() {
        String[] checkers = new String[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            checkers[i] = board.get(KEYS[i]);
        }
        for (int i = BOARD_SIZE; i < BOARD_SIZE * BOARD_SIZE; i++) {
            String value = board.get(KEYS[i]);
            if (value == null) {
                checkers[i%BOARD_SIZE] = null;
            } else {
                if (!value.equals(checkers[i%BOARD_SIZE])) {
                    checkers[i%BOARD_SIZE] = null;
                }
            }
        }
        for (String checker : checkers) {
            if (checker != null) {
                return checker;
            }
        }
        return null;
    }

    /**
     * 斜め方向で並んでるかをチェックする。
     * 並んでいない場合はnullを返す。
     * @return 並んでいるシンボル("○" or "×")
     */
    private String checkDiagonal() {
        String checker = board.get(KEYS[0]);
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i = i + BOARD_SIZE + 1) {
            String value = board.get(KEYS[i]);
            if (value == null) {
                checker = null;
            } else {
                if (!value.equals(checker)) {
                    checker = null;
                }
            }
        }
        if (checker != null) {
            return checker;
        }

        checker = board.get(KEYS[BOARD_SIZE-1]);
        for (int i = BOARD_SIZE - 1; i < BOARD_SIZE * BOARD_SIZE - 1; i = i + BOARD_SIZE -1) {
            String value = board.get(KEYS[i]);
            if (value == null) {
                checker = null;
            } else {
                if (!value.equals(checker)) {
                    checker = null;
                }
            }
        }
        if (checker != null) {
            return checker;
        }
        return null;
    }

    /**
     * 盤に配置されているシンボルを取得する。
     * 何も配置されていない場合はスペースを返す。
     * @param spot 盤の位置(key)
     * @return 盤に配置されているシンボル
     */
    private String getSpot(String spot) {
        String spotStr = board.get(spot);
        if(spotStr == null) {
            spotStr = " ";
        }
        return spotStr;
    }

    @Override
    public int getCanPutCount() {
        return BOARD_SIZE * BOARD_SIZE - board.size();
    }

    @Override
    public Object getBoard() {
        return board;
    }

    @Override
    public int whichWinner() {
        String winSymbol = checkHorizontal();
        if (winSymbol == null) {
            winSymbol = checkVertical();
        }
        if (winSymbol == null) {
            winSymbol = checkDiagonal();
        }

        if (winSymbol == null) {
            return 0;
        } else if (winSymbol.equals(FIRST_SYMBOL)) {
            if (getIsFirstPlayerTurn()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            if (getIsFirstPlayerTurn()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
