package game.marubatsu.player;

import game.marubatsu.status.GameStatus;
import game.marubatsu.status.GameStatusMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 僕の考えた最強のAI。
 * （後攻のとき負ける可能性あります。）
 */
public class SaikyoPlayerUsingMap implements Player {

    @Override
    public void play(GameStatus status) {
        Map<String, String> board = (Map<String, String>)status.getBoard();
        String mySymbol = getMySymbol(status);
        String otherSymbol = getOtherSymbol(status);
        // 勝ち手
        String putSpot = searchLastOneSpot(board, mySymbol);
        // 負けない手
        if (putSpot == null) {
            putSpot = searchLastOneSpot(board, otherSymbol);
        }
        // 優先順位
        if (putSpot == null) {
            putSpot = searchPriorySpot(board, mySymbol);
        }
        board.put(putSpot, mySymbol);
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
    private String searchLastOneSpot(Map<String, String> board, String mySymbol) {
        List<Integer> myPos = new ArrayList<Integer>();
        for (int i = 0; i < GameStatusMap.BOARD_SIZE * GameStatusMap.BOARD_SIZE; i++) {
            if (mySymbol.equals(board.get(GameStatusMap.KEYS[i]))) {
                myPos.add(i);
            }
        }
        if (myPos.size() < GameStatusMap.BOARD_SIZE - 1) {
            return null;
        }
        String winSpot = searchHorizontalLastOneSpot(board, myPos);
        if (winSpot == null) {
            winSpot = searchVerticalLastOneSpot(board, myPos);
        }
        if (winSpot == null) {
            winSpot = searchDiagonalLastOneSpot(board, myPos);
        }
        return winSpot;
    }

    /**
     * 横方向にあと一つで並ぶ部分を探索する。
     * @param board 盤
     * @param myPos 盤に配置してる自分の場所
     * @return あと一つで並ぶ空きの部分(key)
     */
    private String searchHorizontalLastOneSpot(Map<String, String> board, List<Integer> myPos) {
        List<Integer>[] lines = new List[GameStatusMap.BOARD_SIZE];
        for (int i = 0; i < GameStatusMap.BOARD_SIZE; i++) {
            lines[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < GameStatusMap.BOARD_SIZE; i++) {
            for (int j = 0; j < GameStatusMap.BOARD_SIZE; j++) {
                lines[j].add(i + j * GameStatusMap.BOARD_SIZE);
            }
        }

        return searchLastOneSpot(board, myPos, lines);
    }
    /**
     * 縦方向にあと一つで並ぶ部分を探索する。
     * @param board 盤
     * @param myPos 盤に配置してる自分の場所
     * @return あと一つで並ぶ空きの部分(key)
     */
    private String searchVerticalLastOneSpot(Map<String, String> board, List<Integer> myPos) {
        List<Integer>[] lines = new List[GameStatusMap.BOARD_SIZE];
        for (int i = 0; i < GameStatusMap.BOARD_SIZE; i++) {
            lines[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < GameStatusMap.BOARD_SIZE; i++) {
            for (int j = 0; j < GameStatusMap.BOARD_SIZE; j++) {
                lines[j].add(i * GameStatusMap.BOARD_SIZE + j);
            }
        }

        return searchLastOneSpot(board, myPos, lines);
    }
    /**
     * 斜め方向にあと一つで並ぶ部分を探索する。
     * @param board 盤
     * @param myPos 盤に配置してる自分の場所
     * @return あと一つで並ぶ空きの部分(key)
     */
    private String searchDiagonalLastOneSpot(Map<String, String> board, List<Integer> myPos) {
        List<Integer>[] lines = new List[2];
        for (int i = 0; i < 2; i++) {
            lines[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < GameStatusMap.BOARD_SIZE * GameStatusMap.BOARD_SIZE; i = i + GameStatusMap.BOARD_SIZE + 1) {
            lines[0].add(i);
        }
        for (int i = GameStatusMap.BOARD_SIZE - 1; i < GameStatusMap.BOARD_SIZE * GameStatusMap.BOARD_SIZE - 1; i = i + GameStatusMap.BOARD_SIZE - 1) {
            lines[1].add(i);
        }

        return searchLastOneSpot(board, myPos, lines);
    }
    /**
     * 縦横斜め方向にあと一つで並ぶ部分を探索する処理の共通部分。
     * @param board 盤
     * @param myPos 盤に配置してる自分の場所
     * @param lines 探索対象のライン
     * @return あと一つで並ぶ空きの部分(key)
     */
    private String searchLastOneSpot(Map<String, String> board, List<Integer> myPos, List<Integer>[] lines) {
        for (int i = 0; i < lines.length; i++) {
            List<Integer> checker = new ArrayList<Integer>(myPos);
            checker.retainAll(lines[i]);
            if (checker.size() == GameStatusMap.BOARD_SIZE -1) {
                List<Integer> cloneMyPos = new ArrayList<Integer>(myPos);
                lines[i].removeAll(cloneMyPos);
                Integer putPos = lines[i].get(0);
                if (board.get(GameStatusMap.KEYS[putPos]) == null) {
                    return GameStatusMap.KEYS[putPos];
                }
            }
        }
        return null;
    }

    /**
     * 勝つために配置する場所の優先順位を決めて配置する場所を返す。
     * このメソッドで場所を確定する(nullを返すことは許されない）。
     * @param board 盤
     * @param mySymbol 自分のシンボル
     * @return 配置する場所(key)
     */
    private String searchPriorySpot(Map<String, String> board, String mySymbol) {
        int center = (GameStatusMap.BOARD_SIZE - 1) / 2  * (GameStatusMap.BOARD_SIZE + 1);
        if (board.get(GameStatusMap.KEYS[center]) == null) {
            return GameStatusMap.KEYS[center];
        }
        int corner1 = 0;
        int corner2 = GameStatusMap.BOARD_SIZE - 1;
        int corner3 = GameStatusMap.BOARD_SIZE * (GameStatusMap.BOARD_SIZE - 1);
        int corner4 = GameStatusMap.BOARD_SIZE * GameStatusMap.BOARD_SIZE - 1;
        if (board.get(GameStatusMap.KEYS[corner1]) == null && board.get(GameStatusMap.KEYS[corner4]) == null) {
            return GameStatusMap.KEYS[corner1];
        }
        if (board.get(GameStatusMap.KEYS[corner2]) == null && board.get(GameStatusMap.KEYS[corner3]) == null) {
            return GameStatusMap.KEYS[corner2];
        }
        if (board.get(GameStatusMap.KEYS[corner1]) == null) {
            return GameStatusMap.KEYS[corner1];
        }
        if (board.get(GameStatusMap.KEYS[corner2]) == null) {
            return GameStatusMap.KEYS[corner2];
        }
        if (board.get(GameStatusMap.KEYS[corner3]) == null) {
            return GameStatusMap.KEYS[corner3];
        }
        if (board.get(GameStatusMap.KEYS[corner4]) == null) {
            return GameStatusMap.KEYS[corner4];
        }
        for (String put : GameStatusMap.KEYS) {
            if (board.get(put) == null) {
                return put;
            }
        }
        return null; // unreachable
    }
}
