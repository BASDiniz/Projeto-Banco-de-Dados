package negocio.excecao.venda;

public class ClienteNegadoException extends VendaInvalidaException {

    public ClienteNegadoException(){
        super("Um funcionário não pode vender para ele mesmo");
    }

}
