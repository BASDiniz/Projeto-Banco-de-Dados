package negocio.entidade.produto;

import java.io.Serializable;
import static negocio.entidade.produto.ConstantesProduto.*;
import negocio.excecao.produto.*;

/**
 * Classe que representa um produto.
 * @author Éverton Vieira
 * @version 2.00
 */
public class Produto implements Serializable {

    private String codigo, tipoDeRoupa, descricaoDoProduto, faixaEtaria, genero, cor, tamanho;
    private String categoria;
    private int quantidade;
    private double valorVenda;
    private boolean ativo;

    /**
     * Construtor Produto
     * @param codigo
     * @param tipoDeRoupa
     * @param descricaoDoProduto
     * @param faixaEtaria
     * @param genero
     * @param cor
     * @param tamanho
     * @param categoria
     * @param quantidade
     * @param valorVenda
     */
    public Produto(String codigo, String tipoDeRoupa, String descricaoDoProduto, String faixaEtaria, String genero,
                   String cor, String tamanho, String categoria, int quantidade, double valorVenda) {
        this.codigo = codigo;
        this.tipoDeRoupa = tipoDeRoupa;
        this.descricaoDoProduto = this.tipoDeRoupa + " " + descricaoDoProduto;
        this.faixaEtaria = faixaEtaria;
        this.genero = genero;
        this.cor = cor;
        this.tamanho = tamanho;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
        this.ativo = true;
    }

    // Getters e Setters

    public String getCodigo() {
        return this.codigo;
    }

    public String getTipoDeRoupa() {
        return this.tipoDeRoupa;
    }

    public void setTipoDeRoupa(String novoTipoDeRoupa) {
        this.tipoDeRoupa = novoTipoDeRoupa;
    }

    public String getDescricaoDoProduto() {
        return this.descricaoDoProduto;
    }

    public String pegarDescricaoPura() {
        String inicioDescricao = "";

        for(int i = 0; i < this.descricaoDoProduto.length(); i++) {
            inicioDescricao += descricaoDoProduto.charAt(i);

            if(OPCOES_TIPO_DE_ROUPA.contains(inicioDescricao)) {
                return this.descricaoDoProduto.substring(i+2, this.descricaoDoProduto.length());
            }
        }
        return inicioDescricao;
    }

    public void setDescricaoDoProduto(String novaDescricaoDoProduto) {
        this.descricaoDoProduto = novaDescricaoDoProduto;

    }

    public String getFaixaEtaria() {
        return this.faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getGenero() {
        return this.genero;
    }

    public String getCor() {
        return this.cor;
    }

    public String getTamanho() {
        return this.tamanho;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int novaQuantidade) {
        this.quantidade = novaQuantidade;

    }

    public double getValorVenda() {
        return this.valorVenda;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // Metodos(verificacao)

    private boolean verificarCodigoApenasNumero() {

        for(int i = 0; i < this.codigo.length(); i++) {

            if(!Character.isDigit(this.codigo.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean verificarCodigoVazio() {

        if(!this.codigo.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean verificarTipo() {

        if(OPCOES_TIPO_DE_ROUPA.contains(this.tipoDeRoupa)) {
            return true;
        }
        return false;
    }

    private boolean verificarDescricaoDoProduto() {

        if(this.descricaoDoProduto.length() >= 15) {
            return true;
        }
        return false;
    }

    private boolean verificarFaixaEtaria() {

        if(OPCOES_FAIXA_ETARIA.contains(this.faixaEtaria)) {
            return true;
        }
        return false;
    }

    private boolean verificarGenero() {

        if(OPCOES_GENERO.contains(this.genero)) {
            return true;
        }
        return false;
    }

    private boolean verificarCorVazia() {

        if(!this.cor.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean verificarCorApenasLetra() {

        for(int i = 0; i < this.cor.length(); i++) {

            if(Character.isDigit(this.cor.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean verificarTamanho() {

        if(OPCOES_TAMANHO.contains(this.tamanho)) {
            return true;
        }
        return false;
    }

    private boolean verificarCategoria() {

        if(OPCOES_CATEGORIA.contains(this.categoria)) {
            return true;
        }
        return false;
    }

    private boolean verificarQuantidade() {

        if(this.quantidade > 0) {
            return true;
        }
        return false;
    }

    private boolean verificarValorVenda() {

        if(this.valorVenda > 0) {
            return true;
        }
        return false;
    }

    /**
     * Esse método verifica se todos os atributos(dados) do produto s?o v?lidos.
     * @return retorna "true" se todos os atributos(dados) forem válidos.
     * Caso contr?rio retorna "false".
     */
    public boolean verificarProduto() throws ProdutoInvalidoException {

        if(verificarCodigoApenasNumero() != true) {
            throw new CodigoProdutoApenasNumeroException();
        }

        if(verificarCodigoVazio() != verificarCodigoApenasNumero()) {
            throw new CodigoVazioExcepiton();
        }

        if(verificarTipo() != true) {
            throw new TipoInvalidoException();
        }

        if(verificarDescricaoDoProduto() != true) {
            throw new DescricaoTamanhoException();
        }

        if(verificarFaixaEtaria() != true) {
            throw new FaixaEtariaInvalidaException();
        }

        if(verificarGenero() != true) {
            throw new GeneroInvalidoException();
        }

        if(verificarCorVazia() != true) {
            throw new CorVaziaException();
        }

        if(verificarCorApenasLetra() != true) {
            throw new CorApenasLetraException();
        }

        if(verificarTamanho() != true) {
            throw new TamanhoInvalidoException();
        }

        if(verificarCategoria() != true) {
            throw new CategoriaInvalidaException();
        }

        if(verificarQuantidade() != true) {
            throw new ProdutoQuantidadeMenorQueZeroException();
        }

        if(verificarValorVenda() != true) {
            throw new ProdutoValorNegativoException();
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Produto) {
            Produto produto2 = (Produto) obj;
            if(this.codigo.equals(produto2.getCodigo()) &&
                    this.genero.equals(produto2.getGenero()) &&
                    this.cor.equals(produto2.getCor()) &&
                    this.tamanho.equals(produto2.getTamanho())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sobrescrita do m?todo toString.
     * @return retorna o objeto Produto como uma string
     */
    @Override
    public String toString() {
        return "\n\nCodigo: " + this.codigo + "\nTipo: " + this.tipoDeRoupa + "\nDescricao: " + this.descricaoDoProduto +
                "\nFaixa Etaria: " + this.faixaEtaria + "\nGenero: " + this.genero +
                "\nCor: " + this.cor + "\nTamanho: " + this.tamanho +
                "\nCategoria: " + this.categoria + "\nValorVenda: " + this.valorVenda +
                "\nQuantidade: " + this.quantidade + "\nValor: " + this.valorVenda +
                "\nAtivo: " + this.ativo;
    }

}