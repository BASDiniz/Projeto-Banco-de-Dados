package negocio;

import interfaces.IRepositorioCliente;
import negocio.entidade.Cliente;
import negocio.excecao.cliente.ClienteJaCadastradoException;
import negocio.excecao.cliente.ClienteNaoEncontradoException;
import negocio.excecao.cliente.ClienteAtivoException;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import repositorio.RepositorioVenda;

import java.util.ArrayList;

/**
 * Classe de Negócio Cliente.
 * @author Éverton Vieira.
 */
public class NegocioCliente {

    private IRepositorioCliente repositorioCliente;

    /**
     * Construtor NegocioCliente
     * @param repositorioCliente
     */
    public NegocioCliente(IRepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    /**
     * Método que adiciona cliente.
     * @param cliente
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     * @throws ClienteJaCadastradoException
     */
    public void adicionarCliente(Cliente cliente) throws DadosInvalidosException, ContatoInvalidoException, ClienteJaCadastradoException {

        if(this.repositorioCliente.procurarCliente(cliente) == -1 && cliente.verificarDados() == true) {
            this.repositorioCliente.adicionarCliente(cliente);
        }

        else {
            throw new ClienteJaCadastradoException();
        }
    }

    /**
     * Método que busca um cliente pele seu CPF.
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
     * Método que lista todos os clientes com determinado nome.
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

    /**
     * Método que altera um cliente
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
     * Método que desabilita um cliente (se não estiver com pendências)
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
     * Método que habilita um cliente
     * @param cliente
     */
    public void habilitarCliente(Cliente cliente) {

        int indiceCliente = this.repositorioCliente.procurarCliente(cliente);

        if(indiceCliente != -1) {
            this.repositorioCliente.habilitarCliente(indiceCliente);
        }
    }

    /**
     * Método que salva os dados do RepositorioCliente
     */
    public void salvarDados() {
        this.repositorioCliente.salvarDados();
    }

    /**
     * Método que faz a leitura dos do RepositorioCliente
     */
    public void lerDados() {
        this.repositorioCliente.lerDados();
    }

}