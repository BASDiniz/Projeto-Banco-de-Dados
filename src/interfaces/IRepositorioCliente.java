package interfaces;

import negocio.entidade.Cliente;
import java.util.ArrayList;

public interface IRepositorioCliente {
    /**
     * Esse m�todo verifica se o cliente existe no reposit�rio.
     * @param cliente
     * @return a posi��o(�ndice) do cliente no reposit�rio?.
     */
    int procurarCliente(Cliente cliente);

    /**
     * M�todo que adiciona um cliente na lista de clientes.
     * @param cliente
     */
    void adicionarCliente(Cliente cliente);

    /**
     * M�todo que altera um cliente antigo passando um novo como argumento.
     * @param cliente
     * @param indiceCliente
     */
    void alterarCliente(Cliente cliente, int indiceCliente);

    /**
     * M�todo que desabilita um cliente da lista de clientes.
     * @param indiceCliente
     */
    void desabilitarCliente(int indiceCliente);

    /**
     * M�todo que habilita um cliente da lista de clientes.
     * @param indiceCliente
     */
    void habilitarCliente(int indiceCliente);

    boolean verificarGerenteExistente();

    Cliente buscarPorCpf(String cpf);

    /**
     * Lista todos os clientes que come�am com o nome passado como par�metro
     * @param nome
     * @return retorna um ArrayList de cliente
     */
    ArrayList<Cliente> listarPorNomeCliente(String nome);

    ArrayList<Cliente> listarPorNomeFuncionario(String nome);

    void salvarDados();

    void lerDados();
    
    void salvarDadosFieis();
    
    void lerDadosFieis();

    ArrayList<Cliente> listarTodosOsFuncionarios();
    
    void adicionarClienteFiel(Cliente cliente);
    
    ArrayList<Cliente> listarClientesFieis();
    
}
