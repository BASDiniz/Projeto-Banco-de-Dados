package negocio.excecao.cliente.contato;

public class TelefoneApenasNumerosException extends ContatoInvalidoException {
    public TelefoneApenasNumerosException(String mensagem){
        super(mensagem);
    }
}
