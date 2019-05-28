package negocio.excecao.cliente;

public class DadosInvalidosException extends Exception{
    public DadosInvalidosException(String mensagem){
        super(mensagem);
    }
}
