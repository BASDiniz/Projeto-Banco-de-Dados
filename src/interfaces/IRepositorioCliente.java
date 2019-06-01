package interfaces;

import negocio.entidade.Cliente;
import java.util.ArrayList;

public interface IRepositorioCliente {
    /**
     * Esse método verifica se o cliente existe no repositório.
     * @param cliente
     * @return a posição(índice) do cliente no repositório?.
     */
    int procurarCliente(Cliente cliente);

    /**
     * Método que adiciona um cliente na lista de clientes.
     * @param cliente
     */
    void adicionarCliente(Cliente cliente);

    /**
     * Método que altera um cliente antigo passando um novo como argumento.
     * @param cliente
     * @param indiceCliente
     */
    void alterarCliente(Cliente cliente, int indiceCliente);

    /**
     * Método que desabilita um cliente da lista de clientes.
     * @param indiceCliente
     */
    void desabilitarCliente(int indiceCliente);

    /**
     * Método que habilita um cliente da lista de clientes.
     * @param indiceCliente
     */
    void habilitarCliente(int indiceCliente);

    boolean verificarGerenteExistente();

    Cliente buscarPorCpf(String cpf);

    /**
     * Lista todos os clientes que começam com o nome passado como parâmetro
     * @param nome
     * @return retorna um ArrayList de cliente
     */
    ArrayList<Cliente> listarPorNomeCliente(String nome);

    ArrayList<Cliente> listarPorNomeFuncionario(String nome);

    void salvarDados();

    void lerDados();


}
