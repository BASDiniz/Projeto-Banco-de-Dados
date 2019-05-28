package gui.telaPrincipalVendedor;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ControllerTelaPrincipalVendedor implements Initializable {
    
    @FXML
    private AnchorPane anchorPrincipal;

    @FXML
    private AnchorPane anchorVariavel; // painel onde serao carregadas as outras telas
    
    @FXML
    private JFXDrawer drawerMenu;

    @FXML
    private JFXHamburger hamburguer;
    
    public static AnchorPane rootPaneAberta; // E o mesmo que anchorVariavel, a diferenca e que e visivel em outras classes
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rootPaneAberta = anchorVariavel;
        
        // Carrega o Painel Lateral(VBox) no drawer.
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/gui/telaPrincipalVendedor/menu/FXMLConteudoMenuHamburguer.fxml"));
            drawerMenu.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(ControllerTelaPrincipalVendedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Animacao do hambuger.
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburguer);
        transition.setRate(-1);
        hamburguer.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();
            
            // Se o drawer(painel do menu lateral) estiver aberto, entao feche (ao clicar no hamburger)
            if(drawerMenu.isShown()) {
                drawerMenu.close();
            }
            
            // Senao abra o drawer(painel do menu lateral) ao clicar no hamburgeR
            else
                drawerMenu.open();
            });
    }
}
