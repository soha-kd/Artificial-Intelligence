package view;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static view.View.FRAME;

public class CustomizationFrm {
    private static CustomizationFrm customizationFrm = null;

    private JPanel mainPanel;
    private JPanel upperPanel;
    private JRadioButton playerNo1RadioButton;
    private JRadioButton playerNo2RadioButton;
    private JRadioButton obstaclesRadioButton;
    private JRadioButton starsRadioButton;
    private JButton okButton;

    public static CustomizationFrm getInstance() {
        if (customizationFrm == null) {
            customizationFrm = new CustomizationFrm();
        }
        return customizationFrm;
    }

    private CustomizationFrm() {
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goToNextFrm();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getUpperPanel() {
        return upperPanel;
    }

    public JRadioButton getPlayerNo1RadioButton() {
        return playerNo1RadioButton;
    }

    public JRadioButton getPlayerNo2RadioButton() {
        return playerNo2RadioButton;
    }

    public JRadioButton getObstaclesRadioButton() {
        return obstaclesRadioButton;
    }

    public JRadioButton getStarsRadioButton() {
        return starsRadioButton;
    }

    private void goToNextFrm() {
        var boardGUI = BoardGUI.getInstance();
        boardGUI.setBoardInPlayingState(true);
        boardGUI.refreshButtons();
        var playingFrm = PlayingFrm.getInstance();
        playingFrm.getUpperPanel().add(boardGUI.getBoardPnl());
        FRAME.setContentPane(playingFrm.getMainPanel());
        FRAME.pack();
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("CustomizationFrm");
//        frame.setContentPane(new CustomizationFrm().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        upperPanel = new JPanel();
    }
}
