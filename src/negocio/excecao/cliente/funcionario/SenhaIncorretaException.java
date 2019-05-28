package negocio.excecao.cliente.funcionario;

public class SenhaIncorretaException extends Exception {
    public SenhaIncorretaException(){
        super("Senha incorreta.");
    }
}
