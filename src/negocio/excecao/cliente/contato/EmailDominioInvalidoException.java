package negocio.excecao.cliente.contato;

/**
 *
 * @author EVERTON VIEIRA
 */
public class EmailDominioInvalidoException extends ContatoInvalidoException {
    
    public EmailDominioInvalidoException(String dominio) {
        super(dominio + " não é um dominio válido.");
    }
}
