package negocio.excecao.cliente.funcionario;

public class GerenteJaCadastradoException extends Exception {
    public GerenteJaCadastradoException(){
        super("Gerente já cadastrado.");
    }
}
