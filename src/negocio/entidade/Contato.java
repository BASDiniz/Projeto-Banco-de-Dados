package negocio.entidade;

import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.contato.TelefoneTamanhoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe que representa um contato.
 * @author Bruno Diniz
 * @author Everton Vieira
 * @version 2
 */
public class Contato implements Serializable {
    private static final ArrayList OPCOES_DOMINIO = new ArrayList(Arrays.asList("@gmail.com", "@hotmail.com", "@outlook.com", "@yahoo.com"));
    private String telefonePrincipal, telefoneAlternativo, email;

    /**
     * Construtor Contato.
     * @param telefonePrincipal
     * @param telefoneAlternativo
     * @param email
     */
    public Contato(String telefonePrincipal, String telefoneAlternativo, String email){
        this.telefonePrincipal = telefonePrincipal;
        this.telefoneAlternativo = telefoneAlternativo;
        this.email = email;
    }

    //M?todos Getters e Setters

    public String getTelefonePrincipal() {
        return this.telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneAlternativo() {
        return this.telefoneAlternativo;
    }

    public void setTelefoneAlternativo(String telefoneAlternativo) {
        this.telefoneAlternativo = telefoneAlternativo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //M?todo que checa o dominio do e-mail

    private String checarDominio(String email){
        for(int i = 0; i < email.length(); i++){
            if(email.charAt(i) == '@') {
                return email.substring(i, email.length());
            }
        }
        return null;
    }


    //M?todo que verifica o tamanho do atributo "telefonePrincipal"

    private boolean verificarTamanhoTelefonePrincipal(){
        if(this.telefonePrincipal.length() != 11) {
            return false;
        }
        return true;
    }

    //M?todo que verifica o tamanho do atributo "telefoneAlternativo"

    private boolean verificarTamanhoTelefoneAlternativo(){
        if(this.telefoneAlternativo.length() != 11) {
            return false;
        }
        return true;
    }

    //M?todo que verifica se um telefone cont?m apenas n?meros

    private boolean verificarTelefonesApenasNumeros(String telefone) {
        for (int i = 0; i < telefone.length(); i++) {
            if (!Character.isDigit(telefone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //Metodo que verifica se o atributo "email" ? v?lido.

    private boolean verificarEmail() {
        if(OPCOES_DOMINIO.contains(checarDominio(this.email))){
            return true;
        }
        return false;
    }

    /**
     * Método que verifica se todos os atributos do objeto é válido.
     * @returnRetorna "true" se todos os atributos forem válidos, caso contrário, retorna "false".
     * @throws ContatoInvalidoException
     */
    public boolean verificarContato() throws ContatoInvalidoException {

        if(verificarTamanhoTelefonePrincipal() != true) {
            throw new TelefoneTamanhoException("O telefone principal não contém 11 digitos");

        }else if(verificarTamanhoTelefoneAlternativo() != true){
            throw new TelefoneTamanhoException("O telefone alternativo não contém 11 digitos");

        }else if(verificarTelefonesApenasNumeros(this.telefonePrincipal) != true){
            throw new TelefoneTamanhoException("O telefone principal deve conter apenas números");

        }else if(verificarTelefonesApenasNumeros(this.telefoneAlternativo) != true){
            throw new TelefoneTamanhoException("O telefone alternativo deve conter apenas números");
        }
        else if(verificarEmail() != true) {
            throw new ContatoInvalidoException("O dominio " + this.checarDominio(this.email) + " é inválido.");
        }
        return true;
    }

    // Metodo auxiliar - Apenas para imprimir o atributo "telefonePrincipal" de forma organizada.
    private String imprimirTelefonePrincipal(){
        return "(" + this.telefonePrincipal.substring(0,2) + ")" + this.telefonePrincipal.substring(2,7) + "-"
                + this.telefonePrincipal.substring(7,11);
    }

    // Metodo auxiliar - Apenas para imprimir o atributo "telefoneAlternativo" de forma organizada.
    private String imprimirTelefoneAlternativo(){
        return "(" + this.telefoneAlternativo.substring(0,2) + ")" + this.telefoneAlternativo.substring(2,7) + "-"
                + this.telefoneAlternativo.substring(7,11);
    }
    
    /**
     * M?todo que representa o envio de uma mensagem padr?o para notificar o cliente fiel.
     * @return retorna uma string para representar uma mensagem padrão.
     */
    public String enviarEmailClienteFiel() {
        return "Parabens! Voce foi o cliente que realizou mais compras na NewStyle Store. " +
                "Sendo assim, voce ganhar? 10% de desconto na sua proxima compra.";
    }

    /**
     * Sobrescrita do m?todo toString.
     * @return retorna o objeto Contato como uma string
     */
    @Override
    public String toString(){
        return "Telefone Principal: " + imprimirTelefonePrincipal() + "\n" + "Telefone Alternativo: " + imprimirTelefoneAlternativo() + "\n" + "Email: " + this.email + "\n";
    }

}