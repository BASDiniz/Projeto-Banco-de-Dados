package gui.telaPrincipalGerente.menu;

import com.jfoenix.controls.JFXButton;
import fachada.FachadaGerente;
import gui.telaPrincipalGerente.ControllerTelaPrincipalGerente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class FXMLConteudoMenuHamburguerController implements Initializable {
    
    @FXML
    private JFXButton buttonFuncionario;

    @FXML
    private JFXButton buttonCliente;

    @FXML
    private JFXButton buttonProduto;

    @FXML
    private JFXButton buttonVenda;

    @FXML
    private JFXButton buttonLucro;

    @FXML
    private JFXButton buttonSalvarESair;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    // Metodos que carregam telas.
    
    @FXML
    private void carregarTelaFuncionario(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/funcionario/FXMLFuncionario.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    }
    
    @FXML
    private void carregarTelaCliente(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/cliente/FXMLCliente.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    }
    
    @FXML
    private void carregarTelaProduto(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/produto/FXMLProduto.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    }

    @FXML
    private void carregarTelaVenda(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/venda/FXMLVenda.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    }

    @FXML
    private void carregarTelaLucro(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/lucro/FXMLLucro.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    }

    @FXML
    private void salvarESair(ActionEvent event) {

        FachadaGerente fachada = FachadaGerente.getInstance();
        fachada.getFachadaGerente().salvarDados();

        System.exit(0);
    }
}
