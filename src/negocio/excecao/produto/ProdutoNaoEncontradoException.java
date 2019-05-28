package negocio.excecao.produto;

public class ProdutoNaoEncontradoException extends ProdutoException {
    
    public ProdutoNaoEncontradoException() {
        super("Nenhum produto foi encontrado");
    }
}
