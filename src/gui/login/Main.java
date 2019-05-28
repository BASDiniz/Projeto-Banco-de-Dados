package gui.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        stage.setTitle("Bem-vindo - NewStyle Store");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Image iconeJanela = new Image(getClass().getResourceAsStream("/gui/imagens/clothes-hanger.png"));
        stage.getIcons().add(iconeJanela);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
