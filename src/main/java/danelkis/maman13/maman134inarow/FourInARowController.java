package danelkis.maman13.maman134inarow;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

public class FourInARowController {
    @FXML
    private Pane pane;

    @FXML
    private GridPane grid;

    @FXML
    private VBox vbox;

    @FXML private Button clearBtn;

    @FXML
    private Button button0, button1, button2, button3, button4, button5, button6;

    private Button[] buttons = new Button[7];

    @FXML
    protected void initialize() {
        initializeButtons();
        AppState state = AppState.getInstance();
        state.setTotalPlayers(2);
        state.setGrid(grid);
        state.setButtons(buttons);

        // Stretch grid to grow with window size
        grid.prefWidthProperty().bind(pane.widthProperty());
        clearBtn.prefWidthProperty().bind(pane.widthProperty());
        vbox.prefWidthProperty().bind(pane.widthProperty());
        vbox.prefHeightProperty().bind(pane.heightProperty());

    }

    private void initializeButtons() {

        button0 = new Button("1");
        button1 = new Button("2");
        button2 = new Button("3");
        button3 = new Button("4");
        button4 = new Button("5");
        button5 = new Button("6");
        button6 = new Button("7");

        buttons[0] = button0;
        buttons[1] = button1;
        buttons[2] = button2;
        buttons[3] = button3;
        buttons[4] = button4;
        buttons[5] = button5;
        buttons[6] = button6;

        // Add the buttons to the bottom row of the grid
        grid.add(button0, 0, 6);
        grid.add(button1, 1, 6);
        grid.add(button2, 2, 6);
        grid.add(button3, 3, 6);
        grid.add(button4, 4, 6);
        grid.add(button5, 5, 6);
        grid.add(button6, 6, 6);

        for (int i = 0; i < 7; i++) {
            Button btn = buttons[i];
            int finalI = i; // Due to the way Java handles closures, we need to create a final variable to hold the value of i
            btn.setOnAction(e -> {
                AppState state = AppState.getInstance();
                state.insertCoin(finalI);

                GameResult result = state.getGameResult();
                if (result.isGameOver()) {
                    if (result.isWin()) {
                        System.out.println("Player " + result.getWinningPlayer() + " wins!");
                    } else {
                        System.out.println("It's a draw!");
                    }

                    // Disable all column buttons
                    for (Button button : buttons) {
                        button.setDisable(true);
                    }

                    onGameOver(result);

                }
            });

            // Set the button to fill the entire cell
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setMaxHeight(Double.MAX_VALUE);
            btn.setMinHeight(50);
        }
    }

    @FXML
    private void onClearBtnClicked() {
        AppState state = AppState.getInstance();
        state.reset();
    }

    private void onGameOver(GameResult result) {
        assert result.isGameOver();

        // We display a closable dialog if the game is over
        String dialogText = result.isWin() ? "Player " + result.getWinningPlayer() + " wins!" : "It's a draw!";
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(new Label(dialogText));
        dialog.showAndWait();
    }
}