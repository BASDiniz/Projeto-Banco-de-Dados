package negocio.excecao.produto;

public class ProdutoInvalidoException extends Exception {
    
    public ProdutoInvalidoException(String s) {
        super(s);
    }
    
    public ProdutoInvalidoException() { // ver se precisa disso mesmo
    }
}
