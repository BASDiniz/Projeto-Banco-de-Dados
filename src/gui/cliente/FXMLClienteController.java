package gui.cliente;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import fachada.FachadaFuncionario;
import fachada.FachadaGerente;
import gui.cliente.editarCliente.EditarClienteController;
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
import negocio.excecao.cliente.ClienteFielNaoEncontradoException;
import negocio.excecao.cliente.ClienteNaoEncontradoException;

public class FXMLClienteController implements Initializable {
    
    @FXML
    private TextField pesquisa;

    @FXML
    private JFXComboBox buscarPor;

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
        
        this.buscarPor.setItems(FXCollections.observableArrayList("CPF", "Nome"));
        this.nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Nome"));
        this.cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Cpf"));

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
        if(this.buscarPor.getValue().equals("CPF") || this.buscarPor.getValue().equals("Nome")){
            this.botaoBuscar.setDisable(false);
        }
    }

    @FXML
    private void carregarTelaCadastrarCliente(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/cliente/cadastrarCliente/FXMLCadastrarCliente.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    }

    @FXML
    private void carregarTelaEditarCliente(ActionEvent event) throws IOException {
        Cliente cliente = (Cliente) this.tabela.getSelectionModel().getSelectedItem();
        
        if(cliente == null) {
            Alerta.alertar("Nenhum cliente selecionado");
        }
        
        else {
            EditarClienteController.cliente = cliente;
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/cliente/editarCliente/FXMLEditarCliente.fxml"));
            ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
        }
    } 
    
    @FXML
    public void buscar(ActionEvent event){
        FachadaGerente fachada = FachadaGerente.getInstance(); // fach Gerente?
        
        if(this.buscarPor.getValue().equals("CPF")){
            try {
                Cliente cliente = fachada.getFachadaGerente().buscarPorCpf(this.pesquisa.getText());
                this.tabela.setItems(FXCollections.observableArrayList(cliente));

            }
            catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                Alerta.alertar(clienteNaoEncontrado.getMessage());
            }

        }
        else if(this.buscarPor.getValue().equals("Nome")){
            
            if(!this.pesquisa.getText().isEmpty()) {
                try {
                    ArrayList<Cliente> listaDeClientes = fachada.getFachadaGerente().listarPorNomeCliente(this.pesquisa.getText());
                    
                    this.tabela.setItems(FXCollections.observableArrayList(listaDeClientes));
                }
                catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                    Alerta.alertar(clienteNaoEncontrado.getMessage());
                }
            }
        }
    }
    
    @FXML
    public void determinarClienteFiel(ActionEvent event){
        FachadaFuncionario fachada = FachadaFuncionario.getInstance();
        try{
            int mes = Integer.parseInt(this.mes.getText());
            Cliente cliente = null;
            try {
                cliente = fachada.determinarClienteFiel(mes);
            } catch (ClienteFielNaoEncontradoException clienteFiel) {
                Alerta.alertar(clienteFiel.getMessage());
            }
            this.tabela.setItems(FXCollections.observableArrayList(cliente));

        }catch (NumberFormatException numberFormat){
            Alerta.alertar("M?s inv?lido");
        }
    }
    
    @FXML
    public void alterar(ActionEvent event) { // public
        
        Cliente cliente = (Cliente) tabela.getSelectionModel().getSelectedItem();
        
        EditarClienteController.cliente = cliente;
        
        try {
            carregarTelaEditarCliente(event);
        }
        catch (IOException ex) {
        }
    }
}
