package danelkis.maman13.maman134inarow;


public class GameResult {
    private final boolean isGameOver;
    private final boolean isWin; // if game over and not is win then draw
    private final int winningPlayer;
    private final Win win; // if the game is won, this will contain the win details

    public GameResult(boolean isGameOver, boolean isWin, int winningPlayer, Win win) {
        this.isGameOver = isGameOver;
        this.isWin = isWin;
        this.winningPlayer = winningPlayer;
        this.win = win;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public int getWinningPlayer() {
        return winningPlayer;
    }

    public Win getWin() {
        return win;
    }

}
