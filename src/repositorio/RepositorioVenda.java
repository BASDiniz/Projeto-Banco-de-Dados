package repositorio;

import com.mysql.cj.protocol.Resultset;
import connection.ConnectionFactory;
import fachada.FachadaGerente;
import interfaces.IRepositorioVenda;
import negocio.entidade.*;
import negocio.entidade.produto.Produto;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Esta classe realizar opera��es CRUD com objetos do tipo Venda.
 * @author �verton Vieira
 * @author Bruno Diniz
 * @version 2.00
 */
public class RepositorioVenda implements IRepositorioVenda, Serializable {
    
    private ArrayList<Venda> listaVendas;
    
    /**
     * Construtor RepositorioVenda
     */
    public RepositorioVenda() {
        this.listaVendas = new ArrayList<Venda>();
    }

    // banco implementado
    @Override
    public void adicionarVenda(Venda venda){ // adiciona uma venda // FUNCIONANDO
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        Date data = venda.getDataDeVenda().getTime();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String strData = df.format(data);
        try {

            stmt = conexao.prepareStatement("INSERT INTO venda VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1,strData);
            stmt.setInt(2,venda.getCodigo());
            stmt.setDouble(3,venda.getValorTotal());
            stmt.setBoolean(4,venda.getPagamenteParcelado());
            stmt.setString(5,venda.getDescricao());
            stmt.setString(6,venda.getCliente().getCpf());
            stmt.setString(7,venda.getFuncionario().getCpf());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
    }
    
    //banco implementado
    @Override
    public double verLucroMensal(int mes){//verifica lucro mensal do mes // FUNCIONANDO
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        String str;
        Double retorno = 0.0;
        if(String.valueOf(mes).length() == 1){
            str = Integer.toString(mes);
            str = "%/0"+str+"/%";
        }else{
            str = Integer.toString(mes);
            str = "%/"+str+"/%";
        }
//        Date data = venda.getDataDeVenda().getTime();
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        String strData = df.format(data);
        try {
            stmt = conexao.prepareStatement("SELECT * FROM venda WHERE dataDeVenda like ?");
            stmt.setString(1,str);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                retorno = retorno + rs.getDouble("valorTotal");
            }
            return retorno;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return retorno;
    }
    //banco implementado
    @Override
    public double verLucroAnual(int ano){ // verifica lucro anual // FUNCIONANDO
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        String str = "%/%/"+Integer.toString(ano);
        Double retorno = 0.0;
        try {
            stmt = conexao.prepareStatement("SELECT * FROM venda WHERE dataDeVenda like ?");
            stmt.setString(1,str);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                retorno = retorno + rs.getDouble("valorTotal");
            }
            return retorno;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return retorno;
    }
    //banco implementado
    @Override
    public Funcionario determinarFuncionarioDoMes(int mes) {//retorna funcionario do mes //FUNCIONANDO
        RepositorioCliente repCliente = FachadaGerente.getInstance().getFachadaGerente().getRepositorioCliente();
        String str;
        if(String.valueOf(mes).length() == 1){
            str = Integer.toString(mes);
            str = "%/0"+str+"/%";
        }else{
            str = Integer.toString(mes);
            str = "%/"+str+"/%";
        }
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement("SELECT SUM(valorTotal),v.funcionario from funcionario f join venda v on f.cpf = v.funcionario WHERE v.dataDeVenda like ? GROUP BY  v.funcionario");
            stmt.setString(1,str);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return (Funcionario) repCliente.buscarPorCpfFunc(rs.getString("funcionario"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return null;

//        if(listaVendas.size() > 0) {
//
//            for (int i = 0; i < listaVendas.size(); i++) {
//                maiorQuantidadeDeVendas = 0;
//                for (int j = 0; j < listaVendas.size(); j++) {
//                    if (listaVendas.get(i).getFuncionario().equals(listaVendas.get(j).getFuncionario())
//                            && (listaVendas.get(i).getDataDeVenda().get(Calendar.MONTH) + 1) == mes) {
//                        maiorQuantidadeDeVendas++;
//                    }
//                }
//                if (maiorQuantidadeDeVendas > quantidadeDeVendas) {
//                    quantidadeDeVendas = maiorQuantidadeDeVendas;
//                    funcDoMes = listaVendas.get(i).getFuncionario();
//                }
//            }
//        }
//
//        if (funcDoMes != null){
//            funcDoMes.setFuncDoMes(true);
//        }
//        return funcDoMes;
    }
    //banco implementado
    @Override
    public Cliente determinarClienteFiel(int mes) { // retorna o cliente fiel // FUNCIONANDO
        RepositorioCliente repCliente = FachadaGerente.getInstance().getFachadaGerente().getRepositorioCliente();
        String str;
        if(String.valueOf(mes).length() == 1){
            str = Integer.toString(mes);
            str = "%/0"+str+"/%";
        }else{
            str = Integer.toString(mes);
            str = "%/"+str+"/%";
        }
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement("SELECT SUM(valorTotal),v.cliente from cliente c join venda v on c.cpf = v.cliente WHERE v.dataDeVenda like ? GROUP BY  v.cliente");
            stmt.setString(1,str);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return repCliente.buscarPorCpf(rs.getString("cliente"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return null;
//        double valorGasto = 0;
//        double maiorGastoEncontrado = 0;
//        Cliente clienteFiel = null;
//
//        for(int i = 0; i < listaVendas.size(); i++) {
//
//            valorGasto = 0;
//
//            for(int k = 0; k < listaVendas.size(); k++) {
//
//                if(listaVendas.get(i).getCliente().equals(listaVendas.get(k).getCliente()) && listaVendas.get(i).getDataDeVenda().get(Calendar.MONTH) + 1 == mes) {
//                    valorGasto += listaVendas.get(k).getValorTotal();
//                }
//            }
//
//            if(valorGasto > maiorGastoEncontrado) {
//                maiorGastoEncontrado = valorGasto;
//                clienteFiel = listaVendas.get(i).getCliente();
//            }
//        }
//        return clienteFiel;
    }
    //banco implementado
    public ArrayList<Venda> returnAllVendas(){// retorna todas as venda // FUNCIONANDO
        ArrayList<Venda> retorno = new ArrayList<Venda>();
        ArrayList<Produto> carrinho = new ArrayList<>();
        RepositorioCliente repCliente = FachadaGerente.getInstance().getFachadaGerente().getRepositorioCliente();
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {

            stmt = conexao.prepareStatement("SELECT * FROM venda");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Cliente cliente = repCliente.buscarPorCpf(rs.getString("cliente"));
                Funcionario funcionario = (Funcionario) repCliente.buscarPorCpfFunc(rs.getString("funcionario"));
                Venda venda = new Venda(funcionario,cliente,carrinho,rs.getString("descricao"),rs.getBoolean("pagamentoParcelado"));
                venda.setDataDeVenda(rs.getString("dataDeVenda"));
                retorno.add(venda);
            }
            return retorno;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return retorno;
    }

    //M�todo auxiliar que busca os produtos que j� foram vendidos em alguma venda
    //banco implementado
    private ArrayList<Produto> buscarProdutosVendidos() {// retorna produtos vendidos // FUNCIONANDO

        ArrayList<Produto> produtos = new ArrayList<Produto>();
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement("SELECT * FROM produto WHERE venda != -1");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Produto pro = new Produto(rs.getString("codigo"),rs.getString("tipo_de_roupa"),rs.getString("desc_produto"),rs.getString("faixa_etaria"),rs.getString("genero"),rs.getString("cor"),rs.getString("tamanho"),rs.getString("categoria"),rs.getInt("quantidade"),rs.getDouble("valor_venda"));
                produtos.add(pro);
            }
            return produtos;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return null;
//        ArrayList<Produto> produtosVendidos = new ArrayList<Produto>(); // ArrayList de produtos que foram vendidos
//
//        for(int i = 0; i < this.listaVendas.size(); i++) {
//
//            for(int k = 0; k < this.listaVendas.get(i).getCarrinhoProdutos().size(); k++) {
//
//                if(!produtosVendidos.contains(this.listaVendas.get(i).getCarrinhoProdutos().get(k))) {
//                    produtosVendidos.add(this.listaVendas.get(i).getCarrinhoProdutos().get(k));
//                }
//            }
//        }
//        return produtosVendidos;
    }

    /**
     * Esse m�todo busca quais foram os produtos mais vendidos em determinado m�s.
     * @param mes
     * @return retorna um ArrayList de produtos
     */
    @Override
    public ArrayList<Produto> buscarProdutosMaisVendidos(int mes) {


        ArrayList<Produto> produtosVendidos = buscarProdutosVendidos();
        ArrayList<Produto> produtosMaisVendidos = new ArrayList<Produto>(); // Produtos mais vendidos do maior pro menor

        int vendido = 0; // Quantidade de vezes que o produto foi vendido
        int maisVendido = 0;
        Produto produtoAdicionado = null;

        // Numero de vezes que em que dever ser achado o produto mais vendido
        for(int i = 0; i < produtosVendidos.size(); i++) {

            maisVendido = 0;

            // Percorre o ArrayList de produtos que foram vendidos
            for(int l = 0; l < produtosVendidos.size(); l++) {

                vendido = 0;

                // Percorre ArrayList de vendas(listaVendas)
                for(int k = 0; k < this.listaVendas.size(); k++) {

                    // Percorre o ArrayList de produtos(carrinhoProdutos) da venda(listaVendas.get(i)) em questao
                    for(int j = 0; j < this.listaVendas.get(k).getCarrinhoProdutos().size(); j++) {

                        // Se o produto vendido estiver no carrinhosProdutos de tal venda E o produto ainda nao esta no array de produtos mais vendidos
                        if(produtosVendidos.get(l).equals(this.listaVendas.get(k).getCarrinhoProdutos().get(j))
                                && !produtosMaisVendidos.contains(produtosVendidos.get(l))
                                && this.listaVendas.get(k).getDataDeVenda().get(Calendar.MONTH) + 1 == mes) {
                            vendido++;
                        }

                        if(vendido >= maisVendido) {
                            maisVendido = vendido;
                            produtoAdicionado = produtosVendidos.get(l);
                        }
                    }
                }
            }

            if(!produtosMaisVendidos.contains(produtoAdicionado)) {
                produtosMaisVendidos.add(produtoAdicionado);
            }

        }
        return produtosMaisVendidos;
    }

    //banco implementado
    public ArrayList<Cliente> buscarClientesComPendencia() {// retorna clientes com pendencia // FUNCIONANDO

        ArrayList<Cliente> clientesPendentes = new ArrayList<Cliente>();
        RepositorioCliente repCliente = FachadaGerente.getInstance().getFachadaGerente().getRepositorioCliente();
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement("SELECT * FROM venda WHERE pagamentoParcelado = true");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Cliente cliente = repCliente.buscarPorCpf(rs.getString("cliente"));
                clientesPendentes.add(cliente);
            }
            return clientesPendentes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return null;

//        for(int i = 0; i < listaVendas.size(); i++) {
//
//            if(listaVendas.get(i).getPagamenteParcelado() == true) {
//                clientesPendentes.add(listaVendas.get(i).getCliente());
//            }
//        }
    }

    @Override
    public void salvarDados() {
        try{
            FileOutputStream file = new FileOutputStream("listaVendas.dat");
            ObjectOutputStream os = new ObjectOutputStream(file);
            os.writeObject(this.listaVendas);
            os.close();
        }catch(IOException ioException){

        }

    }

    @Override
    public void lerDados(){
        try{
            FileInputStream file = new FileInputStream("listaVendas.dat");
            ObjectInputStream is = new ObjectInputStream(file);
            ArrayList<Venda> listaVendas = (ArrayList<Venda>) is.readObject();
            this.listaVendas = listaVendas;
            is.close();

        } catch (FileNotFoundException fileNotFound) {

        } catch (IOException ioException) {

        } catch (ClassNotFoundException classNotFound) {

        }
    }

    public static void main(String[] args) {
//        Contato c1 = new Contato("87999429191","87996691842","erik@hotmail.com");
//        Contato c2 = new Contato("87999999999","87888888888","erik2@hotmail.com");
//        Funcionario f = new Funcionario("Erik","11111111111",c1,"123123",1000,false);
//        Cliente cli = new Cliente("Erik Jhonatta","70378876414",c2,false);
//        ArrayList<Produto> car = new ArrayList<>();
//        Produto p = new Produto("5","camisa","Camisa para testes","infantil","Unissex","azul","PP","dormir",50,100);
//        car.add(p);
//        Venda v = new Venda(f,cli,car,"Venda somente para testes",false);
//
        RepositorioVenda rep = new RepositorioVenda();
//        rep.adicionarVenda(v);

        System.out.println(rep.buscarProdutosVendidos().get(0).toString());
    }
}
