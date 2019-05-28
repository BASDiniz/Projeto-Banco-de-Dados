package negocio.excecao.produto;

public class CorApenasLetraException extends ProdutoInvalidoException {
    
    public CorApenasLetraException() {
        super("Cor não pode haver números");
    }
}
