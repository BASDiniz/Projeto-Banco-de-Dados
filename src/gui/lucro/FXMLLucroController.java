package gui.lucro;


import java.net.URL;
import java.util.ResourceBundle;

import fachada.FachadaGerente;
import gui.mensagemDeErro.Alerta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLLucroController implements Initializable {

    @FXML
    private TextField ano;

    @FXML
    private TextField mes;

    @FXML
    private Label lucroAnual;

    @FXML
    private Label lucroMensal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    public void verLucroAnual(){
        try{
            int ano = Integer.parseInt(this.ano.getText());
            FachadaGerente fachada = FachadaGerente.getInstance();
            double lucro = fachada.getFachadaGerente().determinarLucroAnual(ano);
            this.lucroAnual.setText("O lucro no ano " + ano + " foi de: R$ " + String.valueOf(lucro));

        }
        catch (NumberFormatException numberFormatException){
            Alerta.alertar("Ano inválido");
        }

    }

    @FXML
    public void verLucroMensal(){
        try{
            int mes = Integer.parseInt(this.mes.getText());
            FachadaGerente fachada = FachadaGerente.getInstance();
            double lucro = fachada.getFachadaGerente().determinarLucroMensal(mes);
            this.lucroMensal.setText("O lucro no mês " + mes + " foi de: R$ " + String.valueOf(lucro));

        }catch (NumberFormatException numberFormatException){
            Alerta.alertar("Mês inv?lido");
        }

    }
    
}
