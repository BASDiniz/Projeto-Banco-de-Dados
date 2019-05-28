package negocio;

import interfaces.IRepositorioProduto;
import negocio.entidade.produto.Produto;
import negocio.excecao.produto.ProdutoInvalidoException;
import negocio.excecao.produto.ProdutoJaCadastradoException;
import negocio.excecao.produto.ProdutoNaoEncontradoException;

import java.util.ArrayList;

/**
 * Classe de Negócio Produto.
 * @author Éverton Vieira
 */
public class NegocioProduto {

    private IRepositorioProduto repositorioProduto;

    public NegocioProduto(IRepositorioProduto repositorioProduto) {
        this.repositorioProduto = repositorioProduto;
    }

    /**
     * Método que adiciona um produto.
     * @param produto
     * @throws ProdutoInvalidoException
     * @throws ProdutoJaCadastradoException
     */
    public void adicionarProduto(Produto produto) throws ProdutoInvalidoException, ProdutoJaCadastradoException {

        if(this.repositorioProduto.procurarProduto(produto) == -1 && produto.verificarProduto() == true) {
            this.repositorioProduto.adicionarProduto(produto);
        }

        else {
            throw new ProdutoJaCadastradoException();
        }
    }

    /**
     * Método que busca um produto por código
     * @param codigo
     * @return retorna o produto encontrado.
     * @throws ProdutoNaoEncontradoException
     */
    public Produto buscarProdutoPorCodigo(String codigo) throws ProdutoNaoEncontradoException {

        Produto produtoEncontrado = this.repositorioProduto.buscarProdutoPorCodigo(codigo);

        if(produtoEncontrado != null) {
            return produtoEncontrado;
        }

        else {
            throw new ProdutoNaoEncontradoException();
        }
    }

    // Método axiliar para métodos de listagem de produtos
    private ArrayList<Produto> listarProdutos(ArrayList<Produto> metodo) throws ProdutoNaoEncontradoException {

        ArrayList<Produto> produtosEncontrados = metodo;

        if(produtosEncontrados.size() > 0) {
            return produtosEncontrados;
        }

        else {
            throw new ProdutoNaoEncontradoException();
        }
    }

    /**
     * Método que lista os produtos pelo seu tipo
     * @param tipo
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorTipo(String tipo) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorTipo(tipo));
    }

    /**
     * Método que lista os produtos pela sua descrição
     * @param descricao
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorDescricao(String descricao) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorDescricao(descricao));
    }

    /**
     * Método que lista os produtos pela sua faixa etária
     * @param faixaEtaria
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorFaixaEtaria(String faixaEtaria) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorFaixaEtaria(faixaEtaria));
    }

    /**
     * Método que lista os produtos pela sua categoria
     * @param categoria
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorCategoria(String categoria) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorCategoria(categoria));
    }

    /**
     * Método que altera um produto
     * @param produto
     * @throws ProdutoInvalidoException
     */
    public void alterarProduto(Produto produto) throws ProdutoInvalidoException {

        int indiceProduto = this.repositorioProduto.procurarProduto(produto);

        if(this.repositorioProduto.procurarProduto(produto) != -1) {

            if(produto.verificarProduto() == true) {
                this.repositorioProduto.alterarProduto(produto, indiceProduto);
            }
        }
    }

    /**
     * Método que desabilita um produto
     * @param produto
     */
    public void desabilitarProduto(Produto produto) {

        int indiceProduto = this.repositorioProduto.procurarProduto(produto);

        if(indiceProduto != -1) {
            this.repositorioProduto.desabilitarProduto(indiceProduto);
        }
    }

    /**
     * Método que salva os dados do RepositorioProduto
     */
    public void salvarDados() {
        this.repositorioProduto.salvarDados();
    }

    /**
     * Método que faz a leitura dos dados do RepositorioProduto
     */
    public void lerDados() {
        this.repositorioProduto.lerDados();
    }

}