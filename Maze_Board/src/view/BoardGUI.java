package view;
import model.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class BoardGUI {
    private static BoardGUI boardGUI = null;

    private final JPanel boardPnl = new JPanel();
    private final JButton[][] boardButtons;
    private boolean boardInPlayingState = false;
    private JButton p1Button = new JButton();
    private JButton p2Button = new JButton();

    private final Game model;
    private final CustomizationFrm configurationFrm = CustomizationFrm.getInstance();
    private final PlayingFrm playingFrm = PlayingFrm.getInstance();

    public static BoardGUI getInstance(int numOfRows, int numOfColumns) {
        if (boardGUI == null) {
            boardGUI = new BoardGUI(numOfRows, numOfColumns);
        }
        return boardGUI;
    }

    public static BoardGUI getInstance() {
        if (boardGUI == null) {
            try {
                throw new IllegalAccessException();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return boardGUI;
    }

    private BoardGUI(int numOfRows, int numOfColumns) {
        model = Game.getInstance(numOfRows, numOfColumns);
        boardPnl.setLayout(new GridLayout(numOfRows, numOfColumns));
        boardButtons = new JButton[numOfRows][numOfColumns];
        initBoardButtons();
        refreshButtons();
    }

    public JPanel getBoardPnl() {
        return boardPnl;
    }

    public void setBoardInPlayingState(boolean boardInPlayingState) {
        this.boardInPlayingState = boardInPlayingState;
    }

    private void initBoardButtons() {
        for (int i = 0, boardButtonsLength = boardButtons.length; i < boardButtonsLength; i++) {
            for (int j = 0, rowOfButtonsLength = boardButtons[i].length; j < rowOfButtonsLength; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setMargin(new Insets(20, 20, 20, 20));
//                boardButtons[i][j].setBackground(Color.WHITE);
                activateButton(boardButtons[i][j], i, j);
                boardPnl.add(boardButtons[i][j]);
            }
        }
    }

    private void activateButton(JButton boardButton, int row, int column) {
        boardButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (boardInPlayingState) {
                    try {
                        model.moveItem(row, column);
                        playingFrm.setPlayersTurnText(model.getTurn());
                    } catch (IllegalStateException illegalStateException) {
                        illegalStateException.getMessage();
                        var illegalMovementDlg = new IllegalMovementDlg();
                        illegalMovementDlg.pack();
                        illegalMovementDlg.setVisible(true);
                    }
                    playingFrm.setP1ScoreText(String.valueOf(model.getPlayer1Score()));
                    playingFrm.setP2ScoreText(String.valueOf(model.getPlayer2Score()));
                    if (model.isGameFinished()) {
                        FinalDlg finalDlg = new FinalDlg();
                        if (model.getPlayer1Score() > model.getPlayer2Score()) {
                            finalDlg.getResultTextField().setText("Player number 1!");
                        } else if (model.getPlayer1Score() > model.getPlayer2Score()) {
                            finalDlg.getResultTextField().setText("Player number 2!");
                        } else {
                            finalDlg.getResultTextField().setText("Just a boring draw!");
                        }
                        finalDlg.pack();
                        finalDlg.setVisible(true);
                    }
                } else {
                    if (configurationFrm.getPlayerNo1RadioButton().isSelected()) {
                        model.setP1Position(row, column);
                    } else if (configurationFrm.getPlayerNo2RadioButton().isSelected()) {
                        model.setP2Position(row, column);
                    } else if (configurationFrm.getObstaclesRadioButton().isSelected()) {
                        model.addObstacle(row, column);
                    } else if (configurationFrm.getStarsRadioButton().isSelected()) {
                        model.addStar(row, column);
                    }
                }
                refreshButtons();
            }
        });
    }

    void refreshButtons() {
        char[][] buttonsData = model.getBoard();
        for (int i = 0; i < buttonsData.length; i++) {
            for (int j = 0; j < buttonsData[i].length; j++) {
                setButton(boardButtons[i][j], buttonsData[i][j]);
            }
        }
    }

    private void setButton(JButton boardButton, char blockType) {
        switch (blockType) {
            case '1' -> {
                p1Button.setBackground(Color.WHITE);
//                p1Button.setText("");
                boardButton.setBackground(Color.CYAN);
//                boardButton.setText("P1");
                p1Button = boardButton;
            }
            case '2' -> {
                p2Button.setBackground(Color.WHITE);
//                p2Button.setText("");
                boardButton.setBackground(Color.MAGENTA);
//                boardButton.setText("P2");
                p2Button = boardButton;
            }
            case 'O' -> {
                boardButton.setBackground(Color.BLACK);
//                boardButton.setText("");
            }
            case 'S' -> {
                boardButton.setBackground(Color.YELLOW);
//                boardButton.setText("");
            }
            case '-' -> {
                boardButton.setBackground(Color.WHITE);
//                boardButton.setText("");
            }
            default -> throw new IllegalArgumentException(String.valueOf(blockType));
        }
    }
}
