package negocio.excecao.cliente;

public class NomeTamanhoException extends DadosInvalidosException {
    
    public NomeTamanhoException() {
        super("Digite seu nome e sobrenome");
    }
}
