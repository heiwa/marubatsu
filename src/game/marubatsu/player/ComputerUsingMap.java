package game.marubatsu.player;

import game.marubatsu.status.GameStatus;
import game.marubatsu.status.GameStatusMap;

import java.util.Map;
import java.util.Random;

/**
 * 空いてる場所にランダムに配置するコンピュータクラス
 */
public class ComputerUsingMap implements Player {
    @Override
    public void play(GameStatus status) {
        int putSpot = new Random().nextInt(status.getCanPutCount());
        Map<String, String> board = (Map<String, String>) status.getBoard();
        for (int i = 0, j = 0; i < GameStatusMap.BOARD_SIZE * GameStatusMap.BOARD_SIZE; i++, j++) {
            if (board.get(GameStatusMap.KEYS[i]) != null) {
                j--;
            }
            if (j == putSpot) {
                board.put(GameStatusMap.KEYS[i], mySymbol(status.getIsFirstPlayerTurn()));
                break;
            }
        }
    }

    /**
     * 自分のシンボルを取得する
     * @param isSecond 後攻フラグ
     * @return 自分のシンボル
     */
    private String mySymbol(boolean isSecond) {
        if (isSecond) {
            return GameStatus.SECOND_SYMBOL;
        }
        return GameStatus.FIRST_SYMBOL;
    }
}
