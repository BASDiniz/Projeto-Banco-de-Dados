package dados;

import connection.ConnectionFactory;
import negocio.entidade.Cliente;
import negocio.entidade.Contato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbCliente {

    public void adicionarCliente(Cliente cliente){
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conexao.prepareStatement("INSERT INTO cliente (nome, cpf, fiel, ativo) VALUES (?, ?, ?, ?)");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setBoolean(3, cliente.getFiel());
            stmt.setBoolean(4, cliente.getAtivo());

            stmt.executeUpdate();

            stmt = conexao.prepareStatement("INSERT INTO contato (telefonePrincipal, telefoneAlternativo, email, proprietario) VALUES (?, ?, ?, ?)");
            stmt.setString(1, cliente.getContato().getTelefonePrincipal());
            stmt.setString(2, cliente.getContato().getTelefoneAlternativo());
            stmt.setString(3, cliente.getContato().getEmail());
            stmt.setString(4, cliente.getCpf());

            stmt.executeUpdate();


        } catch (SQLException e) {
            //e.printStackTrace();
        } finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
    }

    public Cliente procurarCliente(Cliente cliente){
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            stmt = conexao.prepareStatement("SELECT nome, cpf, fiel, ativo" + "FROM cliente WHERE cpf =" + cliente.getCpf());
            //rs = stmt.executeQuery();
            rs = stmt.executeQuery();
            rs.next();

            stmt2 = conexao.prepareStatement("SELECT telefonePrincipal, telefoneAlternativo, email" + "WHERE proprietario =" + cliente.getCpf());
            //rs2 = stmt2.executeQuery();
            rs2 = stmt2.executeQuery();
            rs.next();

            if(!rs.isBeforeFirst()){
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Boolean fiel = rs.getBoolean("fiel");
                Boolean ativo = rs.getBoolean("ativo");

                if(!rs2.isBeforeFirst()){
                    String telefonePrincipal = rs2.getString("telefonePrincipal");
                    String telefoneAlternativo = rs2.getString("telefoneAlternativo");
                    String email = rs2.getString("email");
                    Contato contato = new Contato(telefonePrincipal, telefoneAlternativo, email);
                    Cliente clienteBanco = new Cliente(nome, cpf, contato);
                    clienteBanco.setFiel(fiel);
                    clienteBanco.setAtivo(ativo);

                    return clienteBanco;
                }

            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }finally{
            ConnectionFactory.closeConnection(conexao, stmt);
        }
        return null;
    }
}
