package gui.cliente.editarCliente;

import com.jfoenix.controls.JFXToggleButton;
import fachada.FachadaGerente;
import gui.mensagemDeErro.Alerta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import negocio.entidade.Cliente;
import negocio.excecao.cliente.ClienteAtivoException;
import negocio.excecao.cliente.ClienteNaoEncontradoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;

public class EditarClienteController implements Initializable {
   
    @FXML
    private TextField nome, cpf, telefonePrincipal, telefoneAlternativo, email;
    
    @FXML
    private JFXToggleButton ativo;
    
    @FXML
    private Label mensagem;
    
    public static Cliente cliente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.nome.setText(cliente.getNome());
        this.cpf.setText(cliente.getCpf());
        this.cpf.setDisable(true);
        this.telefonePrincipal.setText(cliente.getContato().getTelefonePrincipal());
        this.telefoneAlternativo.setText(cliente.getContato().getTelefoneAlternativo());
        this.email.setText(cliente.getContato().getEmail());
        this.ativo.setSelected(cliente.getAtivo());
    }    
    
    @FXML
    private void alterar(ActionEvent event) {
        FachadaGerente fachada = FachadaGerente.getInstance();
        
        try {
            fachada.getFachadaGerente().alterarCliente(this.nome.getText(), this.cpf.getText(), this.telefonePrincipal.getText(),
                    this.telefoneAlternativo.getText(), this.email.getText());
            this.mensagem.setText("Dados alterados com sucesso");
        } catch (DadosInvalidosException dadosInvalidos) {
            Alerta.alertar(dadosInvalidos.getMessage());
        } catch (ContatoInvalidoException contatoInvalido) {
            Alerta.alertar(contatoInvalido.getMessage());
        }
        
        if(this.ativo.isSelected()) {
            try {
                fachada.getFachadaGerente().habilitarCliente(this.cpf.getText());
            } 
            catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                Alerta.alertar(clienteNaoEncontrado.getMessage());
            }
        }
        
        else {
            try {
                fachada.getFachadaGerente().desabilitarCliente(this.cpf.getText());
            } catch (ClienteAtivoException clienteAtivo) {
                Alerta.alertar(clienteAtivo.getMessage());
            } catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                Alerta.alertar(clienteNaoEncontrado.getMessage());
            }
        }
      
    }
}
