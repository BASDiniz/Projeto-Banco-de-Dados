package gui.historicoFidelidade;

import fachada.FachadaFuncionario;
import fachada.FachadaGerente;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import negocio.entidade.Cliente;

public class FXMLHistoricoFidelidadeController implements Initializable {
    
    @FXML
    private TableView<Cliente> tabela;

    @FXML
    private TableColumn<Cliente, String> nome;

    @FXML
    private TableColumn<Cliente, String> cpf;

    @FXML
    private TableColumn<Cliente, String> telefonePrincipal;

    @FXML
    private TableColumn<Cliente, String> email;

    @FXML
    private TableColumn<Cliente, String> fiel;

    @FXML
    private TableColumn<Cliente, String> ativo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        this.cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
        this.telefonePrincipal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cliente, String> c) {
                return new ReadOnlyObjectWrapper(c.getValue().getContato().getTelefonePrincipal());
            }
        });
        
        this.email.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Cliente, String> c) {
                return new ReadOnlyObjectWrapper(c.getValue().getContato().getEmail());
            }
        });
        
        this.fiel.setCellValueFactory(new PropertyValueFactory<Cliente, String>("fiel"));
        this.ativo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ativo"));
        

        FachadaGerente fachadaGerente = FachadaGerente.getInstance();
        ArrayList<Cliente> listaDeClientes = fachadaGerente.getFachadaGerente().listarClientesFieis();
        this.tabela.setItems(FXCollections.observableArrayList(listaDeClientes));
    }    
    
}
