package game.marubatsu.player;

import game.marubatsu.status.GameStatus;
import game.marubatsu.status.GameStatusArray;

import java.util.Random;

/**
 * 空いてる場所にランダムに配置するコンピュータクラス
 */
public class ComputerUsingArray implements Player{
    @Override
    public void play(GameStatus status) {
        String[][] board = (String[][])status.getBoard();
        int putSpot = new Random().nextInt(status.getCanPutCount());
        int count = 0;
        for (int i = 0; i < GameStatusArray.BOARD_SIZE; i++) {
            for (int j = 0; j < GameStatusArray.BOARD_SIZE; j++) {
                if (board[i][j] != null) {
                    count--;
                }
                if (count == putSpot) {
                    board[i][j] = mySymbol(status.getIsFirstPlayerTurn());
                    return;
                }
                count++;
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
