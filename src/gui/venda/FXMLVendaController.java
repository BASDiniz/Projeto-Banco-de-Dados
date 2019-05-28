package gui.venda;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import fachada.FachadaFuncionario;
import fachada.FachadaGerente;
import gui.mensagemDeErro.Alerta;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.entidade.Cliente;
import negocio.entidade.produto.Produto;
import negocio.excecao.cliente.ClienteNaoEncontradoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.produto.ProdutoNaoEncontradoException;
import negocio.excecao.venda.CarrinhoVazioException;
import negocio.excecao.venda.ClienteInativoException;
import negocio.excecao.venda.ClienteNegadoException;
import negocio.excecao.venda.ProdutoQuantidadeInsuficienteException;


public class FXMLVendaController implements Initializable {

    @FXML
    private TableView<Produto> tabelaCarrinho;

    @FXML
    private TableColumn<Produto, String> codigoCarrinho;

    @FXML
    private TableColumn<Produto, String> descricaoCarrinho;

    @FXML
    private TableColumn<Produto, String> valorCarrinho;

    @FXML
    private TableView<Produto> tabelaProduto;

    @FXML
    private TableColumn<Produto, String> codigo;

    @FXML
    private TableColumn<Produto, String> descricao;

    @FXML
    private TableColumn<Produto, String> quantidade;

    @FXML
    private TableColumn<Produto, String> valor;

    @FXML
    private TextField codigoProduto;

    @FXML
    private TextField buscarCliente;

    @FXML
    private TextArea descricaoVenda;

    @FXML
    private ComboBox buscar;

    @FXML
    private ComboBox pagamento;

    @FXML
    private JFXButton botaoBuscarCliente;

    @FXML
    private TableView<Cliente> tabelaCliente;

    @FXML
    private TableColumn<Cliente, String> nome;

    @FXML
    private TableColumn<Cliente, String> cpf;

    @FXML
    private Label valorTotal;

    @FXML
    private Label vendaRealizada;

    private ArrayList<Produto> carrinhoProdutos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.carrinhoProdutos = new ArrayList<Produto>();

        this.buscar.setItems(FXCollections.observableArrayList("CPF", "Nome"));
        this.pagamento.setItems(FXCollections.observableArrayList("À vista", "Parcelado"));

        this.codigoCarrinho.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        this.descricaoCarrinho.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricaoDoProduto"));
        this.valorCarrinho.setCellValueFactory(new PropertyValueFactory<Produto, String>("valorVenda"));

        this.codigo.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        this.descricao.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricaoDoProduto"));
        this.quantidade.setCellValueFactory(new PropertyValueFactory<Produto, String>("quantidade"));
        this.valor.setCellValueFactory(new PropertyValueFactory<Produto, String>("valorVenda"));

        this.nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        this.cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));

        this.botaoBuscarCliente.setDisable(true);
    }

    @FXML
    public void buscarProduto(ActionEvent event){
        FachadaFuncionario fachada = FachadaFuncionario.getInstance();
        try {
            Produto produto = fachada.buscarProdutoPorCodigo(this.codigoProduto.getText());
            this.tabelaProduto.setItems(FXCollections.observableArrayList(produto));
        }
        catch (ProdutoNaoEncontradoException produtoNaoEncontrado) {
            Alerta.alertar(produtoNaoEncontrado.getMessage());
        }
    }

    @FXML
    public void habilitarBotaoBuscarCliente(ActionEvent event){
        if(this.buscar.getValue().equals("CPF") || this.buscar.getValue().equals("Nome")){
            this.botaoBuscarCliente.setDisable(false);
        }
    }

    @FXML
    public void buscarCliente(ActionEvent event){
        FachadaGerente fachada = FachadaGerente.getInstance();
        if(this.buscar.getValue().equals("CPF")){
            try {
                Cliente cliente = fachada.getFachadaGerente().buscarPorCpf(this.buscarCliente.getText());
                this.tabelaCliente.setItems(FXCollections.observableArrayList(cliente));

            }
            catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                Alerta.alertar(clienteNaoEncontrado.getMessage());
            }

        }
        else if(this.buscar.getValue().equals("Nome")){

            if(!this.buscarCliente.getText().isEmpty()) {
                try {
                    ArrayList<Cliente> listaDeClientes = fachada.getFachadaGerente().listarPorNomeCliente(this.buscarCliente.getText());

                    this.tabelaCliente.setItems(FXCollections.observableArrayList(listaDeClientes));
                }
                catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
                    Alerta.alertar(clienteNaoEncontrado.getMessage());
                }
            }
        }
    }

    private void atualizarValorTotal(){
        double valorTotal = 0;
        for(int i = 0; i < this.carrinhoProdutos.size(); i++){
            valorTotal += this.carrinhoProdutos.get(i).getValorVenda();
        }
        String valor = String.valueOf(valorTotal);
        this.valorTotal.setText(valor);
    }

    @FXML
    public void adicionarAoCarrinho(ActionEvent event){
        try{
            Produto produto = this.tabelaProduto.getSelectionModel().getSelectedItem();
            if(produto.getQuantidade() > 0){
                this.carrinhoProdutos.add(produto);
                this.tabelaCarrinho.setItems(FXCollections.observableArrayList(carrinhoProdutos));
                atualizarValorTotal();
            }else{
                Alerta.alertar("Quantidade insuficiente");
            }
        }
        catch(NullPointerException nullPointer){
            Alerta.alertar("Nenhum produto selecionado");
        }



    }

    @FXML
    public void removerDoCarrinho(ActionEvent event){
        this.tabelaCarrinho.getItems().remove(this.tabelaProduto.getSelectionModel().getSelectedItem());
        this.carrinhoProdutos.remove(this.tabelaProduto.getSelectionModel().getSelectedItem());
        atualizarValorTotal();
    }

    @FXML
    public void limparCarrinho(ActionEvent event){
        this.tabelaCarrinho.getItems().clear();
        this.carrinhoProdutos.clear();
        atualizarValorTotal();
    }

    @FXML
    public void realizarVenda(ActionEvent event){
        FachadaFuncionario fachada = FachadaFuncionario.getInstance();
        boolean pagamentoParcelado = false;
        try{
            if(this.pagamento.getValue().equals("Parcelado")){
                pagamentoParcelado = true;
            }
        }

        catch (NullPointerException nullPointer){
            Alerta.alertar("Nenhuma opção de pagamento selecionada");
        }

        try {
            Cliente cliente = this.tabelaCliente.getSelectionModel().getSelectedItem();
            fachada.adicionarVenda(cliente.getCpf(), pagamentoParcelado, this.carrinhoProdutos, this.descricaoVenda.getText());
            this.vendaRealizada.setText("Venda realizada com sucesso");
        }
        catch (CarrinhoVazioException carrinhoVazio) {
            Alerta.alertar(carrinhoVazio.getMessage());
        }
        catch (ClienteNaoEncontradoException clienteNaoEncontrado) {
            Alerta.alertar(clienteNaoEncontrado.getMessage());
        }
        catch (ContatoInvalidoException contatoInvalido) {
            Alerta.alertar(contatoInvalido.getMessage());
        }
        catch (ClienteNegadoException clienteNegado) {
            Alerta.alertar(clienteNegado.getMessage());
        }
        catch (DadosInvalidosException dadosInvalidos) {
            Alerta.alertar(dadosInvalidos.getMessage());
        }
        catch (ProdutoQuantidadeInsuficienteException produtoQuantidadeInsuficiente) {
            Alerta.alertar(produtoQuantidadeInsuficiente.getMessage());
        }
        catch (NullPointerException nullPointer){
            Alerta.alertar("Nenhum cliente foi selecionado");
        }
        catch(ClienteInativoException clienteInativo){
            Alerta.alertar(clienteInativo.getMessage());
        }
    }
    
}
