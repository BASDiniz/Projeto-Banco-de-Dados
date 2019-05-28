package negocio.excecao.cliente;

public class ClienteNaoEncontradoException extends ClienteException {
    
    public ClienteNaoEncontradoException() {
        super("Nenhum cliente foi encontrado.");
    }
}
