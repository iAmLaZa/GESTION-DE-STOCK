package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BD.c();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("تسجيل الدخول");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 715, 402));
        
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/image/icons8_cash_register_100px.png")));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
