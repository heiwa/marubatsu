package game.marubatsu;

import game.marubatsu.player.ComputerUsingMap;
import game.marubatsu.player.Player;
import game.marubatsu.player.RandomPlayerUsingMap;
import game.marubatsu.player.SaikyoPlayerUsingMap;
import game.marubatsu.status.GameStatus;
import game.marubatsu.status.GameStatusMap;
import game.marubatsu.util.GameUtil;

/**
 * ○×ゲームのメインクラス
 */
public class Main {

    /**
     * ○×ゲームを動かす。
     * @param args 使用しないコマンドライン引数
     */
    public static void main(String[] args) {
        System.out.println("main");
        GameStatus gameStatus = new GameStatusMap();
        Player[] players = createPlayers();
        GameUtil.printFirstTurn(gameStatus);
        while (!gameStatus.isEndGame()) {
            getPlayer(players, gameStatus.getIsPlayerTurnNow()).play(gameStatus);
            GameUtil.printBoard(gameStatus);
            gameStatus.nextTurn();
        }
        GameUtil.printWinner(gameStatus);
    }

    /**
     * プレイヤーとコンピュータを生成する。
     * @return プレイヤー、コンピュータの配列。
     */
    private static Player[] createPlayers() {
        Player[] players = new Player[2];
        players[0] = new SaikyoPlayerUsingMap(); // 自作した場合は書き換え
        players[1] = new ComputerUsingMap();
        return players;
    }

    /**
     * ターンのプレイヤーを取得する。
     * @param players プレイヤーの配列
     * @param isPlayerTurn プレイヤーのターンならtrue
     * @return ターンのプレイヤー
     */
    private static Player getPlayer(Player[] players, boolean isPlayerTurn) {
        if (isPlayerTurn) {
            return players[0];
        }
        return players[1];
    }
}
