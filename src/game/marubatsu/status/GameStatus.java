package game.marubatsu.status;

import java.util.Random;

/**
 * ゲームステータスを扱う抽象クラス。
 * このクラスのサブクラスで盤の状態を記述する。
 */
public abstract class GameStatus {
    private boolean isFirstPlayerTurn;
    private boolean isPlayerTurnNow;
    public static final String FIRST_SYMBOL = "○";
    public static final String SECOND_SYMBOL = "×";

    /**
     * ゲームステータス初期化
     */
    public void init() {
        initBoard();
        initTurn(new Random().nextBoolean());
    }

    /**
     * 盤の状態を初期化する。
     */
    abstract public void initBoard();

    /**
     * ターンの状態を初期化する。
     * @param isFirstPlayerTurn
     */
    public void initTurn(boolean isFirstPlayerTurn) {
        this.isFirstPlayerTurn = isFirstPlayerTurn;
        this.isPlayerTurnNow = isFirstPlayerTurn;
    }

    /**
     * プレイヤーが先攻かどうかを取得する。
     * @return プレイヤーが先攻ならtrue
     */
    public boolean getIsFirstPlayerTurn() {
        return isFirstPlayerTurn;
    }
    /**
     * プレイヤーのターンかどうかを取得する。
     * @return プレイヤーのターンならtrue
     */
    public boolean getIsPlayerTurnNow() {
        return isPlayerTurnNow;
    }

    /**
     * ターンを交代する。
     */
    public void nextTurn() {
        isPlayerTurnNow = !isPlayerTurnNow;
    }
    /**
     * 盤の状態を文字列で取得する。
     * 例
     * ○ |   | ×
     *   | ○ |
     *   |   | ×
     * @return 盤の状態の文字列
     */
    abstract public String getBoardString();

    /**
     * 盤の状態でゲームが終わっているかどうかを判定する。
     * @return ゲームが終了していたらtrue
     */
    abstract public boolean isEndGame();

    /**
     * 盤に残り何個配置する場所が残っているかを返す。
     * @return 残り配置可能個数
     */
    abstract public int getCanPutCount();

    /**
     * 盤を返す。
     * Player側では盤がどの型で定義されているか知っているのでキャストして使う。
     * @return 盤
     */
    abstract public Object getBoard();

    /**
     * 勝ち負けを返す。
     * プレイヤーが勝ちなら1,コンピュータが勝ちなら-1, 引き分けなら0。
     * @return 勝ち負け
     */
    abstract public int whichWinner();
}
