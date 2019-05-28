package negocio.excecao.cliente;

public class CpfApenasNumeroException extends DadosInvalidosException{
    
    public CpfApenasNumeroException() {
        super("O CPF só pode conter números");
    }
    
}
