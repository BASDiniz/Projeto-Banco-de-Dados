package negocio.excecao.produto;

public class ProdutoValorNegativoException extends ProdutoInvalidoException {
    
    public ProdutoValorNegativoException() {
        super("O valor do produto não pode ser negativo");
    }
}
