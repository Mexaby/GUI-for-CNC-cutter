package Controller;

import Model.DemoShape;
import View.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainController {

    private final MainWindow mainWindow;

    RenderingHints renderingHints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
    );

    public MainController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        mainWindow.addStartButtonActionListener(new StartButton());
        mainWindow.addLoadButtonActionListener(new LoadButton());
    }

    class StartButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DemoShape demoShape = new DemoShape();

            Graphics graphics = mainWindow.getDrawingPanel().getGraphics();
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setRenderingHints(renderingHints);

            demoShape.drawDemoShape(graphics2D);

        }
    }

    class LoadButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }

            String allText = "";
            try {
                allText = new String(Files.readAllBytes(Paths.get(fileChooser.getSelectedFile().getAbsolutePath())));
            } catch (IOException ex) {
                mainWindow.showFileError();
            }

            BufferedReader reader;

            try {
                reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getAbsolutePath()));
                String line = reader.readLine();
                mainWindow.setCommandsText(allText);
                while (line != null) {
                    line = reader.readLine();
                }

                reader.close();
            } catch (IOException ex) {
                mainWindow.showFileError();
            }
        }
    }
}
