package negocio.excecao.produto;

public class FaixaEtariaInvalidaException extends ProdutoInvalidoException {
    
    public FaixaEtariaInvalidaException() {
        super("Faixa etária inválida");
    }
}
