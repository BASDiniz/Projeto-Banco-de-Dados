package negocio.excecao.cliente;

public class ClienteJaCadastradoException extends ClienteException {
    
    public ClienteJaCadastradoException() {
        super("Esse cliente já possui um cadastro.");
    }
}
