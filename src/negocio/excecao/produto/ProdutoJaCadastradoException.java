package negocio.excecao.produto;

public class ProdutoJaCadastradoException extends ProdutoException {
    
    public ProdutoJaCadastradoException() {
        super("O produto ja tem um cadastro.");
    }
}
