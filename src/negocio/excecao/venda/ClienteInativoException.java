package negocio.excecao.venda;

public class ClienteInativoException extends VendaInvalidaException {
    public ClienteInativoException(){
        super("O cliente está inativo");
    }
}
