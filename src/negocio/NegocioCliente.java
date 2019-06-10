package negocio;

import dados.DbCliente;
import interfaces.IRepositorioCliente;
import negocio.entidade.Cliente;
import negocio.excecao.cliente.ClienteJaCadastradoException;
import negocio.excecao.cliente.ClienteNaoEncontradoException;
import negocio.excecao.cliente.ClienteAtivoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import repositorio.RepositorioCliente;
import repositorio.RepositorioVenda;

import java.util.ArrayList;

public class NegocioCliente {
    private DbCliente dbCliente;
    private RepositorioCliente repositorioCliente;

    public NegocioCliente(DbCliente dbCliente, RepositorioCliente repositorioCliente) {
        this.dbCliente = dbCliente;
        this.repositorioCliente = repositorioCliente;
    }

    public void adicionarCliente(Cliente cliente) throws DadosInvalidosException, ContatoInvalidoException, ClienteJaCadastradoException {

        if(this.dbCliente.procurarCliente(cliente) == null && cliente.verificarDados() == true) {
            this.dbCliente.adicionarCliente(cliente);
        }
        else {
            throw new ClienteJaCadastradoException();
        }
    }

    /**
     * M�todo que busca um cliente pele seu CPF.
     * @param cpf
     * @return retorna o cliente encontrado.
     * @throws ClienteNaoEncontradoException
     */
    public Cliente buscarPorCpf(String cpf) throws ClienteNaoEncontradoException {

        Cliente clienteEncontrado = this.repositorioCliente.buscarPorCpf(cpf);

        if(clienteEncontrado != null) {
            return clienteEncontrado;
        }
        throw new ClienteNaoEncontradoException();
    }

    /**
     * M�todo que lista todos os clientes com determinado nome.
     * @param nome
     * @return retorna um ArrayList com os clientes encontrados
     * @throws ClienteNaoEncontradoException
     */
    public ArrayList<Cliente> listarPorNomeCliente(String nome) throws ClienteNaoEncontradoException {

        ArrayList<Cliente> clientesEncontrados = this.repositorioCliente.listarPorNomeCliente(nome);

        if(clientesEncontrados.size() > 0) {
            return clientesEncontrados;
        }
        throw new ClienteNaoEncontradoException();
    }
    
    public ArrayList<Cliente> listarClientesFieis() {
        return this.repositorioCliente.listarClientesFieis();
    }

    /**
     * M�todo que altera um cliente
     * @param cliente
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     */
    public void alterarCliente(Cliente cliente) throws DadosInvalidosException, ContatoInvalidoException {

        int indiceCliente = this.repositorioCliente.procurarCliente(cliente);

        if(indiceCliente != -1) {

            if(cliente.verificarDados() == true) {
                this.repositorioCliente.alterarCliente(cliente, indiceCliente);
            }
        }
    }

    /**
     * M�todo que desabilita um cliente (se n�o estiver com pend�ncias)
     * @param cliente
     * @param repositorioVenda
     * @throws ClienteAtivoException
     */
    public void desabilitarCliente(Cliente cliente, RepositorioVenda repositorioVenda) throws ClienteAtivoException {

        int indiceCliente = this.repositorioCliente.procurarCliente(cliente);

        if(indiceCliente != -1) {
            // Se o cliente nao estiver com alguma pendencia
            if(!repositorioVenda.buscarClientesComPendencia().contains(cliente)) {
                this.repositorioCliente.desabilitarCliente(indiceCliente);
            }
            else {
                throw new ClienteAtivoException();
            }
        }
    }

    /**
     * M�todo que habilita um cliente
     * @param cliente
     */
    public void habilitarCliente(Cliente cliente) {

        int indiceCliente = this.repositorioCliente.procurarCliente(cliente);

        if(indiceCliente != -1) {
            this.repositorioCliente.habilitarCliente(indiceCliente);
        }
    }

    /**
     * M�todo que salva os dados do RepositorioCliente
     */
    public void salvarDados() {
        this.repositorioCliente.salvarDados();
    }

    /**
     * M�todo que faz a leitura dos do RepositorioCliente
     */
    public void lerDados() {
        this.repositorioCliente.lerDados();
    }
    
    public void salvarDadosFieis() {
        this.repositorioCliente.salvarDadosFieis();
    }

    public void lerDadosFieis() {
        this.repositorioCliente.lerDadosFieis();
    }
}