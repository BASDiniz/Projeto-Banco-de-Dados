package repositorio;


import java.io.*;
import java.util.ArrayList;
import interfaces.IRepositorioCliente;
import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;

/**
 * Esta classe manipula? e armazena objetos do tipo cliente.
 * @author �verton Vieira
 * @version 2.00
 */
public class RepositorioCliente implements IRepositorioCliente, Serializable{

    private static RepositorioCliente repCliente;

    private ArrayList<Cliente> listaClientes;
    private ArrayList<Cliente> clientesFieis;

    /**
     * Construtor RepositorioCliente
     */
    private RepositorioCliente() {
        this.listaClientes = new ArrayList<Cliente>();
        this.clientesFieis = new ArrayList<Cliente>();
    }

    public static RepositorioCliente getInstance(){
        if(repCliente == null){
            return repCliente = new RepositorioCliente();
        }else{
            return repCliente;
        }
    }

    @Override
    public int procurarCliente(Cliente cliente) {

        for (int i = 0; i < this.listaClientes.size(); i++) {

            if(this.listaClientes.get(i).equals(cliente)) {

                if(cliente.getAtivo() == true){
                    return i;
                }
            }
        }
        return -1;
    }


    @Override
    public void adicionarCliente(Cliente cliente) {
        this.listaClientes.add(cliente);
    }
    
    @Override
    public void adicionarClienteFiel(Cliente cliente) {
        this.clientesFieis.add(cliente);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        for(int i = 0; i < this.listaClientes.size(); i++) {
            if(this.listaClientes.get(i).getCpf().equals(cpf)) {
                return this.listaClientes.get(i);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cliente> listarPorNomeCliente(String nome) {

        ArrayList<Cliente> clientesEncontrados = new ArrayList<Cliente>();

        for(int i = 0; i < this.listaClientes.size(); i++) {

            if(this.listaClientes.get(i).getNome().toLowerCase().startsWith(nome.toLowerCase())) {
                clientesEncontrados.add(this.listaClientes.get(i));
            }
        }
        return clientesEncontrados;
    }

    @Override
    public ArrayList<Cliente> listarPorNomeFuncionario(String nome){

        ArrayList<Cliente> funcionariosEncontrados = new ArrayList<Cliente>();

        for (int i = 0; i < this.listaClientes.size(); i++) {
            if (this.listaClientes.get(i) instanceof Funcionario) {
                if (this.listaClientes.get(i).getNome().toLowerCase().startsWith(nome.toLowerCase())) {
                    funcionariosEncontrados.add(this.listaClientes.get(i));
                }
            }
        }
        return funcionariosEncontrados;
    }

    @Override
    public ArrayList<Cliente> listarTodosOsFuncionarios(){
        ArrayList<Cliente> funcionarios = new ArrayList<Cliente>();

        for(int i = 0; i < this.listaClientes.size(); i++){
            if(this.listaClientes.get(i) instanceof Funcionario){
                funcionarios.add(this.listaClientes.get(i));

            }
        }
        return funcionarios;
    }
    
    @Override
    public ArrayList<Cliente> listarClientesFieis() {
        return this.clientesFieis;
    }

    @Override
    public void alterarCliente(Cliente Cliente, int indiceCliente) {
        this.listaClientes.set(indiceCliente, Cliente);
    }


    @Override
    public void desabilitarCliente(int indiceCliente) {
        this.listaClientes.get(indiceCliente).setAtivo(false);
    }

    @Override
    public void habilitarCliente(int indiceCliente) {
        this.listaClientes.get(indiceCliente).setAtivo(true);
    }

    @Override
    public boolean verificarGerenteExistente(){
        boolean cargoGerente = false;
        for(int i = 0; i < this.listaClientes.size(); i++){
            if(this.listaClientes.get(i) instanceof Funcionario) {
                Funcionario funcionario = (Funcionario) this.listaClientes.get(i);
                if (funcionario.getCargoGerente() == true) {
                    cargoGerente = true;
                    break;
                }
            }
        }
        return cargoGerente;
    }

    // Metodo auxiliar apenas para testes
    public void imprimirListaClientes() {
        for(int i = 0; i < this.listaClientes.size(); i++) {
            System.out.println(this.listaClientes.get(i) + "\n");
        }
    }

    @Override
    public void salvarDados() {
        try{
            FileOutputStream file = new FileOutputStream("listaClientes.dat");
            ObjectOutputStream os = new ObjectOutputStream(file);
            os.writeObject(this.listaClientes);
            os.close();
        }catch(IOException ioException){

        }

    }

    @Override
    public void lerDados(){
        try{
            FileInputStream file = new FileInputStream("listaClientes.dat");
            ObjectInputStream is = new ObjectInputStream(file);
            ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) is.readObject();
            this.listaClientes = listaClientes;
            is.close();

        }
        catch (FileNotFoundException fileNotFound) {
        }
        catch (IOException ioException) {
        }
        catch (ClassNotFoundException classNotFound) {
        }

    }
    
    @Override
    public void salvarDadosFieis() {
        try{
            FileOutputStream file = new FileOutputStream("listaClientesFieis.dat");
            ObjectOutputStream os = new ObjectOutputStream(file);
            os.writeObject(this.clientesFieis);
            os.close();
        }catch(IOException ioException){

        }

    }
    
    @Override
    public void lerDadosFieis(){
        try{
            FileInputStream file = new FileInputStream("listaClientesFieis.dat");
            ObjectInputStream is = new ObjectInputStream(file);
            ArrayList<Cliente> clientesFieis = (ArrayList<Cliente>) is.readObject();
            this.clientesFieis = clientesFieis;
            is.close();
        }
        catch (FileNotFoundException fileNotFound) {
        }
        catch (IOException ioException) {
        }
        catch (ClassNotFoundException classNotFound) {
        }

    }

}


