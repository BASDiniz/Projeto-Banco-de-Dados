package gui.cliente.cadastrarCliente;

import fachada.FachadaFuncionario;
import gui.mensagemDeErro.Alerta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import negocio.excecao.cliente.ClienteJaCadastradoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;


public class FXMLCadastrarClienteController implements Initializable {

    @FXML
    private TextField nome, cpf, telefonePrincipal, telefoneAlternativo, email;
    
    @FXML
    private Label labelCadastrado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    @FXML
    public void cadastrarCliente(ActionEvent event) {
        
        FachadaFuncionario fachada = FachadaFuncionario.getInstance();
        
        try {

            fachada.adicionarCliente(this.nome.getText(), this.cpf.getText(), this.telefonePrincipal.getText(),
                    this.telefoneAlternativo.getText(), this.email.getText());
            
            this.labelCadastrado.setText("Cliente cadastrado com sucesso!");
            
        }
        catch (ContatoInvalidoException contatoInvalido) {
            Alerta.alertar(contatoInvalido.getMessage());
        }
        catch (DadosInvalidosException dadosInvalidos) {
            Alerta.alertar(dadosInvalidos.getMessage());
        } catch (ClienteJaCadastradoException clienteJaCadastrado) {
            Alerta.alertar(clienteJaCadastrado.getMessage());
        }
    }
}
