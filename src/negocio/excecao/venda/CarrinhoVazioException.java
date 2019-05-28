package negocio.excecao.venda;

public class CarrinhoVazioException extends Exception {

    public CarrinhoVazioException() {
        super("O carrinho de produtos está vazio. Adicione produtos produtos ao carrinho para pode realizar uma venda.");
    }
}
