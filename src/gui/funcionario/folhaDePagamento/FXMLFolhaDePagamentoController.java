package gui.funcionario.folhaDePagamento;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import fachada.FachadaGerente;
import gui.funcionario.editarFuncionario.FXMLEditarFuncionarioController;
import gui.mensagemDeErro.Alerta;
import gui.telaPrincipalGerente.ControllerTelaPrincipalGerente;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.excecao.cliente.funcionario.FuncionarioDoMesNaoEncontradoException;
import negocio.excecao.cliente.funcionario.FuncionarioInativoException;
import negocio.excecao.cliente.funcionario.FuncionarioNaoCadastradoException;
import negocio.excecao.cliente.funcionario.FuncionarioNaoEncontradoException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FXMLFolhaDePagamentoController implements Initializable {

    @FXML
    private TableView<Cliente> tabela;

    @FXML
    private TableColumn<Cliente, String> nome;

    @FXML
    private TableColumn<Cliente, String> cpf;

    @FXML
    private TableColumn<Cliente, String> email;

    @FXML
    private TableColumn<Cliente, String> funcionarioDoMes;

    @FXML
    private TableColumn<Cliente, String> salario;

    @FXML
    private TableColumn<Cliente, String> salarioComBonificacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        this.cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
        this.email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cliente, String> c) {
                return new ReadOnlyObjectWrapper(c.getValue().getContato().getEmail());
            }
        });
        this.funcionarioDoMes.setCellValueFactory(new PropertyValueFactory<Cliente, String>("funcDoMes"));
        this.salario.setCellValueFactory(new PropertyValueFactory<Cliente, String>("salario"));
        this.salarioComBonificacao.setCellValueFactory(new PropertyValueFactory<Cliente, String>("salarioComBonificacao"));


        FachadaGerente fachada = FachadaGerente.getInstance();
        ArrayList<Cliente> listaDeClientes = fachada.listarTodosOsFuncionarios();
        this.tabela.setItems(FXCollections.observableArrayList(listaDeClientes));

    }
}
