package negocio.entidade;

import negocio.excecao.cliente.*;
import negocio.excecao.cliente.contato.*;

import java.io.Serializable;

/**
 * Classe que representa um cliente.
 * @author �verton Vieira
 * @author Bruno Diniz
 * @version 2.00
 */
public class Cliente implements Serializable {

    private String nome, cpf;
    private Contato contato;
    private boolean fiel, ativo;

    /**
     * Construtor cliente
     * @param nome
     * @param cpf
     * @param contato
     */
    public Cliente(String nome, String cpf, Contato contato) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
        this.fiel = false;
        this.ativo = true;
    }

    // Getters e Setters

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Contato getContato() {
        return this.contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Boolean getFiel() {
        return this.fiel;
    }

    public void setFiel(boolean fiel) {
        this.fiel = fiel;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // Metodos de verificacao

    // M?todo que verifica se nome contem apenas letras.
    private boolean verificarApenasLetrasNome() {

        for (int i = 0; i < nome.length(); i++) {

            if (Character.isDigit(nome.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // M?todo que verifica o tamanho do nome
    private boolean verificarTamanhoNome() {

        if (nome.length() >= 7) {
            return true;
        }
        return false;
    }

    // M?todo auxiliar para verificar digitos verificadores
    private char verificarDigito(int peso) {

        char primeiroDigitoVerificador;
        int soma, resto, numero, tamanho; // tamanho?

        soma = 0;
        tamanho = peso - 1;
        //
        for (int i = 0; i < tamanho; i++) {
        /* Converte o i-esimo caractere do CPF em um numero:
        Por exemplo, transforma o caractere '0' no inteiro 0
        (48 eh a posicao de '0' na tabela ASCII)*/
            numero = (int) (this.cpf.charAt(i) - 48);
            soma = soma + (numero * peso);
            peso = peso - 1;
        }

        resto = 11 - (soma % 11);

        if ((resto == 10) || (resto == 11)) {
            primeiroDigitoVerificador = '0';
        } else {
            primeiroDigitoVerificador = (char) (resto + 48); // Converte no respectivo caractere numerico
        }
        return primeiroDigitoVerificador;
    }

    // Calculo do 1o. digito verificador.
    private char verificarPrimeiroDigito() {
        return verificarDigito(10);
    }

    // Calculo do 2o. digito verificador
    private char verificarSegundoDigito() {
        return verificarDigito(11);
    }

    // M?todo que verifica se o cpf ? v?lido
    private boolean verificarCpfValido() {

        // Verifica se os digitos calculados conferem com os digitos informados.
        if ((verificarPrimeiroDigito() == this.cpf.charAt(9)) && (verificarSegundoDigito() == this.cpf.charAt(10))) {
            return true;
        }
        return false;
    }

    // M?todo que verifica se o cpf cont?m apenas n?mero
    private boolean verificarCpfApenasNumero() {

        for (int i = 0; i < this.cpf.length(); i++) {

            if (!Character.isDigit(this.cpf.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // M?todo que verifica o tamanho do cpf
    private boolean verificarCpfTamanho() {

        if (this.cpf.length() == 11) {
            return true;
        }
        return false;
    }

    /**
     * M?todo que verifica se todos os atributos(dados) do cliente s?o v?lidos
     *
     * @return retorna "true" se todos os atributos(dados) s?o v?lidos.
     * Caso contr�rio, retorna "false"
     */
    public boolean verificarDados() throws DadosInvalidosException, ContatoInvalidoException {

        if (verificarApenasLetrasNome() != true) {
            throw new NomeApenasLetraException();
        }

        if (verificarTamanhoNome() != true) {
            throw new NomeTamanhoException();
        }

        if (verificarCpfApenasNumero() != true) {
            throw new CpfApenasNumeroException();
        }

        if (verificarCpfTamanho() != true) {
            throw new CpfTamanhoException();
        }

        if (verificarCpfValido() != true) {
            throw new CpfInvalidoException(this.cpf);
        }

        this.contato.verificarContato();

        return true;
    }

    // Metodo auxiliar - apenas para imprimir o atributo "cpf" de forma organizada.
    private String imprimirCpf() {
        return (this.cpf.substring(0, 3) + "." + this.cpf.substring(3, 6) + "." +
                this.cpf.substring(6, 9) + "-" + this.cpf.substring(9, 11));
    }

    /**
     * M�todo que sobrescreve o m�todo equals.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Cliente) {
            Cliente cliente2 = (Cliente) obj;
            if (this.cpf.equals(cliente2.getCpf())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sobrescrita do m�todo toString.
     *
     * @return retorna o objeto Pessoa como uma string
     */
    @Override
    public String toString() {
        return "Nome: " + this.nome + "\n" + "CPF: " + imprimirCpf() + "\n" + this.contato + "\nFiel: " + this.fiel +
                "\nAtivo: " + this.ativo;
    }
}