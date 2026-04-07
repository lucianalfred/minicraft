package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        stage.setTitle("MiniCraft");
        stage.setScene(scene);
        stage.show();

        Game game = new Game(canvas, scene);
        game.start();
        
    }

    public static void main(String[] args) {
        launch();
    }
}
