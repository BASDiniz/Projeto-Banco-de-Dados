package gui.produto.editarProduto;

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
import com.jfoenix.controls.JFXButton;
import negocio.entidade.produto.Produto;
import negocio.excecao.produto.ProdutoInvalidoException;
import negocio.excecao.produto.ProdutoNaoEncontradoException;

public class EditarProdutoController implements Initializable {
    
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
    private JFXButton botaoAlterar;
    
    @FXML
    private Label mensagem;
    
    @FXML
    private Label mensagemRemovido;
    
    public static Produto produto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.tipoDeRoupa.setItems(FXCollections.observableArrayList("Calça", "Camisa", "Moleton", "Saia", "Terno", "Vestido"));
        this.genero.setItems(FXCollections.observableArrayList("Feminino", "Masculino", "Unissex"));
        this.tamanho.setItems(FXCollections.observableArrayList("PP", "P", "M", "G", "GG"));
        this.faixaEtaria.setItems(FXCollections.observableArrayList("Infantil", "Juvenil", "Adulto"));
        this.categoria.setItems(FXCollections.observableArrayList("Academia", "Casual", "Dormir", "Festa", "Passeio","Praia", "Trabalho"));
        
        this.codigo.setText(produto.getCodigo());
        this.codigo.setDisable(true);
        
        this.cor.setText(produto.getCor());
        this.cor.setDisable(true);
        
        this.descricaoDoProduto.setText(produto.pegarDescricaoPura());
        this.valor.setText(String.valueOf(produto.getValorVenda()));
        
        this.tipoDeRoupa.setValue(produto.getTipoDeRoupa());
        
        this.genero.setValue(produto.getGenero());
        this.genero.setDisable(true);
        
        this.tamanho.setValue(produto.getTamanho());
        this.tamanho.setDisable(true);
        
        this.faixaEtaria.setValue(produto.getFaixaEtaria());
        
        this.categoria.setValue(produto.getCategoria());
        
        SpinnerValueFactory<Integer> opcoesQuantidade = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, produto.getQuantidade());
        this.quantidade.setValueFactory(opcoesQuantidade);
    }    
    
    @FXML
    public void alterarProduto(ActionEvent event) {
        
        FachadaFuncionario fachada = FachadaFuncionario.getInstance(); // fachFunc?
        
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
            Alerta.alertar("Selecione a Faixa Etária");
        }
        try {
            genero = this.genero.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("Selecione o Genêro");
        }

        try {
            tamanho = this.tamanho.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("Selecione o Tamanho");
        }

        try {
            categoria = this.categoria.getValue().toLowerCase();
        } catch (NullPointerException nullPointer) {
            Alerta.alertar("Selecione a Categoria");
        }
        
        try {
            
            Double valor = Double.parseDouble(this.valor.getText());
            fachada.alterarProduto(this.codigo.getText(), tipoDeRoupa,
                    this.descricaoDoProduto.getText(), faixaEtaria, 
                    genero, this.cor.getText(),
                    tamanho, categoria, this.quantidade.getValue(), valor);
            
            this.mensagem.setText("Dados alterados com sucesso");
        } catch (ProdutoInvalidoException produtoInvalido) {
            Alerta.alertar(produtoInvalido.getMessage());
        } catch (NumberFormatException numberFormatException){
            Alerta.alertar("Valor inválido");
        }
            
    }
    
    @FXML
    private void removerProduto(ActionEvent event) {
        
        FachadaFuncionario fachada = FachadaFuncionario.getInstance(); // fachFunc?
        
        Produto produto = null;
        try {
            produto = fachada.buscarProdutoPorCodigo(this.codigo.getText());
        } catch (ProdutoNaoEncontradoException produtoNaoEncontrado) {
            Alerta.alertar(produtoNaoEncontrado.getMessage());
        }
        
        if(produto != null) {
        
            if(produto.getAtivo() != false) {
                
                try {
                    fachada.desabilitarProduto(this.codigo.getText());
                    this.mensagemRemovido.setText("Produto removido");
                    this.botaoAlterar.setDisable(true);
                }
                catch (ProdutoNaoEncontradoException produtoNaoEncontado) {
                }
            }
        }
    }
    
}
