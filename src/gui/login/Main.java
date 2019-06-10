package gui.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import negocio.entidade.Cliente;
import negocio.entidade.Contato;
import negocio.entidade.Funcionario;
import fachada.FachadaGerente;
import negocio.excecao.cliente.ClienteJaCadastradoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.funcionario.FuncionarioJaCadastradoException;
import negocio.excecao.cliente.funcionario.GerenteJaCadastradoException;

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

    public static void main(String[] args) throws FuncionarioJaCadastradoException, DadosInvalidosException, ContatoInvalidoException, GerenteJaCadastradoException {
        try {
            FachadaGerente.getInstance().getFachadaGerente().adicionarCliente("Bruno Diniz", "11111111111", "12312312312", "12312312312", "bruno@gmail.com");
        } catch (ClienteJaCadastradoException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
