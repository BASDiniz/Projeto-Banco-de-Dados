package gui.funcionario;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import fachada.FachadaGerente;
import gui.funcionario.editarFuncionario.FXMLEditarFuncionarioController;
import gui.mensagemDeErro.Alerta;
import gui.telaPrincipalGerente.ControllerTelaPrincipalGerente;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class FXMLFuncionarioController implements Initializable {

    @FXML
    private TextField pesquisa;

    @FXML
    private JFXComboBox busca;

    @FXML
    private TableView<Cliente> tabela;

    @FXML
    private TableColumn<Cliente, String> nome;

    @FXML
    private TableColumn<Cliente, String> cpf;

    @FXML
    private TableColumn<Cliente, String> telefonePrincipal;

    @FXML
    private TableColumn<Cliente, String> telefoneAlternativo;

    @FXML
    private TableColumn<Cliente, String> email;

    @FXML
    private TableColumn<Cliente, String> ativo;

    @FXML
    private TextField mes;

    @FXML
    private JFXButton botaoBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.busca.setItems(FXCollections.observableArrayList("CPF", "Nome"));
        this.nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        this.cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
        this.telefonePrincipal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cliente, String> c) {
                return new ReadOnlyObjectWrapper(c.getValue().getContato().getTelefonePrincipal());
            }
        });

        this.telefoneAlternativo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cliente, String> c) {
                return new ReadOnlyObjectWrapper(c.getValue().getContato().getTelefoneAlternativo());
            }
        });

        this.email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cliente, String> c) {
                return new ReadOnlyObjectWrapper(c.getValue().getContato().getEmail());
            }
        });

        this.ativo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ativo"));

        this.botaoBuscar.setDisable(true);


    }

    @FXML
    private void habilitarBotaoBuscar(){
        if(this.busca.getValue().equals("CPF") || this.busca.getValue().equals("Nome")){
            this.botaoBuscar.setDisable(false);
        }
    }

    @FXML
    private void carregarTelaCadastrarFuncionario(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/funcionario/cadastrarFuncionario/FXMLCadastrarFuncionario.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    }

    @FXML
    private void carregarTelaEditarFuncionario(ActionEvent event) throws IOException {
        Cliente cliente = (Cliente) this.tabela.getSelectionModel().getSelectedItem();
        if(cliente == null) {
            Alerta.alertar("Nenhum funcion?rio selecionado");
        }else{
            FXMLEditarFuncionarioController.cliente = cliente;
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/funcionario/editarFuncionario/FXMLEditarFuncionario.fxml"));
            ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
        }

    } 

    @FXML
    public void buscar(){
        if(busca.getValue().equals("CPF") || busca.getValue().equals("Nome")){
            FachadaGerente fachada = FachadaGerente.getInstance();
            this.botaoBuscar.setDisableVisualFocus(false);

            if(this.busca.getValue().equals("CPF")){
                try {
                    Funcionario funcionario = fachada.getFachadaGerente().buscarPorCpfFunc(this.pesquisa.getText());

                    this.tabela.setItems(FXCollections.observableArrayList(funcionario));

                }
                catch (FuncionarioNaoCadastradoException funcionarioNaoCadastrado) {
                    Alerta.alertar(funcionarioNaoCadastrado.getMessage());
                }
                catch (FuncionarioInativoException funcionarioInativo) {
                    Alerta.alertar(funcionarioInativo.getMessage());
                }

            }
            else if(this.busca.getValue().equals("Nome")){
                if(!this.pesquisa.getText().isEmpty()){
                    try {
                        ArrayList<Cliente> listaDeClientes = fachada.listarPorNomeFuncionario(this.pesquisa.getText());
                        this.tabela.setItems(FXCollections.observableArrayList(listaDeClientes));
                    }
                    catch (FuncionarioNaoEncontradoException funcionarioNaoEncontrado) {
                        Alerta.alertar(funcionarioNaoEncontrado.getMessage());
                    }
                }
            }
        }
    }

    @FXML
    public void determinarFuncionarioDoMes(){
        FachadaGerente fachada = FachadaGerente.getInstance();
        try{
            int mes = Integer.parseInt(this.mes.getText());
            Funcionario funcionario = null;
            try {
                funcionario = fachada.getFachadaGerente().determinarFuncionarioDoMes(mes);
            } catch (FuncionarioDoMesNaoEncontradoException funcionarioDoMes) {
                Alerta.alertar(funcionarioDoMes.getMessage());
            }
            this.tabela.setItems(FXCollections.observableArrayList(funcionario));

        }catch (NumberFormatException numberFormat){
            Alerta.alertar("M?s inv?lido");
        }

    }

}
