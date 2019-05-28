package gui.funcionario.editarFuncionario;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import fachada.FachadaGerente;
import gui.mensagemDeErro.Alerta;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.excecao.cliente.ClienteAtivoException;
import negocio.excecao.cliente.ClienteNaoEncontradoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.funcionario.FuncionarioNaoCadastradoException;
import negocio.excecao.cliente.funcionario.GerenteJaCadastradoException;


public class FXMLEditarFuncionarioController implements Initializable {

    @FXML
    private JFXComboBox cargo;

    @FXML
    private TextField nome, cpf, telefonePrincipal, telefoneAlternativo, email, senha, salario;

    @FXML
    private JFXToggleButton ativo;

    @FXML
    private Label mensagem;

    public static Cliente cliente;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cargo.setItems(FXCollections.observableArrayList("Vendedor", "Gerente"));
        if(cliente instanceof Cliente){
            Funcionario funcionario = (Funcionario) cliente;
            this.nome.setText(funcionario.getNome());
            this.cpf.setText(funcionario.getCpf());
            this.cpf.setDisable(true);
            this.telefonePrincipal.setText(funcionario.getContato().getTelefonePrincipal());
            this.telefoneAlternativo.setText(funcionario.getContato().getTelefoneAlternativo());
            this.email.setText(funcionario.getContato().getEmail());
            this.ativo.setSelected(funcionario.getAtivo());
            this.senha.setText(funcionario.getSenha());
            String salario = String.valueOf(funcionario.getSalario());
            this.salario.setText(salario);

            if(funcionario.getCargoGerente() == true){
                this.cargo.setValue("Gerente");
            }else{
                this.cargo.setValue("Vendedor");
            }

            this.ativo.setSelected(funcionario.getAtivo());
        }
    }

    @FXML
    private void alterar(ActionEvent event){
        FachadaGerente fachada = FachadaGerente.getInstance();

        boolean cargo = false;
        if(this.cargo.getValue().equals("Gerente")){
            cargo = true;
        }

        try {
            Double salario = Double.parseDouble(this.salario.getText());
            fachada.alterarFuncionario(this.nome.getText(), this.cpf.getText(), this.telefonePrincipal.getText(),
                    this.telefoneAlternativo.getText(), this.email.getText(), this.senha.getText(), salario, cargo);
            this.mensagem.setText("Dados alterados com sucesso");
        }
        catch (DadosInvalidosException dadosInvalidos) {
            Alerta.alertar(dadosInvalidos.getMessage());
        }
        catch (ContatoInvalidoException contatoInvalido) {
            Alerta.alertar(contatoInvalido.getMessage());
        }
        catch (GerenteJaCadastradoException gerenteJaCadastrado) {
            Alerta.alertar(gerenteJaCadastrado.getMessage());
        }
        catch (FuncionarioNaoCadastradoException funcionarioNaoCadastrado) {
            Alerta.alertar(funcionarioNaoCadastrado.getMessage());
        }
        catch(NumberFormatException numberFormat){
            Alerta.alertar("O campo sal?rio deve conter apenas n?meros");
    }

        if(this.ativo.isSelected()) {
            try {
                fachada.habilitarFuncionario(this.cpf.getText());
            }
            catch (FuncionarioNaoCadastradoException funcionarioNaoCadastrado) {
                Alerta.alertar(funcionarioNaoCadastrado.getMessage());
            }
            catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                Alerta.alertar(clienteNaoEncontrado.getMessage());
            }
        }else{
            try {
                fachada.desabilitarFuncionario(this.cpf.getText());
            }
            catch (FuncionarioNaoCadastradoException funcionarioNaoCadastrado) {
                Alerta.alertar(funcionarioNaoCadastrado.getMessage());
            }
            catch (ClienteAtivoException clienteAtivo) {
                Alerta.alertar(clienteAtivo.getMessage());
            }
            catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                Alerta.alertar(clienteNaoEncontrado.getMessage());
            }
        }
    }
}
