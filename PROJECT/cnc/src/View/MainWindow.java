package View;

import Model.DemoShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;

public class MainWindow{
    private JButton loadFileButton;
    private JButton startButton;
    private JTextArea commandsText;
    private JPanel windowPanel;
    private JScrollPane commandsArea;
    private JPanel drawingPanel;
    JFrame frame = new JFrame("title");

    public MainWindow() {
        frame.setContentPane(windowPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        commandsArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        commandsArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        commandsText.setEditable(false);

    }

    public void addStartButtonActionListener (final ActionListener actionListener){
        startButton.addActionListener(actionListener);
    }

    public void addLoadButtonActionListener (final ActionListener actionListener){
        loadFileButton.addActionListener(actionListener);
    }

    public JPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setCommandsText(String text){
        commandsText.setText(text);
    }

    public void showFileError(){
        JOptionPane.showConfirmDialog(null,"Error opening file");
    }

    public void clearDrawingPanel(){
        drawingPanel.getGraphics().clearRect(0, 0, drawingPanel.getWidth(), drawingPanel.getHeight());
    }

}
