package gui.historicoVenda;

import fachada.FachadaGerente;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.entidade.Venda;
import negocio.entidade.produto.Produto;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;


public class FXMLHistoricoVendaController implements Initializable {

    @FXML
    private TableView<Venda> tabela;

    @FXML
    private TableColumn<Venda, Integer> codigoVenda;

    @FXML
    private TableColumn<Venda, String> cpfFunc;

    @FXML
    private TableColumn<Venda, String> cpfCliente;

    @FXML
    private TableColumn<Venda, String> dataVenda;

    @FXML
    private TableColumn<Venda, String> parcelado;

    @FXML
    private TableColumn<Venda, String> valor;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.codigoVenda.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("cod"));
        this.cpfFunc.setCellValueFactory(new PropertyValueFactory<Venda, String>("cpfFunc"));
        this.cpfCliente.setCellValueFactory(new PropertyValueFactory<Venda, String>("cpfCliente"));
        this.dataVenda.setCellValueFactory(new PropertyValueFactory<Venda, String>("data"));
//        this.carrinhoVenda.setCellValueFactory(new PropertyValueFactory<VendaFormatada, ArrayList<Produto>>("carrinho"));
        this.parcelado.setCellValueFactory(new PropertyValueFactory<Venda, String>("parcelado"));
        this.valor.setCellValueFactory(new PropertyValueFactory<Venda, String>("valor"));

    }
    @FXML
    public void buscarHistoricoVendas(){
        FachadaGerente fachada = FachadaGerente.getInstance();
        ArrayList<Venda> vendas = fachada.historicoVendas();

    }
}
