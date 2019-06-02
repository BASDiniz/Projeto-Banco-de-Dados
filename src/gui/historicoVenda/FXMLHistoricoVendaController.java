package gui.historicoVenda;

import fachada.FachadaGerente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.entidade.Venda;
import negocio.entidade.VendaForm;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;


public class FXMLHistoricoVendaController implements Initializable {

    @FXML
    private TableView<VendaForm> tabela;

    @FXML
    private TableColumn<VendaForm, String> codigoVenda;

    @FXML
    private TableColumn<VendaForm, String> cpfFunc;

    @FXML
    private TableColumn<VendaForm, String> cpfCliente;

    @FXML
    private TableColumn<VendaForm,String> dataVenda;

//    @FXML
//    private TableColumn<Venda, String> carrinhoVenda;

    @FXML
    private TableColumn<VendaForm, String> parcelado;

    @FXML
    private TableColumn<VendaForm, String> valor;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.codigoVenda.setCellValueFactory(new PropertyValueFactory<VendaForm, String>("cod"));
        this.cpfFunc.setCellValueFactory(new PropertyValueFactory<VendaForm, String>("cpfFunc"));
        this.cpfCliente.setCellValueFactory(new PropertyValueFactory<VendaForm, String>("cpfCli"));
        this.dataVenda.setCellValueFactory(new PropertyValueFactory<VendaForm, String>("data"));
        this.parcelado.setCellValueFactory(new PropertyValueFactory<VendaForm, String>("parcelado"));
        this.valor.setCellValueFactory(new PropertyValueFactory<VendaForm, String>("valor"));

        ArrayList<Venda> lista = FachadaGerente.getInstance().historicoVendas();
        ArrayList<VendaForm> show = new ArrayList<VendaForm>();
        for (Venda item:lista) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String data = sdf.format(item.getDataDeVenda().getTime());
//          item.getDataDeVenda()
            VendaForm temp = new VendaForm(Integer.toString(item.getCodigo()),item.getFuncionario().getCpf(),item.getCliente().getCpf(),data,Boolean.toString(item.getPagamenteParcelado()),Double.toString(item.getValorTotal()));
            show.add(temp);
        }
        this.tabela.setItems(FXCollections.observableArrayList(show));
    }



}
