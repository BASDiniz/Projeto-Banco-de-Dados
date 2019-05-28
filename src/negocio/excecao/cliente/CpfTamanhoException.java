package negocio.excecao.cliente;

public class CpfTamanhoException extends DadosInvalidosException {
    
    public CpfTamanhoException() {
        super("Um CPF deve ter 11 digitos");
    }
    
}
