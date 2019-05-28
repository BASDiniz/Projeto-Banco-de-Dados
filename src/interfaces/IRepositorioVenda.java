package interfaces;

import negocio.entidade.Cliente;
import negocio.entidade.Funcionario;
import negocio.entidade.Venda;
import negocio.entidade.produto.Produto;

import java.util.ArrayList;

public interface IRepositorioVenda {


    /**
     * Método que adiciona uma venda
     * @param venda
     */
    void adicionarVenda(Venda venda);

    /**
     * Método para ver o lucro mensal
     * @param mes
     * @return retorna o lucro mensal
     */
    double verLucroMensal(int mes);

    /**
     * Método para ver o lucro anual
     * @param ano
     * @return retorna o lucro anual
     */
    double verLucroAnual(int ano);

    /**
     * Método que determina o funcion?rio do m?s
     * @param mes
     * @return retorna o funcion?rio do m?s
     */
    Funcionario determinarFuncionarioDoMes(int mes);

    /**
     * Método que determina o cliente fiel
     * @param mes
     * @return retorna o cliente fiel
     */
    Cliente determinarClienteFiel(int mes);

    /**
     * Método que busca os cliente que possuem pend?ncias
     * @return retorna um ArrayList com todos os clientes com pend?ncia
     */
    ArrayList<Cliente> buscarClientesComPendencia();

    /**
     * Método que busca os produtos mais vendidos em um determinado m?s
     * @param mes
     * @return retorna um ArrayList com os produtos mais vendidos
     */
    ArrayList<Produto> buscarProdutosMaisVendidos(int mes);

    /**
     * Método para salvar os dados da classe repositorioVenda
     */
    void salvarDados();

    /**
     * Método que faz a leitura dos dados da classe repositorioVenda
     */
    void lerDados();

}
