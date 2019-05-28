package repositorio;

import negocio.entidade.produto.Produto;
import interfaces.IRepositorioProduto;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
    
    @Override
    public int procurarProduto(Produto produto) {
         
        for(int i = 0; i < this.listaProdutos.size(); i++) {
            
            if(this.listaProdutos.get(i).equals(produto)) {
                
                if(this.listaProdutos.get(i).getAtivo() == true) {
                    return i;
                }
                
            }
        }
        return -1;
    }
    
    @Override 
    public void adicionarProduto(Produto produto) {
        this.listaProdutos.add(produto);
    }
    
    @Override
    public Produto buscarProdutoPorCodigo(String codigo) {
        
        for(int i = 0; i < this.listaProdutos.size(); i++) {
            
            if(this.listaProdutos.get(i).getAtivo() == true) { //////////////////////
             
                if(this.listaProdutos.get(i).getCodigo().equals(codigo)) {
                    return this.listaProdutos.get(i);
                }
            }
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
            
            if(this.listaProdutos.get(i).getAtivo() == true) { //////////////////////
                
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
    
}
    