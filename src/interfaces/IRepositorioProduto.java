package interfaces;
import negocio.entidade.produto.Produto;

import java.util.ArrayList;

public interface IRepositorioProduto {

    /**
     * Esse método verifica se o produto existe no repositório.
     * @param produto
     * @return
     */
    int procurarProduto(Produto produto);

    /**
     * Esse método adiciona um produto ao repositório
     * @param produto
     */
    void adicionarProduto(Produto produto);

    Produto buscarProdutoPorCodigo(String codigo);

    /**
     * Lista todos os produtos que começam com o tipo passado como parâmetro
     * @param tipo
     * @return retorna um ArrayList de produtos
     */
    ArrayList<Produto> listarProdutosPorTipo(String tipo);

    /**
     * Lista todos os produtos que começam com a descricao passada como parâmetro
     * @param descricao
     * @return retorna um ArrayList de produtos
     */
    ArrayList<Produto> listarProdutosPorDescricao(String descricao);

    /**
     * Lista todos os produtos que começam com a faixa etária passada como parâmetro
     * @param faixaEtaria
     * @return retorna um ArrayList de produtos
     */
    ArrayList<Produto> listarProdutosPorFaixaEtaria(String faixaEtaria);

    /**
     * Lista todos os produtos que começam com a categoria passada como parâmetro
     * @param categoria
     * @return retorna um ArrayList de produtos
     */
    ArrayList<Produto> listarProdutosPorCategoria(String categoria);

    /**
     * Esse método substitui um determinado objeto do tipo Produto por um novo objeto do tipo Produto.
     * @param produto
     * @param indiceProduto
     */
    void alterarProduto(Produto produto, int indiceProduto);

    void desabilitarProduto(int indiceProduto); // "remover"

    void salvarDados();

    void lerDados();
}
