package game.marubatsu.util;

import game.marubatsu.status.GameStatus;

/**
 * ゲーム進行上、利用するメソッドのutilクラス。
 * Mainクラスに書くのが嫌だったため切り出し。
 * 主に出力を担当する。
 */
public class GameUtil {
    /**
     * 先攻の表示を行う。
     * @param status ゲームステータス
     */
    public static void printFirstTurn(GameStatus status) {
        boolean isFirstPlayerTurn = status.getIsFirstPlayerTurn();
        if(isFirstPlayerTurn) {
            System.out.println("プレイヤーが先攻です。");
        } else {
            System.out.println("コンピュータが先攻です。");
        }
    }

    /**
     * 盤の状態を表示する。
     * 処理はGameStatusに委譲。
     * @param status ゲームステータス
     */
    public static void printBoard(GameStatus status) {
        System.out.println(status.getBoardString());
    }

    /**
     * 勝者を表示する。
     * @param status ゲームステータス
     */
    public static void printWinner(GameStatus status) {
        switch(status.whichWinner()) {
            case -1:
                System.out.println("コンピュータの勝ち");
                break;
            case 1:
                System.out.println("プレイヤーの勝ち");
                break;
            default:
                System.out.println("引き分け");
                break;
        }
    }
}
