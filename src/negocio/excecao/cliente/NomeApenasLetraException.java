package negocio.excecao.cliente;

public class NomeApenasLetraException extends DadosInvalidosException {
    
    public NomeApenasLetraException() {
        super("O nome só pode conter letras");
    }
}
