package game.marubatsu.player;

import game.marubatsu.status.GameStatus;

/**
 * プレイヤー（打ち手）のインターフェース
 */
public interface Player {
    /**
     * ゲームステータスを受け取り、自分の一手を実行する。
     * 実装クラスはゲームステータスの型を知っている体。
     * @param status ゲームステータス
     */
    public void play(GameStatus status);
}
