package danelkis.maman13.maman134inarow;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class AppState {

    private static final int EMPTY_SQUARE = 0;
    private static final int BOARD_WIDTH = 7;
    private static final int BOARD_HEIGHT = 6;

    private int totalPlayers;
    private int currentPlayer;
    private int turnCounter = 0;
    private int[][] board; // 0 means empty, 1 means player 1, 2 means player 2, etc...
    private Shape[][] shapesBoard; // will hold the shapes, to easily draw line later

    private static AppState instance;

    private GridPane grid;

    private Button[] buttons = new Button[7];

    private List<Circle> circles = new ArrayList<>();

    private AppState() {
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        shapesBoard = new Shape[BOARD_HEIGHT][BOARD_WIDTH];
        currentPlayer = 1; // Player 1 starts
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }

        return instance;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    public void insertCoin(int column) {
        System.out.println("Inserting coin to column " + column);

        if (column < 0 || column >= 7) {
            throw new IllegalArgumentException("Invalid column number: " + column);
        }

        if (board[0][column] != EMPTY_SQUARE) {
            throw new IllegalArgumentException("Column is full");
        }

        for (int i = this.BOARD_HEIGHT - 1; i >= 0; i--) {
            if (board[i][column] == EMPTY_SQUARE) {
                board[i][column] = currentPlayer;
                String color = ColorFactory.getColor(currentPlayer);
                Circle circle = new Circle(0, 0, 20);
                circle.setFill(Color.web(color));
                grid.add(circle, column, i);
                circles.add(circle);
                shapesBoard[i][column] = circle;
                break;
            }
        }

        switchTurn();

        // if column is no longer insertable, disable the button
        if (board[0][column] != EMPTY_SQUARE) {
            buttons[column].setDisable(true);
        }
    }

    public void switchTurn() {
        int nextPlayer;
        if (currentPlayer == totalPlayers) {
            nextPlayer = 1; // We start from 1, because 0 is reserved for empty square.
        } else {
            nextPlayer = currentPlayer + 1;
        }

        System.out.println(STR."Switching player from \{currentPlayer} to \{nextPlayer}");
        currentPlayer = nextPlayer;
        turnCounter++;
    }

    public GameResult getGameResult() {
        int winningPlayer = 0; // 0 means no one is winning
        boolean isBoardFull = turnCounter == BOARD_WIDTH * BOARD_HEIGHT;
        Win win = null;

        // Check for horizontal win
        for (int playerIdx = 1; playerIdx < totalPlayers + 1; playerIdx++) {
            for (int startingColumnIndex = 0; startingColumnIndex < BOARD_WIDTH - 4; startingColumnIndex++) {
                for (int startingRowIndex = 0; startingRowIndex < 6; startingRowIndex++) {
                    if (board[startingRowIndex][startingColumnIndex] == playerIdx && board[startingRowIndex][startingColumnIndex + 1] == playerIdx && board[startingRowIndex][startingColumnIndex + 2] == playerIdx && board[startingRowIndex][startingColumnIndex + 3] == playerIdx) {
                        winningPlayer = playerIdx;
                        Shape startCircle = shapesBoard[startingRowIndex][startingColumnIndex];
                        Shape endCircle = shapesBoard[startingRowIndex][startingColumnIndex + 3];

                        for (int i = 0; i < 4; i++) {
                            Circle circle = (Circle) shapesBoard[startingRowIndex][startingColumnIndex + i];
                            circle.setFill(Color.GREEN);
                        }

                        win = new Win(WinType.HORIZONTAL, startCircle, endCircle, playerIdx);
                    }
                }
            }
        }

        // Check for vertical win
        for (int playerIdx = 1; playerIdx < totalPlayers + 1; playerIdx++) {
            for (int startingColumnIndex = 0; startingColumnIndex < BOARD_WIDTH; startingColumnIndex++) {
                for (int startingRowIndex = 0; startingRowIndex < 3; startingRowIndex++) {
                    if (board[startingRowIndex][startingColumnIndex] == playerIdx && board[startingRowIndex + 1][startingColumnIndex] == playerIdx && board[startingRowIndex + 2][startingColumnIndex] == playerIdx && board[startingRowIndex + 3][startingColumnIndex] == playerIdx) {
                        winningPlayer = playerIdx;

                        for (int i = 0; i < 4; i++) {
                            Circle circle = (Circle) shapesBoard[startingRowIndex + i][startingColumnIndex];
                            circle.setFill(Color.GREEN);
                        }

                        win = new Win(WinType.VERTICAL, shapesBoard[startingRowIndex][startingColumnIndex], shapesBoard[startingRowIndex + 3][startingColumnIndex], playerIdx);
                    }
                }
            }
        }

        // Check for diagonal win (top-left to bottom-right)
        for (int playerIdx = 1; playerIdx < totalPlayers + 1; playerIdx++) {
            for (int startingColumnIndex = 0; startingColumnIndex < BOARD_WIDTH - 3; startingColumnIndex++) {
                for (int startingRowIndex = 0; startingRowIndex < 3; startingRowIndex++) {
                    if (board[startingRowIndex][startingColumnIndex] == playerIdx && board[startingRowIndex + 1][startingColumnIndex + 1] == playerIdx && board[startingRowIndex + 2][startingColumnIndex + 2] == playerIdx && board[startingRowIndex + 3][startingColumnIndex + 3] == playerIdx) {
                        winningPlayer = playerIdx;

                        for (int i = 0; i < 4; i++) {
                            Circle circle = (Circle) shapesBoard[startingRowIndex + i][startingColumnIndex + i];
                            circle.setFill(Color.GREEN);
                        }

                        win = new Win(WinType.DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT, shapesBoard[startingRowIndex][startingColumnIndex], shapesBoard[startingRowIndex + 3][startingColumnIndex + 3], playerIdx);
                    }
                }
            }
        }

        // Check for diagonal win (bottom-left to top-right)
        for (int playerIdx = 1; playerIdx < totalPlayers + 1; playerIdx++) {
            for (int startingColumnIndex = 0; startingColumnIndex < BOARD_WIDTH - 3; startingColumnIndex++) {
                for (int startingRowIndex = 3; startingRowIndex < 6; startingRowIndex++) {
                    if (board[startingRowIndex][startingColumnIndex] == playerIdx && board[startingRowIndex - 1][startingColumnIndex + 1] == playerIdx && board[startingRowIndex - 2][startingColumnIndex + 2] == playerIdx && board[startingRowIndex - 3][startingColumnIndex + 3] == playerIdx) {
                        winningPlayer = playerIdx;

                        for (int i = 0; i < 4; i++) {
                            Circle circle = (Circle) shapesBoard[startingRowIndex - i][startingColumnIndex + i];
                            circle.setFill(Color.GREEN);
                        }

                        win = new Win(WinType.DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT, shapesBoard[startingRowIndex][startingColumnIndex], shapesBoard[startingRowIndex - 3][startingColumnIndex + 3], playerIdx);
                    }
                }
            }
        }

        boolean isGameOver = winningPlayer != 0 || isBoardFull;
        boolean isWin = winningPlayer != 0;

        return new GameResult(isGameOver, isWin, winningPlayer, win);

    }

    public void reset() {
        turnCounter = 0;
        currentPlayer = 1;
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        shapesBoard = new Shape[BOARD_HEIGHT][BOARD_WIDTH];

        for (Button button : buttons) {
            button.setDisable(false);
        }

        for (Circle circle : circles) {
            grid.getChildren().remove(circle);
        }
    }

}
