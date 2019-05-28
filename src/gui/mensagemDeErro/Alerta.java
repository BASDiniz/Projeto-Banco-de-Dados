package gui.mensagemDeErro;

import javafx.scene.control.Alert;

public class Alerta {

    public static void alertar(String mensagem){

        Alert mensagemDeErro = new Alert(Alert.AlertType.ERROR);
        mensagemDeErro.setTitle("Error");
        mensagemDeErro.setHeaderText("Error 404");
        mensagemDeErro.setContentText(mensagem);
        mensagemDeErro.showAndWait();

    }

}
