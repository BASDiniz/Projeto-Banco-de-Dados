package negocio.entidade;

import java.io.Serializable;
import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.*;


import java.util.ArrayList;
import java.util.Calendar;
import negocio.entidade.produto.Produto;
import negocio.excecao.venda.CarrinhoVazioException;
import negocio.excecao.venda.ClienteInativoException;
import negocio.excecao.venda.ClienteNegadoException;

/**
 * Classe que representa uma venda.
 * @author Éverton Vieira
 * @author Bruno Diniz
 * @version 2.00
 */
public class Venda implements Serializable {

    private static int geradorDeCodigo = 1;
    private int codigo;
    private Funcionario funcionario;
    private Cliente cliente;
    private ArrayList<Produto> carrinhoProdutos;
    private String descricao;
    private boolean pagamentoParcelado;
    private double valorTotal;
    private Calendar dataDeVenda;

    /**
     * Construtor Venda com descrição
     * @param funcionario
     * @param cliente
     * @param carrinhoProdutos
     * @param descricao
     * @param pagamentoParcelado
     */
    public Venda(Funcionario funcionario, Cliente cliente, ArrayList<Produto> carrinhoProdutos, String descricao, boolean pagamentoParcelado) {
        this.codigo = geradorDeCodigo;
        geradorDeCodigo++;

        this.funcionario = funcionario;
        this.cliente = cliente;
        this.carrinhoProdutos = carrinhoProdutos;
        this.descricao = descricao;
        this.pagamentoParcelado = pagamentoParcelado;
        this.valorTotal = calcularValorTotal();
        this.dataDeVenda = Calendar.getInstance();
    }

    /**
     * Construtor Venda sem descrição
     * @param funcionario
     * @param cliente
     * @param carrinhoProdutos
     * @param pagamentoParcelado
     */
    public Venda(Funcionario funcionario, Cliente cliente, ArrayList<Produto> carrinhoProdutos, boolean pagamentoParcelado) {
        this.codigo = geradorDeCodigo;
        geradorDeCodigo++;

        this.funcionario = funcionario;
        this.cliente = cliente;
        this.carrinhoProdutos = carrinhoProdutos;
        this.pagamentoParcelado = pagamentoParcelado;
        this.valorTotal = calcularValorTotal();
        this.dataDeVenda = Calendar.getInstance();
    }

    // Getters e Setters

    public int getCodigo() {
        return this.codigo;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public ArrayList<Produto> getCarrinhoProdutos() {
        return this.carrinhoProdutos;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean getPagamenteParcelado() {
        return this.pagamentoParcelado;
    }

    public void setPagamentoParcelado(boolean pagamentoParcelado) {
        this.pagamentoParcelado = pagamentoParcelado;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public Calendar getDataDeVenda() {
        return this.dataDeVenda;
    }

    // Metodos

    /**
     * Método que concede uma desconto de 10% em uma venda
     */
    public void descontarVenda() {
        this.valorTotal =  this.valorTotal - (this.valorTotal * 0.10);
    }


    /**
     * Esse método verifica se todos os atributos(dados) da venda são válidos
     * @return retorna "true" se todos os atributos(dados) forem válidos. Caso contrário retorna "false".
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     * @throws ClienteNegadoException
     * @throws CarrinhoVazioException
     * @throws ClienteInativoException
     */
    public boolean verificarVenda() throws DadosInvalidosException, ContatoInvalidoException,
            ClienteNegadoException, CarrinhoVazioException, ClienteInativoException {

        if(this.carrinhoProdutos.size() == 0){
            throw new CarrinhoVazioException();
        }
        else if(cliente.equals(funcionario)){
            throw new ClienteNegadoException();
        }
        else if(!cliente.getAtivo()){
            throw new ClienteInativoException();
        }

        this.cliente.verificarDados();
        this.funcionario.verificarDados();

        return true;
    }

    //Método que calcular que calcula o valor total de uma venda

    private double calcularValorTotal() {

        int soma = 0;

        for(int i = 0; i < this.carrinhoProdutos.size(); i++) {
            soma += carrinhoProdutos.get(i).getValorVenda();
        }
        return soma;
    }

    /**
     * Método para imprimir o carrinhoProdutos (Apenas para testes)
     * @return
     */
    public String imprimirCarrinhoProdutos() {

        ArrayList<Produto> produtosDaVenda = new ArrayList();
        String produtos = "";

        for(int i = 0 ; i < this.carrinhoProdutos.size(); i++) {

            if(!produtosDaVenda.contains(carrinhoProdutos.get(i))) {
                produtosDaVenda.add(carrinhoProdutos.get(i));
            }
        }

        for(int i = 0; i < produtosDaVenda.size(); i++) {

            int quantidadeNoCarrinho = 0;

            for(int k = 0; k < carrinhoProdutos.size(); k++) {

                if(produtosDaVenda.get(i) == carrinhoProdutos.get(k)) {
                    quantidadeNoCarrinho ++;
                }
            }
            produtos += produtosDaVenda.get(i).getDescricaoDoProduto()+ ", qtd = " + quantidadeNoCarrinho + " | ";
        }
        return produtos;
    }

    /**
     * Sobrescrita do método equals
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Venda) {
            Venda venda2 = (Venda) obj;
            if(this.codigo == venda2.getCodigo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sobrescrita do método toString.
     * @return retorna o objeto Venda como uma string
     */
    @Override
    public String toString() {
        return "Codigo da venda: " + this.codigo + "\nFuncionario\n" + this.funcionario + "\nCliente\n" + this.cliente +
                "\nCarrinho de Produtos: " + imprimirCarrinhoProdutos()+ "\nDescricao: " + this.descricao +
                "\nPagamento Pendente: " + this.pagamentoParcelado + "\nValor Total: " + this.valorTotal
                + "\nData de venda: " + this.dataDeVenda.get(Calendar.DAY_OF_MONTH) + "/" +
                (this.dataDeVenda.get(Calendar.MONTH) + 1) + "/" + this.dataDeVenda.get(Calendar.YEAR) + "\n";
    }

}

