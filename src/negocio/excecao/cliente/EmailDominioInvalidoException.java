package negocio.excecao.cliente;

/**
 *
 * @author EVERTON VIEIRA
 */
public class EmailDominioInvalidoException extends Exception {
    
    public EmailDominioInvalidoException(String dominio) {
        super(dominio + " não é um dominio válido.");
    }
}
