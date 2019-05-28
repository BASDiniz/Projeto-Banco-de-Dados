package negocio.excecao.venda;

public class ProdutoQuantidadeInsuficienteException extends VendaInvalidaException {

    public ProdutoQuantidadeInsuficienteException(String descricaoProduto) {
        super("Não há quantidade suficiente de " + descricaoProduto + " pra realizar a venda");
    }
}