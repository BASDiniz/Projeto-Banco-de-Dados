package negocio.entidade;

import negocio.excecao.cliente.DadosInvalidosException;
import negocio.excecao.cliente.contato.ContatoInvalidoException;
import negocio.excecao.cliente.funcionario.SalarioNegativoException;
import negocio.excecao.cliente.funcionario.SenhaTamanhoException;

import java.io.Serializable;

/**
 * Classe que representa um funcion?rio.
 * @author Bruno Diniz
 * @version 2
 */
public class Funcionario extends Cliente implements Serializable{
    private String senha;
    private double salario;
    private boolean cargoGerente;
    private boolean funcDoMes;
    private double salarioComBonificacao;

    /**
     * Construtor Funcionario
     * @param nome
     * @param cpf
     * @param contato
     * @param senha
     * @param salario
     * @param cargoGerente
     */
    public Funcionario(String nome, String cpf, Contato contato, String senha, double salario,
                       boolean cargoGerente){
        super(nome, cpf, contato);
        this.senha = senha;
        this.salario = salario;
        this.cargoGerente = cargoGerente;
        this.funcDoMes = false;
        this.salarioComBonificacao = 0.0;
    }

    //M?todos Getters e Setters

    public boolean getFuncDoMes(){
        return this.funcDoMes;
    }

    public void setFuncDoMes(boolean funcDoMes){
        this.funcDoMes = funcDoMes;
    }

    public double getSalarioComBonificacao(){
        return this.salarioComBonificacao;
    }

    public void setSalarioComBonificacao(double salarioComBonificacao){
        this.salarioComBonificacao = salarioComBonificacao;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSalario(){
        return this.salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean getCargoGerente(){
        return this.cargoGerente;
    }

    public void setCargoGerente(boolean cargoGerente) {
        this.cargoGerente = cargoGerente;
    }

    //M?todo que verifica se a senha ? menor que 5

    private boolean verificarSenha(){
        if(this.senha.length() < 5){
            return false;
        }
        return true;
    }

    //M?todo que verifica se o s?lario ? um n?mero negativo

    private boolean verificarSalario(){
        if(this.salario < 0){
            return false;
        }

        return true;
    }

    /**
     * M?todo que acrescenta um b?nus de 5% ao sal?rio
     */
    public void bonificar(){
        double bonus = this.salario * 0.05;
        this.salario += bonus;
    }

    /**
     * Sobrescrita do m?todo "verificarDados" da superclasse Cliente
     * M?todo que verifica se todos os atributos s?o v?lidos.
     * @return retorna "true" se os atributos estiverem v?lidos, caso contr?rio, joga uma exce??o.
     * @throws DadosInvalidosException
     * @throws ContatoInvalidoException
     */
    @Override
    public boolean verificarDados() throws DadosInvalidosException, ContatoInvalidoException {

        super.verificarDados();

        if(verificarSenha() != true){
            throw new SenhaTamanhoException();
        }
        else if(verificarSalario() != true){
            throw new SalarioNegativoException();
        }
        return true;
    }

    /**
     * Sobrescrita do m?todo "toString" da superclasse cliente.
     * @return retorna os dados de um objeto do tipo Funcionario.
     */
    @Override
    public String toString(){
        return super.toString() + "\n" + "Senha: " + this.senha + "\nSal?rio: R$" + this.salario
                + "\nCargoGerente: " + this.cargoGerente + "\n";
    }
}
