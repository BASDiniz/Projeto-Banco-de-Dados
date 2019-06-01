package negocio;

import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.entidade.produto.Produto;
import negocio.entidade.Venda;
import negocio.excecao.cliente.funcionario.FuncionarioDoMesNaoEncontradoException;
import negocio.excecao.venda.CarrinhoVazioException;
import negocio.excecao.venda.ClienteInativoException;
import negocio.excecao.venda.ClienteNegadoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import interfaces.IRepositorioVenda;
import java.util.ArrayList;
import java.util.Calendar;
import negocio.excecao.cliente.ClienteFielNaoEncontradoException;
import negocio.excecao.venda.ProdutoQuantidadeInsuficienteException;
import repositorio.RepositorioProduto;

/**
 * Classe de neg�cio Venda
 */
public class NegocioVenda {

    private IRepositorioVenda repositorioVenda;

    /**
     * Construtor NegocioVenda
     * @param repositorioVenda
     */
    public NegocioVenda(IRepositorioVenda repositorioVenda) {
        this.repositorioVenda = repositorioVenda;
    }

    /**
     * M�todo para adicionar uma venda
     * @param venda
     * @param repositorioProduto
     * @throws ClienteNegadoException
     * @throws ContatoInvalidoException
     * @throws DadosInvalidosException
     * @throws ProdutoQuantidadeInsuficienteException
     * @throws CarrinhoVazioException
     * @throws ClienteInativoException
     */
    public void adicionarVenda(Venda venda, RepositorioProduto repositorioProduto) throws ClienteNegadoException, ContatoInvalidoException,
            DadosInvalidosException, ProdutoQuantidadeInsuficienteException, CarrinhoVazioException, ClienteInativoException {

        if(venda.verificarVenda() && verificarQuantidadeDosProdutos(venda, repositorioProduto)) {
            if(venda.getCliente().getFiel() == true) {
                venda.descontarVenda();
                venda.getCliente().setFiel(false);
            }
            diminuirQuantidadeDosProdutos(venda, repositorioProduto);
            this.repositorioVenda.adicionarVenda(venda);

        }
    }

    //M�todo que verifica a quantidade dos produtos

    private boolean verificarQuantidadeDosProdutos(Venda venda, RepositorioProduto repositorioProduto) throws ProdutoQuantidadeInsuficienteException{

        for(int i = 0; i < venda.getCarrinhoProdutos().size(); i++) {

            String codigo = venda.getCarrinhoProdutos().get(i).getCodigo();
            Produto produtoDoRepositorio = repositorioProduto.buscarProdutoPorCodigo(codigo);

            int contador = 0;

            for(int k = 0; k < venda.getCarrinhoProdutos().size(); k++) {

                if(produtoDoRepositorio.equals(venda.getCarrinhoProdutos().get(k))) {
                    contador++;
                }
            }
            if(produtoDoRepositorio.getQuantidade() < contador) {
                throw new ProdutoQuantidadeInsuficienteException(produtoDoRepositorio.getDescricaoDoProduto());
            }
        }
        return true;
    }

    //M�todo que diminui a quantidade dos produtos ap�s uma venda

    private void diminuirQuantidadeDosProdutos(Venda venda, RepositorioProduto repositorioProduto) {

        for(int i = 0; i < venda.getCarrinhoProdutos().size(); i++) {

            String codigo = venda.getCarrinhoProdutos().get(i).getCodigo();
            Produto produtoDoRepositorio = repositorioProduto.buscarProdutoPorCodigo(codigo);

            if(produtoDoRepositorio != null){
                produtoDoRepositorio.setQuantidade((produtoDoRepositorio.getQuantidade() - 1));
            }
        }
    }

    /**
     * M�todo que determina um lucro mensal em um dado m�s
     * @param mes
     * @return retorna o lucro mensal
     * @throws NumberFormatException
     */
    public double determinarLucroMensal(int mes) throws NumberFormatException{
        return this.repositorioVenda.verLucroMensal(mes);

    }

    /**
     * M�todo que determina um lucro anual em um dado ano
     * @param ano
     * @return retorna o lucro anual
     * @throws NumberFormatException
     */
    public double determinarLucroAnual(int ano)throws NumberFormatException{
        return this.repositorioVenda.verLucroAnual(ano);
    }

    /**
     * M�todo que determina o funcion�rio do m�s(funcion�rio que mais vendeu) em um dado m�s
     * @param mes
     * @return retorna o funcion�rio do m�s
     * @throws NumberFormatException
     * @throws FuncionarioDoMesNaoEncontradoException
     */
    public Funcionario determinarFuncionarioDoMes(int mes) throws NumberFormatException, FuncionarioDoMesNaoEncontradoException {
        if(this.repositorioVenda.determinarFuncionarioDoMes(mes) == null){
            throw new FuncionarioDoMesNaoEncontradoException();
        }else{
            return this.repositorioVenda.determinarFuncionarioDoMes(mes);
        }
    }

    /**
     * M�todo que determina o cliente fiel(cliente que mais gastou) em um dado m�s
     * @param mes
     * @return retorna o cliente fiel
     * @throws NumberFormatException
     * @throws ClienteFielNaoEncontradoException
     */
    public Cliente determinarClienteFiel(int mes) throws NumberFormatException, ClienteFielNaoEncontradoException {
        if(this.repositorioVenda.determinarClienteFiel(mes) == null) {
            throw new ClienteFielNaoEncontradoException();
        }else {
            return this.repositorioVenda.determinarClienteFiel(mes);
        }
    }

    /**
     * M�todo que busca todos os cliente que possuem pend�ncias
     * @return retorna um ArrayList de cliente
     */
    public ArrayList<Cliente> buscarClientesComPendencia() {
        return this.repositorioVenda.buscarClientesComPendencia();
    }

    /**
     * M�todo que busca os produtos mais vendidos
     * @param mes
     * @return retorna um ArrayList de produtos
     * @throws NumberFormatException
     */
    public ArrayList<Produto> buscarProdutosMaisVendidos(int mes) throws NumberFormatException {
        return this.repositorioVenda.buscarProdutosMaisVendidos(mes);
    }

    /**
     * M�todo que verifica se � dia 28 e bonifica o funcion�rio do m�s
     */
    public void checarDiaDaBonificacao(){
        Calendar dataAtual = Calendar.getInstance();
        //Tava dia 28, mudei para 1 só pra testes
        Funcionario funcDoMes = this.repositorioVenda.determinarFuncionarioDoMes((dataAtual.get(Calendar.MONTH)) + 1);

        if(dataAtual.get(Calendar.DAY_OF_MONTH) == 1){
            if(funcDoMes != null){
                funcDoMes.bonificar();
                System.out.println(funcDoMes);
            }
        }
        else{
            funcDoMes.setFuncDoMes(false);
        }
    }

    /**
     * M�todo que verifica se � dia 28 e torna um determinado cliente em um cliente fiel
     */
    public void checarDiaDoClienteFiel() {
        Calendar dataAtual = Calendar.getInstance();
        if(dataAtual.get(Calendar.DAY_OF_MONTH) == 28) {
            Cliente clienteFiel = this.repositorioVenda.determinarClienteFiel((dataAtual.get(Calendar.MONTH)) + 1);

            if(clienteFiel != null){
                clienteFiel.setFiel(true);
                clienteFiel.getContato().enviarEmailClienteFiel();
            }
        }
    }

    /**
     * M�todo que salva os dados do repositorioVenda
     */
    public void salvarDados(){
        this.repositorioVenda.salvarDados();

    }

    /**
     * M�todo que faz a leitura dos dados do repositorioVenda
     */
    public void lerDados(){
        this.repositorioVenda.lerDados();
    }

}