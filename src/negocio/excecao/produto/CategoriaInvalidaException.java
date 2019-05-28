package negocio.excecao.produto;

public class CategoriaInvalidaException extends ProdutoInvalidoException {
    
    public CategoriaInvalidaException() {
        super("Categoria inválida");
    }
    
}
