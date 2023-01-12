package Controller;

import Model.DemoShape;
import View.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainController {

    private final MainWindow mainWindow;

    public File file;

    RenderingHints renderingHints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
    );

    public MainController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        mainWindow.addStartButtonActionListener(new StartButton());
        mainWindow.addLoadButtonActionListener(new LoadButton());
    }

    class LoadButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
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

    class StartButton implements ActionListener {

        public void drawShape(File file) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                Graphics2D graphics2D = (Graphics2D) mainWindow.getDrawingPanel().getGraphics();
                graphics2D.setRenderingHints(renderingHints);
                Path2D.Double path = new Path2D.Double();
//                Arc2D.Double arc = new Arc2D.Double();

                while ((line = reader.readLine()) != null) {
                    String[] coordinates = line.split(" ");

                    if (line.startsWith("G00")) {
                        // parse the G00 command and move the cutting head to the specified position

                        double x = Double.parseDouble(coordinates[1].substring(1));
                        double y = Double.parseDouble(coordinates[2].substring(1));
                        path.moveTo(x, y);

                    } else if (line.startsWith("G01")) {
                        // parse the G01 command and move the cutting head to the specified position
                        double x = Double.parseDouble(coordinates[1].substring(1));
                        double y = Double.parseDouble(coordinates[2].substring(1));
                        path.lineTo(x, y);

                    } else if (line.startsWith("G02")) {
                        // parse the G02 command and draw a clockwise arc
                        double controlPointOneX = Double.parseDouble(coordinates[1]);
                        double controlPointOneY = Double.parseDouble(coordinates[2]);
                        double controlPointTwoX = Double.parseDouble(coordinates[3]);
                        double controlPointTwoY = Double.parseDouble(coordinates[4]);
                        double endX = Double.parseDouble(coordinates[5]);
                        double endY = Double.parseDouble(coordinates[6]);
                        path.curveTo(controlPointOneX, controlPointOneY, controlPointTwoX, controlPointTwoY, endX, endY);
//                    } else if (line.startsWith("G02")) {
//                        // parse the G02 command and draw a clockwise arc
//                        double x = Double.parseDouble(coordinates[1].substring(1));
//                        double y = Double.parseDouble(coordinates[2].substring(1));
//                        double i = Double.parseDouble(coordinates[3].substring(1));
//                        double j = Double.parseDouble(coordinates[4].substring(1));
//                        double radius = Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
//                        double centerX = x + i;
//                        double centerY = y + j;
//                        double startAngle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
//                        double endAngle = startAngle - 90;
//                        arc.setArcByCenter(centerX, centerY, radius, startAngle, endAngle, Arc2D.OPEN);
//                        path.append(arc, true);
//                    } else if (line.startsWith("G03")) {
//                        // parse the G02 command and draw a clockwise arc
//                        double x = Double.parseDouble(coordinates[1].substring(1));
//                        double y = Double.parseDouble(coordinates[2].substring(1));
//                        double i = Double.parseDouble(coordinates[3].substring(1));
//                        double j = Double.parseDouble(coordinates[4].substring(1));
//                        double radius = Math.sqrt(Math.pow(x - (x + i), 2) + Math.pow(y - (y + j), 2));
//                        double centerX = x + i;
//                        double centerY = y + j;
//                        double startAngle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
//                        double endAngle = startAngle - 90;
//                        arc.setArcByCenter(centerX, centerY, radius, startAngle, endAngle, Arc2D.OPEN);
//                        path.append(arc, true);
                    }
                }
                graphics2D.draw(path);
            } catch (IOException e) {
                mainWindow.showFileError();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                mainWindow.clearDrawingPanel();
                drawShape(file);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "You have not selected a file");
            }
        }
    }
}