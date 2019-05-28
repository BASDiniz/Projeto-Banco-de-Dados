package negocio.excecao.produto;

public class CodigoVazioExcepiton extends ProdutoInvalidoException {
    
    public CodigoVazioExcepiton() {
        super("O código não pode ficar vazio");
    }
    
}
