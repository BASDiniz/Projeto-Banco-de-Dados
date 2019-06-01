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
 * Classe de negócio Funcionario
 */
public class NegocioFuncionario {

    public static Funcionario funcionarioLogado;
    private IRepositorioCliente repositorioCliente;

    public NegocioFuncionario(IRepositorioCliente repositorioCliente){

        this.repositorioCliente = repositorioCliente;

    }

    /**
     * Método para adicionar um funcionário
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
            if (this.repositorioCliente.procurarCliente(funcionario1) == -1 && funcionario1.verificarDados() == true) {
                if (funcionario1.getCargoGerente() != true) {
                    this.repositorioCliente.adicionarCliente(funcionario1);
                }
                else if(repositorioCliente.verificarGerenteExistente() == false){
                    this.repositorioCliente.adicionarCliente(funcionario1);
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
     * Método para listar todos os funcionários com um determinado nome
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

    /**
     * Método para alterar um funcionário
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
     * Método para desabilitar um funcionário
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
     * Método para habilitar um funcionário
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
     * Método para buscar um funcionário pelo seu CPF
     * @param cpf
     * @return retorna o funcionário encontrado
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
     * Método para efetuar o login no sistema
     * @param cpf
     * @param senha
     * @return returna o funcionário logado
     * @throws FuncionarioNaoCadastradoException
     * @throws SenhaIncorretaException
     * @throws FuncionarioInativoException
     */
    public Funcionario logar(String cpf, String senha) throws FuncionarioNaoCadastradoException,
            SenhaIncorretaException, FuncionarioInativoException {
        Funcionario funcionario = buscarPorCpf(cpf);

        if(funcionario.getSenha().equals(senha)) {
            funcionarioLogado = funcionario;
            return funcionario;
        }else{
            throw new SenhaIncorretaException();
        }
    }

    /**
     * Método para salvar os dados do repositorioCliente
     */
    public void salvarDados(){
        this.repositorioCliente.salvarDados();
    }

    /**
     * Método para fazer a leitura dos dados do repositorioCliente
     */
    public void lerDados(){
        this.repositorioCliente.lerDados();
    }
}
