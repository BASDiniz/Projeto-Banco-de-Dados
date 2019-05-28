package gui.login;

import fachada.FachadaFuncionario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fachada.FachadaGerente;
import gui.mensagemDeErro.Alerta;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import negocio.entidade.Funcionario;
import negocio.excecao.cliente.funcionario.FuncionarioInativoException;
import negocio.excecao.cliente.funcionario.FuncionarioNaoCadastradoException;
import negocio.excecao.cliente.funcionario.SenhaIncorretaException;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField cpf;

    @FXML
    private PasswordField senha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void chamarMenu(String tipoMenu){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(tipoMenu));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Image iconeJanela = new Image(getClass().getResourceAsStream("/gui/imagens/clothes-hanger.png"));
            stage.getIcons().add(iconeJanela);
            stage.show();
        }
        catch (IOException e) {
        }

    }

    @FXML
    private void logar(ActionEvent event){
        String cpf = this.cpf.getText();
        String senha = this.senha.getText();

        try {
            FachadaFuncionario fachadaFuncionario = FachadaFuncionario.getInstance();
            fachadaFuncionario.lerDados();


            Funcionario funcionario = fachadaFuncionario.logar(cpf, senha);

            if(funcionario.getCargoGerente()){
                FachadaGerente fachadaGerente = FachadaGerente.getInstance();
                fachadaGerente.getFachadaGerente().checarDiaDaBonificacao();
                fachadaGerente.getFachadaGerente().checarDiaDoClienteFiel();

                chamarMenu("/gui/telaPrincipalGerente/FXMLTelaPrincipalGerente.fxml");

                Stage stage = (Stage) this.cpf.getScene().getWindow();
                stage.close();

            }else{

                fachadaFuncionario.checarDiaDaBonificacao();
                fachadaFuncionario.checarDiaDoClienteFiel();

                chamarMenu("/gui/telaPrincipalVendedor/FXMLTelaPrincipalVendedor.fxml");
                Stage stage = (Stage) this.cpf.getScene().getWindow();
                stage.close();

            }
        }
        catch (FuncionarioNaoCadastradoException funcionarioNaoCadastrado) {
            Alerta.alertar(funcionarioNaoCadastrado.getMessage());
        }
        catch (SenhaIncorretaException senhaIncorreta) {
            Alerta.alertar(senhaIncorreta.getMessage());
        }
        catch(FuncionarioInativoException funcionarioInativo){
            Alerta.alertar(funcionarioInativo.getMessage());
        }
    }
    
}
