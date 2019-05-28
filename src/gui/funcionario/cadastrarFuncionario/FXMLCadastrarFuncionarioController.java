package gui.funcionario.cadastrarFuncionario;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import fachada.FachadaGerente;
import gui.mensagemDeErro.Alerta;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.funcionario.FuncionarioJaCadastradoException;
import negocio.excecao.cliente.funcionario.GerenteJaCadastradoException;


public class FXMLCadastrarFuncionarioController implements Initializable {

    @FXML
    private TextField nome, cpf, telefonePrincipal, telefoneAlternativo, email, senha, salario;


    @FXML
    private JFXComboBox cargo;

    @FXML
    private Label mensagem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cargo.setItems(FXCollections.observableArrayList("Gerente", "Vendedor"));
    }

    @FXML
    public void cadastrarFuncionario(){
        FachadaGerente fachada = FachadaGerente.getInstance();
        try {

            double salario = Double.parseDouble(this.salario.getText());
            boolean cargo = false;
            if(this.cargo.getValue().equals("Gerente")){
                cargo = true;
            }

            fachada.adicionarFuncionario(this.nome.getText(), this.cpf.getText(), this.telefonePrincipal.getText(),
                    this.telefoneAlternativo.getText(), this.email.getText(), this.senha.getText(), salario, cargo);
            mensagem.setText("Cadastrado com sucesso");

        }
        catch (DadosInvalidosException dadosInvalidos) {
            Alerta.alertar(dadosInvalidos.getMessage());
        }
        catch (ContatoInvalidoException contatoInvalido) {
            Alerta.alertar(contatoInvalido.getMessage());
        }
        catch (NumberFormatException numberFormatException){
            Alerta.alertar("Sal?rio inv?lido");
        }
        catch (GerenteJaCadastradoException gerenteJaCadastrado) {
            Alerta.alertar(gerenteJaCadastrado.getMessage());
        }
        catch (FuncionarioJaCadastradoException funcionarioJaCadastrado) {
            Alerta.alertar(funcionarioJaCadastrado.getMessage());
        }
        catch(NullPointerException nullPointer){
            Alerta.alertar("Selecione o cargo");
        }

    }

}
