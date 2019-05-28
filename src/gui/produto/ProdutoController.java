package gui.produto;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import fachada.FachadaFuncionario;
import gui.mensagemDeErro.Alerta;
import gui.produto.editarProduto.EditarProdutoController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.telaPrincipalGerente.ControllerTelaPrincipalGerente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import negocio.entidade.produto.Produto;
import negocio.excecao.produto.ProdutoNaoEncontradoException;

/**
 * FXML Controller class
 *
 * @author EVERTON
 */
public class ProdutoController implements Initializable {
    
    @FXML
    private TextField mes;
    
    @FXML
    private TextField pesquisa;
    
    @FXML
    private JFXComboBox buscarPor; // <?> String?
    
    @FXML
    private JFXButton botaoBuscar;
    
    @FXML
    private TableView<Produto> tabela;
    
    @FXML
    private TableColumn<Produto, String> codigo;

    @FXML
    private TableColumn<Produto, String> tipo;

    @FXML
    private TableColumn<Produto, String> descricao;

    @FXML
    private TableColumn<Produto, String> genero;

    @FXML
    private TableColumn<Produto, String> cor;

    @FXML
    private TableColumn<Produto, String> tamanho;

    @FXML
    private TableColumn<Produto, String> valor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.buscarPor.setItems(FXCollections.observableArrayList("Categoria", "Código", "Descrição", "Faixa Etária", "Tipo"));
        this.botaoBuscar.setDisable(true);
        
        
        this.codigo.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        this.tipo.setCellValueFactory(new PropertyValueFactory<Produto, String>("tipoDeRoupa"));
        this.descricao.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricaoDoProduto"));
        this.genero.setCellValueFactory(new PropertyValueFactory<Produto, String>("genero"));
        this.cor.setCellValueFactory(new PropertyValueFactory<Produto, String>("cor"));
        this.tamanho.setCellValueFactory(new PropertyValueFactory<Produto, String>("tamanho"));
        this.valor.setCellValueFactory(new PropertyValueFactory<Produto, String>("valorVenda"));
    }
    
    
    @FXML
    private void habilitarBotaoBuscar() {
        if(this.buscarPor.getValue().equals("Categoria") || this.buscarPor.getValue().equals("Código")
                || this.buscarPor.getValue().equals("Descrição") || this.buscarPor.getValue().equals("Faixa Etária")
                || this.buscarPor.getValue().equals("Tipo")){
            this.botaoBuscar.setDisable(false);
        }
    }
    
    @FXML
    private void carregarTelaCadastrarProduto(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/produto/cadastrarProduto/FXMLCadastrarProduto.fxml"));
        ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
    } 
    
    @FXML
    private void carregarTelaAlterarProduto(ActionEvent event) throws IOException {
        Produto produto = (Produto) this.tabela.getSelectionModel().getSelectedItem();
        
        if(produto == null) {
            Alerta.alertar("Nenhum produto selecionado");
        }
        
        else {
            EditarProdutoController.produto = produto;
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/gui/produto/editarProduto/FXMLEditarProduto.fxml"));
            ControllerTelaPrincipalGerente.rootPaneAberta.getChildren().setAll(pane);
        }
    } 
    
    @FXML
    public void buscar(ActionEvent event){
        FachadaFuncionario fachada = FachadaFuncionario.getInstance(); // fach Gerente?
        
        if(this.buscarPor.getValue().equals("Categoria")){
            try {
                ArrayList<Produto> produtosEncontrados = fachada.listarProdutosPorCategoria(this.pesquisa.getText());
                this.tabela.setItems(FXCollections.observableArrayList(produtosEncontrados));

            } catch (ProdutoNaoEncontradoException produtoNaoEncontrado) {
                Alerta.alertar(produtoNaoEncontrado.getMessage());
            }
        }
        
        else if(this.buscarPor.getValue().equals("Código")){
            
            
            
            try {
                Produto produto = fachada.buscarProdutoPorCodigo(this.pesquisa.getText());

                this.tabela.setItems(FXCollections.observableArrayList(produto));
                
                
            }
            catch (ProdutoNaoEncontradoException produtoNaoEncontrado) {
                Alerta.alertar(produtoNaoEncontrado.getMessage());
            }
        }
        
        else if(this.buscarPor.getValue().equals("Descrição")){
            
            if(!this.pesquisa.getText().isEmpty()) {
                try {
                    ArrayList<Produto> produtosEncontrados = fachada.listarProdutosPorDescricao(this.pesquisa.getText());
                    this.tabela.setItems(FXCollections.observableArrayList(produtosEncontrados));

                } catch (ProdutoNaoEncontradoException produtoNaoEncontrado) {
                    Alerta.alertar(produtoNaoEncontrado.getMessage());
                }
            }
        }
        
        else if(this.buscarPor.getValue().equals("Faixa Etária")){
            try {
                ArrayList<Produto> produtosEncontrados = fachada.listarProdutosPorFaixaEtaria(this.pesquisa.getText());
                this.tabela.setItems(FXCollections.observableArrayList(produtosEncontrados));

            } catch (ProdutoNaoEncontradoException produtoNaoEncontrado) {
                Alerta.alertar(produtoNaoEncontrado.getMessage());
            }
        }
        
        if(this.buscarPor.getValue().equals("Tipo")){
            try {
                ArrayList<Produto> produtosEncontrados = fachada.listarProdutosPorTipo(this.pesquisa.getText());
                this.tabela.setItems(FXCollections.observableArrayList(produtosEncontrados));

            } catch (ProdutoNaoEncontradoException produtoNaoEncontrado) {
                Alerta.alertar(produtoNaoEncontrado.getMessage());
            }
        }
    }

    @FXML
    private void produtosMaisVendidos(ActionEvent event) {
        FachadaFuncionario fachada = FachadaFuncionario.getInstance();

        try {
            ArrayList<Produto> produtosEncontrados = fachada.buscarProdutosMaisVendidos(Integer.parseInt(this.mes.getText()));
            this.tabela.setItems(FXCollections.observableArrayList(produtosEncontrados));
        } catch(NumberFormatException NumberFormatException) {
            Alerta.alertar("Mês inválido");
        }

    }
    
}
