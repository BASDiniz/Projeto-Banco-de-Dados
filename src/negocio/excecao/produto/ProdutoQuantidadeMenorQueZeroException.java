package negocio.excecao.produto;

public class ProdutoQuantidadeMenorQueZeroException extends ProdutoInvalidoException {
    
    public ProdutoQuantidadeMenorQueZeroException() {
        super("A quantidade não pode ser menor que 1");
    }
}
