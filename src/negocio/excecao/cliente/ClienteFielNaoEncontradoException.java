package negocio.excecao.cliente;

public class ClienteFielNaoEncontradoException extends Exception {
    
    public ClienteFielNaoEncontradoException() {
        super("Não há nenhum cliente fiel no momento");
    }
}
