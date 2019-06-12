package negocio;

import interfaces.IRepositorioCliente;
import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.excecao.cliente.ClienteAtivoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.funcionario.*;
import repositorio.RepositorioVenda;

import java.util.ArrayList;

/**
 * Classe de neg�cio Funcionario
 */
public class NegocioFuncionario {

    public static Funcionario funcionarioLogado;
    private IRepositorioCliente repositorioCliente;

    public NegocioFuncionario(IRepositorioCliente repositorioCliente){

        this.repositorioCliente = repositorioCliente;

    }

    /**
     * M�todo para adicionar um funcion�rio
     * @param funcionario
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     * @throws FuncionarioJaCadastradoException
     * @throws GerenteJaCadastradoException
     */
    public void adicionarFuncionario(Cliente funcionario) throws DadosInvalidosException,
            ContatoInvalidoException, FuncionarioJaCadastradoException,
            GerenteJaCadastradoException{

        if(funcionario instanceof Funcionario){
            Funcionario funcionario1 = (Funcionario) funcionario;
            if (this.repositorioCliente.procurarFuncionario(funcionario1) == -1 && funcionario1.verificarDados()) {
                if (funcionario1.getCargoGerente() == false) { // se nao for gerente
                    this.repositorioCliente.adicionarFuncionario(funcionario1);
                }
                else if(!repositorioCliente.verificarGerenteExistente()){
                    this.repositorioCliente.adicionarFuncionario(funcionario1);
                }
                else{
                    throw new GerenteJaCadastradoException();
                }
            }else{
                throw new FuncionarioJaCadastradoException();
            }
        }
    }

    /**
     * M�todo para listar todos os funcion�rios com um determinado nome
     * @param nome
     * @return retorna ArrayList com todos os funcionario encontrados
     * @throws FuncionarioNaoEncontradoException
     */
    public ArrayList<Cliente> listarPorNomeFuncionario(String nome) throws FuncionarioNaoEncontradoException {

        ArrayList<Cliente> clientesEncontrados = this.repositorioCliente.listarPorNomeFuncionario(nome);
        if(clientesEncontrados.size() > 0) {
            return clientesEncontrados;
        }else{
            throw new FuncionarioNaoEncontradoException();
        }
    }

    public ArrayList<Cliente> listarTodosOsFuncionarios(){
        ArrayList<Cliente> todosFuncs = this.repositorioCliente.listarTodosOsFuncionarios();
        if(todosFuncs.size() > 0){
            return todosFuncs;
        }
        return null;
    }

    /**
     * M�todo para alterar um funcion�rio
     * @param funcionario
     * @throws FuncionarioNaoCadastradoException
     * @throws GerenteJaCadastradoException
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     */
    public void alterarFuncionario(Funcionario funcionario)throws FuncionarioNaoCadastradoException,
            GerenteJaCadastradoException, DadosInvalidosException, ContatoInvalidoException{

        int indiceIndividuo = this.repositorioCliente.procurarCliente(funcionario);

        if(indiceIndividuo != -1 && funcionario.verificarDados() == true){
            if(funcionario.getCargoGerente() != true){
                this.repositorioCliente.alterarCliente(funcionario, indiceIndividuo);
        }
            else if(repositorioCliente.verificarGerenteExistente() == false){
                this.repositorioCliente.alterarCliente(funcionario, indiceIndividuo);
            }
            else{
                throw new GerenteJaCadastradoException();
            }
        }
        else{
            throw new FuncionarioNaoCadastradoException();
        }

    }

    /**
     * M�todo para desabilitar um funcion�rio
     * @param cliente
     * @param repositorioVenda
     * @throws FuncionarioNaoCadastradoException
     * @throws ClienteAtivoException
     */
    public void desabilitarFuncionario(Cliente cliente, RepositorioVenda repositorioVenda) throws FuncionarioNaoCadastradoException,
            ClienteAtivoException {

        int indiceCliente = this.repositorioCliente.procurarCliente(cliente);

        if(indiceCliente != -1) {
            if(!repositorioVenda.buscarClientesComPendencia().contains(cliente)) {
                this.repositorioCliente.desabilitarCliente(indiceCliente);
            }
            else {
                throw new ClienteAtivoException();
            }
        }else{
            throw new FuncionarioNaoCadastradoException();
        }
    }

    /**
     * M�todo para habilitar um funcion�rio
     * @param cliente
     * @throws FuncionarioNaoCadastradoException
     */
    public void habilitarFuncionario(Cliente cliente) throws FuncionarioNaoCadastradoException {

        int indiceCliente = this.repositorioCliente.procurarCliente(cliente);

        if(indiceCliente != -1) {

            this.repositorioCliente.habilitarCliente(indiceCliente);
        }else{
            throw new FuncionarioNaoCadastradoException();
        }
    }


    /**
     * M�todo para buscar um funcion�rio pelo seu CPF
     * @param cpf
     * @return retorna o funcion�rio encontrado
     * @throws FuncionarioNaoCadastradoException
     * @throws FuncionarioInativoException
     */
    public Funcionario buscarPorCpf(String cpf) throws FuncionarioNaoCadastradoException,
            FuncionarioInativoException {

        Cliente cliente = this.repositorioCliente.buscarPorCpf(cpf);
        Funcionario funcionario = null;

        if(cliente instanceof Funcionario){
            funcionario = (Funcionario) cliente;
        }
        if (funcionario == null) {
            throw new FuncionarioNaoCadastradoException();
        }
        else if(!funcionario.getAtivo()){
            throw new FuncionarioInativoException();
        }
        else {
            return funcionario;

        }
    }

    /**
     * M�todo para efetuar o login no sistema
     * @param cpf
     * @param senha
     * @return returna o funcion�rio logado
     * @throws FuncionarioNaoCadastradoException
     * @throws SenhaIncorretaException
     * @throws FuncionarioInativoException
     */
    public Funcionario logar(String cpf, String senha) throws FuncionarioNaoCadastradoException,
            SenhaIncorretaException, FuncionarioInativoException {
        Funcionario funcionario = buscarPorCpfFunc(cpf);

        if(funcionario.getSenha().equals(senha)) {
            funcionarioLogado = funcionario;
            return funcionario;
        }else{
            throw new SenhaIncorretaException();
        }
    }

    public Funcionario buscarPorCpfFunc(String cpf) throws FuncionarioNaoCadastradoException,
            FuncionarioInativoException {

        Cliente cliente = this.repositorioCliente.buscarPorCpfFunc(cpf);
        Funcionario funcionario = null;

        if(cliente instanceof Funcionario){
            funcionario = (Funcionario) cliente;
        }
        if (funcionario == null) {
            throw new FuncionarioNaoCadastradoException();
        }
        else if(!funcionario.getAtivo()){
            throw new FuncionarioInativoException();
        }
        else {
            return funcionario;

        }
    }
}
