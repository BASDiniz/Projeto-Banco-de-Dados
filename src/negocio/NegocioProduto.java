package negocio;

import interfaces.IRepositorioProduto;
import negocio.entidade.produto.Produto;
import negocio.excecao.produto.ProdutoInvalidoException;
import negocio.excecao.produto.ProdutoJaCadastradoException;
import negocio.excecao.produto.ProdutoNaoEncontradoException;

import java.util.ArrayList;

/**
 * Classe de Neg�cio Produto.
 * @author �verton Vieira
 */
public class NegocioProduto {

    private IRepositorioProduto repositorioProduto;

    public NegocioProduto(IRepositorioProduto repositorioProduto) {
        this.repositorioProduto = repositorioProduto;
    }

    /**
     * M�todo que adiciona um produto.
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
     * M�todo que busca um produto por c�digo
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

    // M�todo axiliar para m�todos de listagem de produtos
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
     * M�todo que lista os produtos pelo seu tipo
     * @param tipo
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorTipo(String tipo) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorTipo(tipo));
    }

    /**
     * M�todo que lista os produtos pela sua descri��o
     * @param descricao
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorDescricao(String descricao) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorDescricao(descricao));
    }

    /**
     * M�todo que lista os produtos pela sua faixa et�ria
     * @param faixaEtaria
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorFaixaEtaria(String faixaEtaria) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorFaixaEtaria(faixaEtaria));
    }

    /**
     * M�todo que lista os produtos pela sua categoria
     * @param categoria
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorCategoria(String categoria) throws ProdutoNaoEncontradoException {
        return listarProdutos(this.repositorioProduto.listarProdutosPorCategoria(categoria));
    }

    /**
     * M�todo que altera um produto
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
     * M�todo que desabilita um produto
     * @param produto
     */
    public void desabilitarProduto(Produto produto) {

        int indiceProduto = this.repositorioProduto.procurarProduto(produto);

        if(indiceProduto != -1) {
            this.repositorioProduto.desabilitarProduto(indiceProduto);
        }
    }
}