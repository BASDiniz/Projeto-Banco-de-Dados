package gui.produto.cadastrarProduto;

import com.jfoenix.controls.JFXComboBox;
import fachada.FachadaFuncionario;
import gui.mensagemDeErro.Alerta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import negocio.excecao.produto.ProdutoInvalidoException;
import negocio.excecao.produto.ProdutoJaCadastradoException;

/**
 * FXML Controller class
 *
 * @author EVERTON
 */
public class CadastraProdutoController implements Initializable {
    
    @FXML
    private TextField codigo;

    @FXML
    private TextField cor;
    
    @FXML
    private TextField descricaoDoProduto;

    @FXML
    private TextField valor;
    
    @FXML
    private JFXComboBox<String> tipoDeRoupa;

    @FXML
    private JFXComboBox<String> genero;

    @FXML
    private JFXComboBox<String> tamanho;

    @FXML
    private JFXComboBox<String> faixaEtaria;

    @FXML
    private JFXComboBox<String> categoria;
    
    @FXML
    private Spinner<Integer> quantidade;
    
    @FXML
    private Label labelCadastrado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.tipoDeRoupa.setItems(FXCollections.observableArrayList("Calça", "Camisa", "Moleton", "Saia", "Terno", "Vestido"));
        this.genero.setItems(FXCollections.observableArrayList("Feminino", "Masculino", "Unissex"));
        this.tamanho.setItems(FXCollections.observableArrayList("PP", "P", "M", "G", "GG"));
        this.faixaEtaria.setItems(FXCollections.observableArrayList("Infantil", "Juvenil", "Adulto"));
        this.categoria.setItems(FXCollections.observableArrayList("Academia", "Casual", "Dormir", "Festa", "Passeio","Praia", "Trabalho"));
    
        SpinnerValueFactory<Integer> opcoesQuantidade = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        this.quantidade.setValueFactory(opcoesQuantidade);
    }    
    
    @FXML
    public void cadastrarProduto(ActionEvent event) {
        
        FachadaFuncionario fachada = FachadaFuncionario.getInstance();
        
        String tipoDeRoupa = null;
        String faixaEtaria = null;
        String genero = null;
        String tamanho = null;
        String categoria = null;
        String cor = null;
        
        try {
             tipoDeRoupa = this.tipoDeRoupa.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("Selecione o Tipo de Roupa");
        }

        try {
            faixaEtaria = this.faixaEtaria.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("Selecione a faixa etaria");
        }
        try {
            genero = this.genero.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("Selecione o genero");
        }

        try {
            tamanho = this.tamanho.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("Selecione o tamanho");
        }

        try {
            categoria = this.categoria.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("selecione a categoria");
        }
        
        try {
            
            Double valor = Double.parseDouble(this.valor.getText());
            fachada.adicionarProduto(this.codigo.getText(), tipoDeRoupa,
                    this.descricaoDoProduto.getText(), faixaEtaria, 
                    genero, this.cor.getText(),
                    tamanho, categoria, this.quantidade.getValue(), valor);
            
            this.labelCadastrado.setText("Produto cadastrado com sucesso!");
        } catch (ProdutoInvalidoException produtoInvalido) {
            Alerta.alertar(produtoInvalido.getMessage());
        } catch (ProdutoJaCadastradoException produtoJaCadastrado) {
            Alerta.alertar(produtoJaCadastrado.getMessage());
        } catch (NumberFormatException numberFormatException){
            Alerta.alertar("Valor invalido");
        }
            
    }
     
}
