import Controller.MainController;
import View.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        new MainController(mainWindow);
    }
}