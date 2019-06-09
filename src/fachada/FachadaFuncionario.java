package fachada;

import negocio.NegocioCliente;
import negocio.NegocioFuncionario;
import negocio.NegocioProduto;
import negocio.NegocioVenda;
import negocio.entidade.*;
import negocio.excecao.cliente.ClienteJaCadastradoException;
import negocio.excecao.cliente.ClienteNaoEncontradoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.funcionario.FuncionarioDoMesNaoEncontradoException;
import negocio.excecao.cliente.funcionario.FuncionarioInativoException;
import negocio.excecao.cliente.funcionario.FuncionarioNaoCadastradoException;
import negocio.excecao.cliente.funcionario.SenhaIncorretaException;
import negocio.excecao.produto.ProdutoInvalidoException;
import negocio.excecao.produto.ProdutoJaCadastradoException;
import negocio.excecao.produto.ProdutoNaoEncontradoException;
import negocio.excecao.venda.CarrinhoVazioException;
import negocio.excecao.venda.ClienteInativoException;
import negocio.excecao.venda.ClienteNegadoException;
import negocio.excecao.venda.ProdutoQuantidadeInsuficienteException;
import repositorio.*;

import java.util.ArrayList;
import negocio.entidade.produto.Produto;
import negocio.excecao.cliente.ClienteAtivoException;
import negocio.excecao.cliente.ClienteFielNaoEncontradoException;

/**
 * Classe que se comunica com a GUI - FachadaFuncionario
 */
public class FachadaFuncionario{

    private static FachadaFuncionario fachada;

    private NegocioFuncionario negocioFuncionario;
    private NegocioCliente negocioCliente;
    private NegocioProduto negocioProduto;
    private NegocioVenda negocioVenda;

    private RepositorioCliente repositorioCliente;
    private RepositorioProduto repositorioProduto;
    private RepositorioVenda repositorioVenda;

    /**
     * Construtor FachadaFuncionario
     */
    private FachadaFuncionario(){

        this.repositorioProduto = new RepositorioProduto();
        this.repositorioVenda = new RepositorioVenda();


        this.negocioCliente = new NegocioCliente(this.repositorioCliente = RepositorioCliente.getInstance());
        this.negocioFuncionario = new NegocioFuncionario(this.repositorioCliente = RepositorioCliente.getInstance());
        this.negocioProduto = new NegocioProduto(this.repositorioProduto);
        this.negocioVenda = new NegocioVenda(this.repositorioVenda);
    }

    /**
     * Padr?o de projeto singleton
     * M?todo que cria ou retorna uma inst?ncia da FachadaFuncionario
     * @return FachadaFuncionario
     */
    public static FachadaFuncionario getInstance(){
        if(fachada == null){
            return fachada = new FachadaFuncionario();
        }
        else{
            return fachada;
        }
    }

    //M?todos Getters e Setters

    public NegocioFuncionario getNegocioFuncionario(){
        return this.negocioFuncionario;
    }

    public RepositorioCliente getRepositorioCliente(){
        return this.repositorioCliente;
    }

    public RepositorioProduto getRepositorioProduto(){
        return this.repositorioProduto;
    }

    public RepositorioVenda getRepositorioVenda(){
        return this.repositorioVenda;
    }

    /**
     * M?todo que valida o login
     * @param cpf
     * @param senha
     * @return retorna o funcion?rio logado
     * @throws FuncionarioNaoCadastradoException
     * @throws SenhaIncorretaException
     */
    public Funcionario logar(String cpf, String senha) throws FuncionarioNaoCadastradoException, SenhaIncorretaException,
            FuncionarioInativoException {

        Funcionario funcionario = this.negocioFuncionario.logar(cpf, senha);
        return funcionario;

    }

    //Cliente

    /**
     * M?todo para adicionar um cliente
     * @param nome
     * @param cpf
     * @param telefonePrincipal
     * @param telefoneAlternativo
     * @param email
     * @throws ClienteJaCadastradoException
     * @throws ContatoInvalidoException
     * @throws DadosInvalidosException
     */
    public void adicionarCliente(String nome, String cpf, String telefonePrincipal, String telefoneAlternativo,
                                 String email) throws ClienteJaCadastradoException, ContatoInvalidoException,
            DadosInvalidosException {

        Contato contato = new Contato(telefonePrincipal, telefoneAlternativo, email);
        Cliente cliente = new Cliente(nome, cpf, contato) ;

        this.negocioCliente.adicionarCliente(cliente);

    }

    /**
     * M?todo que busca um cliente pelo seu CPF
     * @param cpf
     * @return retorna o cliente buscado
     * @throws ClienteNaoEncontradoException
     */
    public Cliente buscarPorCpf(String cpf) throws ClienteNaoEncontradoException {
        return this.negocioCliente.buscarPorCpf(cpf);
    }

    /**
     * M?todo que lista todos os clientes com um determinado nome
     * @param nome
     * @return retorna um ArrayList com todos os clientes encontrados
     * @throws ClienteNaoEncontradoException
     */
    public ArrayList<Cliente> listarPorNomeCliente(String nome) throws ClienteNaoEncontradoException {
        return this.negocioCliente.listarPorNomeCliente(nome);
    }

    /**
     * M?todo para alterar um cliente
     * @param nome
     * @param cpf
     * @param telefonePrincipal
     * @param telefoneAlternativo
     * @param email
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     */
    public void alterarCliente(String nome, String cpf, String telefonePrincipal, String telefoneAlternativo,
                               String email) throws DadosInvalidosException, ContatoInvalidoException {

        Contato contato = new Contato(telefonePrincipal, telefoneAlternativo, email);
        Cliente cliente = new Cliente(nome, cpf, contato);

        this.negocioCliente.alterarCliente(cliente);
    }

    /**
     * M?todo que desabilita um cliente
     * @param cpf
     * @throws ClienteAtivoException
     * @throws ClienteNaoEncontradoException
     */
    public void desabilitarCliente(String cpf) throws ClienteAtivoException, ClienteNaoEncontradoException { 
        
        Cliente cliente = this.negocioCliente.buscarPorCpf(cpf);
        
        this.negocioCliente.desabilitarCliente(cliente, this.repositorioVenda);
    }

    /**
     * M?todo que habilita um cliente
     * @param cpf
     * @throws ClienteNaoEncontradoException
     */
    public void habilitarCliente(String cpf) throws ClienteNaoEncontradoException { // E se os outros dados estiverem invalidos?
        
        Cliente cliente = this.negocioCliente.buscarPorCpf(cpf);
        
        this.negocioCliente.habilitarCliente(cliente);
    }
    
    public ArrayList<Cliente> listarClientesFieis() {
        return this.negocioCliente.listarClientesFieis();
    }
    
    // Produto

    /**
     * M?todo para adicionar um produto
     * @param codigo
     * @param tipoDeRoupa
     * @param descricaoDoProduto
     * @param faixaEtaria
     * @param genero
     * @param cor
     * @param tamanho
     * @param categoria
     * @param quantidade
     * @param valorVenda
     * @throws ProdutoInvalidoException
     * @throws ProdutoJaCadastradoException
     */
    public void adicionarProduto(String codigo, String tipoDeRoupa, String descricaoDoProduto, String faixaEtaria, String genero,
                                 String cor, String tamanho, String categoria, int quantidade, double valorVenda
    ) throws ProdutoInvalidoException, ProdutoJaCadastradoException {

        Produto produto = new Produto(codigo, tipoDeRoupa, descricaoDoProduto, faixaEtaria, genero, cor, tamanho, categoria, quantidade, valorVenda);
        this.negocioProduto.adicionarProduto(produto);
    }

    /**
     * M?todo que busca um produto pelo c?digo
     * @param codigo
     * @return retorna o produto encontrado
     * @throws ProdutoNaoEncontradoException
     */
    public Produto buscarProdutoPorCodigo(String codigo) throws ProdutoNaoEncontradoException {
        return this.negocioProduto.buscarProdutoPorCodigo(codigo);
    }

    /**
     * M?todo que lista os produtos pelo seu tipo
     * @param tipo
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorTipo(String tipo) throws ProdutoNaoEncontradoException {
        return this.negocioProduto.listarProdutosPorTipo(tipo);
    }

    /**
     * M?todo que lista os produtos pela sua descri??o
     * @param descricao
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorDescricao(String descricao) throws ProdutoNaoEncontradoException {
        return this.negocioProduto.listarProdutosPorDescricao(descricao);
    }

    /**
     * M?todo que lista os produtos pela sua faixa et?ria
     * @param faixaEtaria
     * @return retorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorFaixaEtaria(String faixaEtaria) throws ProdutoNaoEncontradoException {
        return this.negocioProduto.listarProdutosPorFaixaEtaria(faixaEtaria);
    }

    /**
     * M?todo que lista os produtos pela sua descri??o
     * @param categoria
     * @returnretorna um ArraList com os produtos encontrados
     * @throws ProdutoNaoEncontradoException
     */
    public ArrayList<Produto> listarProdutosPorCategoria(String categoria) throws ProdutoNaoEncontradoException {
        return this.negocioProduto.listarProdutosPorCategoria(categoria);
    }

    /**
     * M?todo que altera um produto
     * @param codigo
     * @param tipoDeRoupa
     * @param descricaoDoProduto
     * @param faixaEtaria
     * @param genero
     * @param cor
     * @param tamanho
     * @param categoria
     * @param quantidade
     * @param valorVenda
     * @throws ProdutoInvalidoException
     */
    public void alterarProduto(String codigo, String tipoDeRoupa, String descricaoDoProduto, String faixaEtaria, String genero,
                               String cor, String tamanho, String categoria, int quantidade, double valorVenda
    ) throws ProdutoInvalidoException {

        Produto produto = new Produto(codigo, tipoDeRoupa, descricaoDoProduto, faixaEtaria, genero, cor, tamanho, categoria, quantidade, valorVenda);
        this.negocioProduto.alterarProduto(produto);
    }

    /**
     * M?todo que desabilita um produto
     * @param codigo
     * @throws ProdutoNaoEncontradoException
     */
    public void desabilitarProduto(String codigo) throws ProdutoNaoEncontradoException {

        Produto produto = this.negocioProduto.buscarProdutoPorCodigo(codigo);
        this.negocioProduto.desabilitarProduto(produto);

    }

    // Venda

    /**
     * M?todo para adicionar uma venda
     * @param cpf
     * @param pagamentoParcelado
     * @param carrinhoProdutos
     * @param descricao
     * @throws CarrinhoVazioException
     * @throws ClienteNaoEncontradoException
     * @throws ContatoInvalidoException
     * @throws ClienteNegadoException
     * @throws DadosInvalidosException
     * @throws ProdutoQuantidadeInsuficienteException
     * @throws ClienteInativoException
     */
    public void adicionarVenda(String cpf,  boolean pagamentoParcelado, ArrayList<Produto> carrinhoProdutos, String descricao)
            throws CarrinhoVazioException, ClienteNaoEncontradoException, ContatoInvalidoException, ClienteNegadoException, DadosInvalidosException,
            ProdutoQuantidadeInsuficienteException, ClienteInativoException {

        Funcionario funcionario = NegocioFuncionario.funcionarioLogado; // retorna o funcionario que ta logado e fazendo a venda
        Cliente cliente = buscarPorCpf(cpf); // retorna o cliente que ta comprando
        Venda venda;

        if(descricao != null){
            venda = new Venda(funcionario, cliente, carrinhoProdutos, descricao, pagamentoParcelado);
        }else{
            venda = new Venda(funcionario, cliente, carrinhoProdutos, pagamentoParcelado);
        }

        this.negocioVenda.adicionarVenda(venda, this.repositorioProduto);

    }

    /**
     * M?todo que determina o lucro mensal
     * @param mes
     * @return retorna o lucro mensal
     * @throws NumberFormatException
     */
    public double determinarLucroMensal(int mes)throws NumberFormatException{
        return this.negocioVenda.determinarLucroMensal(mes);
    }

    /**
     * M?todo que determina o lucro anual
     * @param ano
     * @return retorna o lucro anual
     * @throws NumberFormatException
     */
    public double determinarLucroAnual(int ano)throws NumberFormatException{
        return this.negocioVenda.determinarLucroAnual(ano);
    }

    /**
     * M?todo que determina o funcion?rio do m?s
     * @param mes
     * @return retorna o funcion?rio do m?s
     * @throws NumberFormatException
     * @throws FuncionarioDoMesNaoEncontradoException
     */
    public Funcionario determinarFuncionarioDoMes(int mes) throws NumberFormatException, FuncionarioDoMesNaoEncontradoException {
        return this.negocioVenda.determinarFuncionarioDoMes(mes);
    }

    /**
     * M?todo que determina o cliente fiel
     * @param mes
     * @return retorna o cliente fiel
     * @throws NumberFormatException
     * @throws ClienteFielNaoEncontradoException
     */
    public Cliente determinarClienteFiel(int mes) throws NumberFormatException, ClienteFielNaoEncontradoException { // Number?
        return this.negocioVenda.determinarClienteFiel(mes);

    }

    /**
     * M?todo que busca os produtos mais vendidos em um determinado m?s
     * @param mes
     * @return retorna um ArrayList com os produtos mais vendidos
     * @throws NumberFormatException
     */
    public ArrayList<Produto> buscarProdutosMaisVendidos(int mes) throws NumberFormatException { 
        return this.negocioVenda.buscarProdutosMaisVendidos(mes);
    }

    /**
     * M?todo para checar se ? dia 28 e bonificar o funcion?rio do m?s
     */
    public void checarDiaDaBonificacao(){
        this.negocioVenda.checarDiaDaBonificacao();
    }

    /**
     * M?todo para checar se ? dia 28 e tornar um cliente em cliente fiel
     */
    public void checarDiaDoClienteFiel() {
        this.negocioVenda.checarDiaDoClienteFiel(this.repositorioCliente = RepositorioCliente.getInstance());
    }

    /**
     * M?todo que salva os dados dos neg?cios
     */
    public void salvarDados(){
        this.negocioCliente.salvarDados();
        this.negocioCliente.salvarDadosFieis();
        this.negocioProduto.salvarDados();
        this.negocioVenda.salvarDados();
    }

    /**
     * M?todo que faz a leitura dos dados dos neg?cios
     */
    public void lerDados(){
        this.negocioCliente.lerDados();
        this.negocioCliente.lerDadosFieis();
        this.negocioProduto.lerDados();
        this.negocioVenda.lerDados();
    }

}
