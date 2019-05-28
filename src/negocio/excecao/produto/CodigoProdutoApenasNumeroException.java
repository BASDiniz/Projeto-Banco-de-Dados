package negocio.excecao.produto;

public class CodigoProdutoApenasNumeroException extends ProdutoInvalidoException { // retira Produto?
    
    public CodigoProdutoApenasNumeroException() {
        super("O código só deve conter numeros");
    }
}
