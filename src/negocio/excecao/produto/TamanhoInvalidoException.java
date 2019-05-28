package negocio.excecao.produto;

public class TamanhoInvalidoException extends ProdutoInvalidoException {
    
    public TamanhoInvalidoException() {
       super("O tamanho está incorreto");
    }
    
}
