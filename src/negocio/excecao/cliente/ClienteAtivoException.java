package negocio.excecao.cliente;

public class ClienteAtivoException extends ClienteException {
    
    public ClienteAtivoException() {
        super("O cliente não pode ser desativado, pois possui pendencias");
    }
}
