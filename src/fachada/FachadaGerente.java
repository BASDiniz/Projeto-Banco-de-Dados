package fachada;

import negocio.entidade.Cliente;
import negocio.entidade.Contato;
import negocio.entidade.Funcionario;
import negocio.entidade.Venda;
import negocio.excecao.cliente.ClienteAtivoException;
import negocio.excecao.cliente.ClienteNaoEncontradoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.funcionario.*;

import java.util.ArrayList;

/**
 * Classe que se comunica com a GUI - FachadaGerente
 */
public class FachadaGerente {

    private static FachadaGerente fachada;

    private FachadaFuncionario fachadaGerente;

    /**
     * Construtor FachadaGerente
     */
    private FachadaGerente(){

        this.fachadaGerente = FachadaFuncionario.getInstance();
    }

    /**
     * Padr?o de projeto singleton
     * M?todo que cria ou retorna uma inst?ncia da FachadaGerente
     * @return FachadaGerente
     */
    public static FachadaGerente getInstance(){

        if(fachada == null){
            return fachada = new FachadaGerente();

        }else{
            return fachada;
        }
    }

    public FachadaFuncionario getFachadaGerente(){
        return this.fachadaGerente;
    }


    /**
     * M?todo que busca um funcion?rio pelo seu CPF
     * @param cpf
     * @return retorna o funcion?rio buscado
     * @throws FuncionarioNaoCadastradoException
     * @throws FuncionarioInativoException
     */
    public Funcionario buscarPorCpf(String cpf) throws FuncionarioNaoCadastradoException,
            FuncionarioInativoException {

        return this.fachadaGerente.getNegocioFuncionario().buscarPorCpf(cpf);
    }

    /**Metodo que retorna as vendas, para fazer o historico de vendas
     * necessita apenas formatar as vendas pra printar na tela
     *
     * @author Erik Jhonatta
     * @return retorna um arraylist
     */
    public ArrayList<Venda> historicoVendas(){
        return this.fachadaGerente.getRepositorioVenda().returnAllVendas();
    }


    /**
     * M?todo para adicionar um funcion?rio
     * @param nome
     * @param cpf
     * @param telefonePrincipal
     * @param telefoneAlternativo
     * @param email
     * @param senha
     * @param salario
     * @param cargoGerente
     * @throws ContatoInvalidoException
     * @throws DadosInvalidosException
     * @throws GerenteJaCadastradoException
     * @throws FuncionarioJaCadastradoException
     */
    public void adicionarFuncionario(String nome, String cpf, String telefonePrincipal, String telefoneAlternativo, String email,
                                     String senha, double salario, boolean cargoGerente) throws ContatoInvalidoException,
            DadosInvalidosException, GerenteJaCadastradoException, FuncionarioJaCadastradoException {

        Contato contato = new Contato(telefonePrincipal, telefoneAlternativo, email);

        Cliente funcionario = new Funcionario(nome, cpf, contato, senha, salario, cargoGerente);

        this.fachadaGerente.getNegocioFuncionario().adicionarFuncionario(funcionario);

    }

    /**
     * M?todo para listar todos os funcion?rio com um determinado nome
     * @param nome
     * @return retorna um ArrayList com todos os funcion?rios encontrados
     * @throws FuncionarioNaoEncontradoException
     */
    public ArrayList<Cliente> listarPorNomeFuncionario(String nome) throws FuncionarioNaoEncontradoException {
        return this.getFachadaGerente().getNegocioFuncionario().listarPorNomeFuncionario(nome);
    }

    public ArrayList<Cliente> listarTodosOsFuncionarios(){
        return this.getFachadaGerente().getNegocioFuncionario().listarTodosOsFuncionarios();
    }

    /**
     * M?todo para alterar um funcion?rio
     * @param nome
     * @param cpf
     * @param telefonePrincipal
     * @param telefoneAlternativo
     * @param email
     * @param senha
     * @param salario
     * @param cargo
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     * @throws GerenteJaCadastradoException
     * @throws FuncionarioNaoCadastradoException
     */
    public void alterarFuncionario(String nome, String cpf, String telefonePrincipal, String telefoneAlternativo, String email,
                                   String senha, double salario, boolean cargo) throws DadosInvalidosException,
            ContatoInvalidoException, GerenteJaCadastradoException, FuncionarioNaoCadastradoException {

        Contato contato = new Contato(telefonePrincipal, telefoneAlternativo, email);
        Funcionario funcionario = new Funcionario(nome, cpf, contato, senha, salario, cargo);
        this.fachadaGerente.getNegocioFuncionario().alterarFuncionario(funcionario);
    }

    /**
     * M?todo para desabilitar um funcion?rio
     * @param cpf
     * @throws FuncionarioNaoCadastradoException
     * @throws ClienteAtivoException
     * @throws ClienteNaoEncontradoException
     */
    public void desabilitarFuncionario(String cpf) throws FuncionarioNaoCadastradoException,
            ClienteAtivoException, ClienteNaoEncontradoException {


        Cliente cliente = this.getFachadaGerente().buscarPorCpf(cpf);
        Funcionario funcionario = (Funcionario) cliente;

        this.fachadaGerente.getNegocioFuncionario().desabilitarFuncionario(funcionario, this.fachadaGerente.getRepositorioVenda());
    }

    /**
     * M?todo para habilitar um funcion?rio
     * @param cpf
     * @throws FuncionarioNaoCadastradoException
     * @throws ClienteNaoEncontradoException
     */
    public void habilitarFuncionario(String cpf) throws FuncionarioNaoCadastradoException, ClienteNaoEncontradoException {

        Cliente cliente = this.getFachadaGerente().buscarPorCpf(cpf);
        Funcionario funcionario = (Funcionario) cliente;

        this.fachadaGerente.getNegocioFuncionario().habilitarFuncionario(funcionario);
    }

}
