package gui.historicoVenda;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.entidade.Venda;

import java.net.URL;
import java.util.ResourceBundle;


public class FXMLHistoricoVendaController implements Initializable {

    @FXML
    private TableView<Venda> tabela;

    @FXML
    private TableColumn<Venda, String> codigoVenda;

    @FXML
    private TableColumn<Funcionario, String> cpfFunc;

    @FXML
    private TableColumn<Cliente, String> cpfCliente;

    @FXML
    private TableColumn<Venda,String> dataVenda;

    @FXML
    private TableColumn<Venda, String> carrinhoVenda;

    @FXML
    private TableColumn<Venda, String> parcelado;

    @FXML
    private TableColumn<Venda, String> valor;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("OK");
    }
}
