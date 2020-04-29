import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class PixelInfo extends Application {

    static Robot robot;

    public static void main(String[] args) {
        try{
            robot = new Robot();
        }catch(AWTException ignore){}
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vb = new VBox();
        vb.setBackground(Background.EMPTY);
        vb.setStyle("-fx-border-color:blue;");
        vb.setOnMouseClicked(e -> {
            System.out.print(e.getScreenX() + " : " + e.getScreenY());

            Thread thread = new Thread( () -> {
                java.awt.Color color = robot.getPixelColor((int) e.getScreenX(), (int) e.getScreenY());
                System.out.println("  -- r: " + color.getRed() + " | g: " + color.getGreen() + " | b: " + color.getBlue());
            });
            thread.start();
        });

        Scene scene = new Scene(vb, 300, 300);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }
}
