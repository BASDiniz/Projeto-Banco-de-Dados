package negocio.excecao.produto;

public class GeneroInvalidoException extends ProdutoInvalidoException {
    
    public GeneroInvalidoException() {
        super("Gênero inválido");
    }
}
