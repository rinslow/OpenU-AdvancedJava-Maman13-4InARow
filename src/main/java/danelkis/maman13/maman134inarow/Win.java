package danelkis.maman13.maman134inarow;

import javafx.scene.shape.Shape;

public class Win {
    private final WinType winType;
    private final Shape from;
    private final Shape to;

    private final int winningPlayer;

    public Win(WinType winType, Shape from, Shape to, int winningPlayer) {
        this.winType = winType;
        this.from = from;
        this.to = to;
        this.winningPlayer = winningPlayer;
    }

    public WinType getWinType() {
        return winType;
    }

    public Shape getFrom() {
        return from;
    }

    public Shape getTo() {
        return to;
    }

    public int getWinningPlayer() {
        return winningPlayer;
    }

}
