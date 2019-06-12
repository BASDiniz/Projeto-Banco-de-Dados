package repositorio;

import connection.ConnectionFactory;
import fachada.FachadaGerente;
import negocio.entidade.produto.Produto;
import interfaces.IRepositorioProduto;
import negocio.excecao.produto.ProdutoInvalidoException;
import negocio.excecao.produto.ProdutoJaCadastradoException;
import negocio.excecao.produto.ProdutoNaoEncontradoException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta classe manipula? e armazena objetos do tipo Produto.
 * @author Éverton Vieira 
 * @version 2.00
 */
public class RepositorioProduto implements IRepositorioProduto, Serializable {
    
    private ArrayList<Produto> listaProdutos;
    
    /**
     * Construtor RepositorioProduto
     */
    public RepositorioProduto() {
        this.listaProdutos = new ArrayList<Produto>();
    }

    //Implementado no banco
    @Override
    public int procurarProduto(Produto produto) {

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement("SELECT * FROM produto WHERE codigo = ? AND genero = ? AND cor = ? AND tamanho = ? ");
            stmt.setString(1, produto.getCodigo());
            stmt.setString(2, produto.getGenero());
            stmt.setString(3, produto.getCor());
            stmt.setString(4, produto.getTamanho());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                if(rs.getInt("ativo") == 1){
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return -1;
    }

    //Implementado no banco
    @Override 
    public void adicionarProduto(Produto produto) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement("INSERT INTO produto VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, produto.getCodigo());
            stmt.setString(2, produto.getTipoDeRoupa());
            stmt.setString(3, produto.getDescricaoDoProduto());
            stmt.setString(4, produto.getFaixaEtaria());
            stmt.setDouble(5, produto.getValorVenda());
            stmt.setString(6, produto.getGenero());
            stmt.setInt(7, produto.getQuantidade());
            stmt.setString(8, produto.getCor());
            stmt.setBoolean(9, produto.getAtivo());
            stmt.setString(10, produto.getCategoria());
            stmt.setString(11, produto.getTamanho());
            stmt.setInt(12, -1);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }

        this.listaProdutos.add(produto);
    }

    //Implementado no banco
    @Override
    public Produto buscarProdutoPorCodigo(String codigo) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conexao.prepareStatement("SELECT * FROM produto WHERE codigo = ?");
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();
            rs.next();

            if(!rs.isBeforeFirst()){
                String codigo1 = rs.getString("codigo");
                String tipoDeRoupa = rs.getString("tipo_de_roupa");
                String descProduto = rs.getString("desc_produto");
                String faixaEtaria = rs.getString("faixa_etaria");
                String genero = rs.getString("genero");
                Integer quantidade = rs.getInt("quantidade");
                String cor = rs.getString("cor");
                Boolean ativo = rs.getBoolean("ativo");
                String categoria = rs.getString("categoria");
                String tamanho = rs.getString("tamanho");
                Double valorVenda = rs.getDouble("valor_venda");
                Produto produtoBanco = new Produto(codigo1, tipoDeRoupa, descProduto, faixaEtaria, genero, cor, tamanho, categoria, quantidade, valorVenda);
                produtoBanco.setAtivo(ativo);

                return produtoBanco;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return null;
    }
    
    @Override
    public ArrayList<Produto> listarProdutosPorTipo(String tipo) { // TipoDeRoupa?
        
        ArrayList<Produto> produtosEncontrados = new ArrayList();
        
        for(int i = 0; i < this.listaProdutos.size(); i++) {
            
            if(this.listaProdutos.get(i).getAtivo() == true) { //////////////////////
            
                if(this.listaProdutos.get(i).getTipoDeRoupa().toLowerCase().equals(tipo.toLowerCase())){
                    produtosEncontrados.add(this.listaProdutos.get(i));
                }
            }
        }
        return produtosEncontrados;
    }
    
    @Override
    public ArrayList<Produto> listarProdutosPorDescricao(String descricao) {
        
        ArrayList<Produto> produtosEncontrados = new ArrayList();
        
        for(int i = 0; i < this.listaProdutos.size(); i++) {
            
            if(this.listaProdutos.get(i).getAtivo() == true) { //////////////////////
                
                if(this.listaProdutos.get(i).getDescricaoDoProduto().toLowerCase().startsWith(descricao.toLowerCase())){
                    produtosEncontrados.add(this.listaProdutos.get(i));
                }
            }
        }
        return produtosEncontrados;
    }
    
    @Override
    public ArrayList<Produto> listarProdutosPorFaixaEtaria(String faixaEtaria) {
        
        ArrayList<Produto> produtosEncontrados = new ArrayList();
        
        for(int i = 0; i < this.listaProdutos.size(); i++) {
            
            if(this.listaProdutos.get(i).getAtivo() == true) { //////////////////////
                
                if(this.listaProdutos.get(i).getFaixaEtaria().toLowerCase().equals(faixaEtaria.toLowerCase())){
                    produtosEncontrados.add(this.listaProdutos.get(i));
                }
            }
        }
        return produtosEncontrados;
    }
    
    @Override
    public ArrayList<Produto> listarProdutosPorCategoria(String categoria) {
        
        ArrayList<Produto> produtosEncontrados = new ArrayList();
        
        for(int i = 0; i < this.listaProdutos.size(); i++) {
            if(this.listaProdutos.get(i).getAtivo() == true) {
                if(this.listaProdutos.get(i).getCategoria().toLowerCase().equals(categoria.toLowerCase())){
                    produtosEncontrados.add(this.listaProdutos.get(i));
                }
            }
        }
        return produtosEncontrados;
    }
    
    @Override
    public void alterarProduto(Produto produto, int indiceProduto) {
        this.listaProdutos.set(indiceProduto, produto);
    }
    
    @Override
    public void desabilitarProduto(int indiceProduto) {
        this.listaProdutos.get(indiceProduto).setAtivo(false);
    }
   
    /**
     * Método auxiliar apenas para testes.
     */
    public void imprimirListaProdutos() {
        
        for(int i = 0; i < this.listaProdutos.size(); i++) {
            System.out.println(this.listaProdutos.get(i) + "\n");
        }
    }
    
    @Override
    public void salvarDados() {
        
        try {
            FileOutputStream file = new FileOutputStream("listaProdutos.dat");
            ObjectOutputStream os = new ObjectOutputStream(file);
            os.writeObject(this.listaProdutos);
            os.close();
        }
        
        catch(IOException ioException) {
        }
    }
        
     @Override
    public void lerDados(){
        
        try { 
            FileInputStream file = new FileInputStream("listaProdutos.dat");
            ObjectInputStream is = new ObjectInputStream(file);
            ArrayList<Produto> listaProdutos = (ArrayList<Produto>) is.readObject();
            this.listaProdutos = listaProdutos;
            is.close();
        } 
        
        catch (FileNotFoundException fileNotFound) {
        } 
        catch (IOException ioException) {    
        } 
        catch (ClassNotFoundException classNotFound) {
        }
    }

    public static void main(String[] args){
//        try {
//            FachadaGerente.getInstance().getFachadaGerente().adicionarProduto("12345", "saia", "Calça azul com verde", "juvenil", "masculino", "azul", "p", "dormir", 20, 245.50);
//            System.out.println(FachadaGerente.getInstance().getFachadaGerente().buscarProdutoPorCodigo("12345"));
//        } catch (ProdutoInvalidoException e) {
//            e.printStackTrace();
//        } catch (ProdutoJaCadastradoException e) {
//            e.printStackTrace();
//        } catch (ProdutoNaoEncontradoException e) {
//            e.printStackTrace();
//        }
    }
    
}
    